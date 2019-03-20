package cn.clouddisk.controller;

import cn.clouddisk.entity.Admin;
import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.AdminService;
import cn.clouddisk.service.impl.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logIn")
    public String logIn(User user) {
        return "admin/logIn";
    }

    @PostMapping("/logIn")
    public String signIn(User user, Model model) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return "redirect:/user/list";

        } catch (Exception e) {
            return "redirect:/admin/logIn";
        }
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(int id) {
        userService.deleteUserById(id);
        return "redirect:/admin/signInForm";
    }
}
