package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping("/admin")
    public String logIn(User user) {
        return "/admin/signIn";
    }

    @RequestMapping("/admin/signInForm")
    public String signIn(Model model) {
        List<User> allUsers = adminService.findAllUsers();
        model.addAttribute("users",allUsers);
        return "/admin/users";
    }
    @RequestMapping("/admin/deleteUser")
    public String deleteUser(int id){
        adminService.deleteUserById(id);
        return "redirect:/admin/signInForm";
    }
}
