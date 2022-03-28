package com.example.ecsite20220314.controller;

import javax.servlet.http.HttpSession;

import com.example.ecsite20220314.domain.User;
import com.example.ecsite20220314.form.LoginForm;
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
        //ログイン状態のチェック
		if(userId!=null){
            return  "redirect:/shoppingList";
		}
        //入力エラーチェック
        if(result.hasErrors()){
            return  index();
        }
        //アカウント情報の検索
        User user=service.login(loginForm);

        //パスワード確認
        //パスワード失敗if文
        if(!(user.getPassword().equals(loginForm.getPassword()))){
            model.addAttribute("passMiss","パスワードが正しくありません");
            return  "foward:/login";
        }else{
            //パスワード確認後、セッションをセット
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName",user.getName());
            //ゲストカート情報をログインカートに更新
            if(session.getAttribute("preId")!=null){
                service.updateGeustCart(user.getId(),(Integer)session.getAttribute("preId"));
            //セッションスコープからゲストidの破棄
                session.removeAttribute("preId");
            }
            return  "redirect:/itemList";
        }

    }
}
