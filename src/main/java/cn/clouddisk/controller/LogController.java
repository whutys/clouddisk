package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.RoleService;
import cn.clouddisk.service.impl.UserService;
import cn.clouddisk.utils.ShiroUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PropertySource("classpath:settings.properties")
//@RequestMapping("/jsp")
public class LogController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public LogController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Value("${storePath}")
    String path;

    @ResponseBody
    @RequestMapping("test")
    public String test(Map<String, Object> model) {
//        model.put("time", new Date());
//        model.put("message", "message");
        User admin1 = userService.selectUserByName("admin1");
        return admin1.getPassword();
    }

    @GetMapping("/logIn")
    public String signInPage(User user) {
        return "signin";
    }

    @PostMapping("/logIn")
//    @ResponseBody
    public String signInForm(User user, BindingResult bindingResult, HttpSession session,boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(),rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            user = ShiroUtils.getUser();
            List<String> roleKeys = roleService.selectRoleKeys(user.getId());
            // 如果登陆成功 把用户名放到session域
            session.setAttribute("user", user);
            if (user.getUsername().equals("admin1")) {
                session.setAttribute("admin", true);
            }
            if (roleKeys.contains("admin")) return "redirect:/user";
//            return "success";
            return "redirect:/userHome";
        } catch (Exception e) {
            e.printStackTrace();
            bindingResult.rejectValue("username", "", "用户名或密码错误");
            return "signin";
//            return "redirect:/message.jsp";
        }

    }

//    @RequestMapping("/signOut")
//    public String signOut(HttpSession session) {
//        session.invalidate();
//        return "redirect:/index.jsp";
//    }

    @RequestMapping("/autoSignIn")
    public String autoSignIn() {
        return "redirect:/userHome";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/register")
    public String registerForm(Model model, String inviteCode, @Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (!"6666".equals(inviteCode)) {
                bindingResult.addError(new ObjectError("inviteCode", "邀请码不正确"));
                model.addAttribute("inviteCodeError", "邀请码不正确");
            } else {
                try {
                    userService.insertUser(user); // 如果用户已注册 下层的service会抛出异常
                    new File(path + File.separator + user.getUsername()).mkdirs();
                    return "redirect:/logIn";
                } catch (Exception e) {
                    e.printStackTrace();
                    model.addAttribute("userNameError", "该用户已注册");
                }
            }
            model.addAttribute("user", user);
            return "signup";
        } else {
            model.addAttribute("user", user);
            return "signup";
        }
    }

    @ResponseBody
    @RequestMapping("/checkUserName")
    public JSONObject checkUserName(String username) {
        Map<String, Boolean> map = new HashMap<>();
        try {
            if (userService.findUser(username)) {
                map.put("valid", false);
            } else {
                map.put("valid", true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("valid", false);
        }
        return JSONObject.parseObject(JSON.toJSONString(map));
    }

    @ResponseBody
    @RequestMapping("/checkInviteCode")
    public JSONObject checkInviteCode(String inviteCode) {
        Map<String, Boolean> map = new HashMap<>();
        if ("6666".equals(inviteCode)) {
            map.put("valid", true);
        } else {
            map.put("valid", false);
        }
        return JSONObject.parseObject(JSON.toJSONString(map));
    }
}
