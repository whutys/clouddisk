package cn.clouddisk.controller;

import cn.clouddisk.entity.Admin;
import cn.clouddisk.entity.User;
import cn.clouddisk.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    final private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping("")
    public String logIn(Admin admin) {
        return "/admin/signIn";
    }

    @RequestMapping("/signInForm")
    public String signIn(Admin admin, Model model) {
        if (!adminService.checkAdmin(admin)) return "redirect:/admin";
        List<User> allUsers = adminService.findAllUsers();
        model.addAttribute("users", allUsers);
        return "/admin/users";
    }

    @RequestMapping("/deleteUser")
    public String deleteUser(int id) {
        adminService.deleteUserById(id);
        return "redirect:/admin/signInForm";
    }
}
