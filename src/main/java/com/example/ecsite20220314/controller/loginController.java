package com.example.ecsite20220314.controller;

import javax.servlet.http.HttpSession;

import com.example.ecsite20220314.domain.User;
import com.example.ecsite20220314.form.LoginForm;
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
@RequestMapping("/login")
public class loginController {
    
    @ModelAttribute
    public LoginForm setUpForm(){
        return  new LoginForm();
    }
    @Autowired
    private loginService service;
    @Autowired
    private HttpSession session;

    @RequestMapping("")
    public  String  index(){
        Integer userId = (Integer) session.getAttribute("userId");
		if(userId!=null){
            return  "redirect:/shoppingList";
		}
        return  "login";
    }

    @RequestMapping("/confirm")
    public  String  login(@Validated LoginForm  loginForm,BindingResult result,RedirectAttributes redirectAttributes,Model model){
        
        Integer userId = (Integer) session.getAttribute("userId");
		if(userId!=null){
            return  "redirect:/shoppingList";
		}

        if(result.hasErrors()){
            return  index();
        }

        User user=service.login(loginForm);

        if(!(user.getPassword().equals(loginForm.getPassword()))){
            model.addAttribute("passMiss","パスワードが正しくありません");
            return  "foward:/login";
        }else{
            return  "redirect:/itemList";
        }

    }
}
