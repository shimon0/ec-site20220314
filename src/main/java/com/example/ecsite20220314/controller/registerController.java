package com.example.ecsite20220314.controller;


import javax.servlet.http.HttpSession;

import com.example.ecsite20220314.form.UserForm;
import com.example.ecsite20220314.service.loginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/register")
public class registerController {
    @Autowired
    private HttpSession session;

    @ModelAttribute
    private UserForm setUpUserForm(){
        return  new UserForm();
    }
    @Autowired
    private loginService service;

    @RequestMapping("")
    public  String  index(){
        if (session.getAttribute("userId")!=null) {
            return  "redirect:/itemList";
        }
        return  "register_user";
    }

    @RequestMapping("/confirm")
    public String confirm(@Validated UserForm userForm,
    BindingResult  result,RedirectAttributes redirectAttributes,Model model){
        if (result.hasErrors()) {
            return  index();
        }
        if(!(userForm.getPassword().equals(userForm.getConfirmPassword()))){
            model.addAttribute("passErorrMessage","確認用パスワードが一致していません");
            return  index();
        }
        if(service.confirmMail(userForm)!=0){
            model.addAttribute("exist","既にそのメールアドレスは存在しています");
            return  index();
        }
        service.register(userForm);
        redirectAttributes.addAttribute("registerMessage", "アカウントを作成しました");
        return  "redirect:/login";
    }
}
