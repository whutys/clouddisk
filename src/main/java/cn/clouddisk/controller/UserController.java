package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@PropertySource("classpath:settings.properties")
@RequestMapping("/jsp")
public class UserController {
    @Autowired
    UserService userService;
    @Value("${storePath}")
    String path;

    @ResponseBody
    @RequestMapping("test")
    public String test(Map<String, Object> model) {
        model.put("time", new Date());
        model.put("message", "message");
        return "test";
    }

    @GetMapping("/signInPage")
    public String signInPage(User user) {
        return "signin";
    }

    @PostMapping("/signInForm")
    public String signInForm(User user, BindingResult bindingResult, HttpSession session) {
        try {
           Map userMap = userService.checkUser(user);
            user.setNickName((String) userMap.get("nickName"));
            user.setIsVip((int)userMap.get("isVip"));
            if (user.getUserName() != null) {
                // 如果登陆成功 把用户名放到session域
                session.setAttribute("user", user);
                if (user.getUserName().equals("admin1")) {
                    session.setAttribute("admin", true);
                }
                return "redirect:/searchUserFile";
            }
            bindingResult.rejectValue("userName", "", "用户名或密码错误");
            return "signin";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/message.jsp";
        }
    }

    @RequestMapping("/signOut")
    public String signOut(HttpSession session) {
        session.invalidate();
        return "redirect:/index.jsp";
    }

    @RequestMapping("/autoSignIn")
    public String autoSignIn() {
        return "redirect:/searchUserFile";
    }

    @RequestMapping("/registPage")
    public String registPage(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping("/registForm")
    public String registForm(Model model, String inviteCode, @Valid User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            if (!"6666".equals(inviteCode)) {
                bindingResult.addError(new ObjectError("inviteCode", "邀请码不正确"));
                model.addAttribute("inviteCodeError", "邀请码不正确");
            } else {
                try {
                    userService.createUser(user); // 如果用户已注册 下层的service会抛出异常
                    new File(path + File.separator + user.getUserName()).mkdirs();
                    return "redirect:/signInPage";
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
    public JSONObject checkUserName(String userName) {
        Map<String, Boolean> map = new HashMap<>();
        try {
            if (userService.findUser(userName)) {
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
