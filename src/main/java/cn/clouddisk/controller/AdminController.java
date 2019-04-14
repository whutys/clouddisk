package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Admin {

    @RequestMapping("/admin")
    public String logIn(User user){
        return "/admin/signIn";
    }

    @RequestMapping("/admin/signInForm")
    public String signIn(){
        return "/admin/home";
    }
}
