package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("test")
    public String test(Map<String, Object> model) {
        model.put("time",new Date());
        model.put("message","message");
        return "test";
    }

    @GetMapping(value = "signInPage")
    public String signInPage(User user) {
        return "signin";
    }

    @RequestMapping(value = "/signInForm", method = RequestMethod.POST)
    public String signInForm(User user, BindingResult bindingResult, HttpSession session) {
        try {
            String user_name = userService.checkUser(user);
            if (user_name != null && (!"".equals(user_name))) {
                // 如果登陆成功 把用户名放到session域
                session.setAttribute("user_name", user_name);
                if (user_name.equals("admin1")) {
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
        return JSONObject.parseObject( JSON.toJSONString(map));
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
