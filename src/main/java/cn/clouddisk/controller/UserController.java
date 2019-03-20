package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.UserService;
import cn.clouddisk.shiro.service.PasswordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private PasswordService passwordService;

    @Autowired
    public UserController(UserService userService,PasswordService passwordService) {
        this.userService = userService;
        this.passwordService=passwordService;
    }
    @RequiresPermissions("user:view")
    @GetMapping()
    public String user(User user, Model model){
        List<User> list = userService.selectUserList(user);
        model.addAttribute("users", list);
        return "user/user";
    }
    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public String list(User user, Model model){
        List<User> list = userService.selectUserList(user);
        model.addAttribute("users", list);
        return "/admin/users";
    }
    @GetMapping("/add")
    @ResponseBody
    public String add(){
        return "/user/add";
    }
    @RequiresPermissions("user:add")
    @PostMapping("/add")
    @ResponseBody
    public String addSave(User user){
        user.setPassword(passwordService.encryptPassword(user.getPassword(),"whutys",3));
        userService.insertUser(user);
        return null;
    }
    @RequiresPermissions("user:remove")
    @GetMapping("/remove/{userId}")
    @ResponseBody
    public String remove(@PathVariable("userId") int userId){
        userService.deleteUserById(userId);
        return null;
    }
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") int userId, ModelMap modelMap){
        User user = userService.selectUserById(userId);
        user.setPassword("");
        modelMap.put("user", user);
        return "/user/edit";
    }
    @RequiresPermissions("user:edit")
    @PostMapping("/edit")
    public String editSave(User user){
        user.setPassword(passwordService.encryptPassword(user.getPassword(),"whutys",3));
        userService.updateUser(user);
        return "redirect:/user";
    }
}
