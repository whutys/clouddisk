package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.RoleServiceImpl;
import cn.clouddisk.service.impl.UserRoleServiceImpl;
import cn.clouddisk.service.impl.UserServiceImpl;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RequiresRoles("admin")
@Controller
@RequestMapping("/user")
public class UserController {

    private UserServiceImpl userService;
    private RoleServiceImpl roleService;
    private UserRoleServiceImpl userRoleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService,UserRoleServiceImpl userRoleService) {
        this.userService = userService;
        this.roleService = roleService;
        this.userRoleService=userRoleService;
    }
    @RequiresPermissions("user:view")
    @GetMapping()
    public String user(User user, Model model){
        List<User> list = userService.findUserList(user);
        list.forEach((each)->each.setRoles(roleService.getRoleByUserId(each.getId())));
        model.addAttribute("users", list);
        return "user/user";
    }
    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public String list(User user, Model model){
        List<User> list = userService.findUserList(user);
        model.addAttribute("users", list);
        return "/user/user";
    }
    @GetMapping("/add")
    public String add(User user){
        return "/user/add";
    }

    @RequiresPermissions("user:add")
    @PostMapping("/add")
    public String addSave(User user,String username){
        int userId = userService.addUser(user);
        userRoleService.insertUserRoleByUserId(userService.findUserByName(username).getId());
        return "redirect:/user";
    }
    @RequiresPermissions("user:remove")
    @GetMapping("/remove/{userId}")
    public String remove(@PathVariable("userId") int userId){
        userService.removeUserById(userId);
        return "redirect:/user";
    }
    @GetMapping("/edit/{userId}")
    public String edit(@PathVariable("userId") int userId, ModelMap modelMap){
        User user = userService.findUserById(userId);
        user.setPassword(null);
        modelMap.put("user", user);
        return "/user/edit";
    }
    @RequiresPermissions("user:edit")
    @PostMapping("/edit")
    public String editSave(User user){
        userService.updateUser(user);
        return "redirect:/user";
    }
}
