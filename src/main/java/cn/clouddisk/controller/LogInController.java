package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.UserServiceImpl;
import cn.clouddisk.utils.ShiroUtils;
import cn.clouddisk.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
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
import java.util.Map;

@Controller
@PropertySource("classpath:settings.properties")
//@RequestMapping("/jsp")
public class LogInController {
    private UserServiceImpl userServiceImpl;

    @Autowired
    public LogInController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @Value("${storePath}")
    String path;

    @ResponseBody
    @RequestMapping("test")
    public String test(Map<String, Object> model) {
//        model.put("time", new Date());
//        model.put("message", "message");
        User admin1 = userServiceImpl.selectUserByName("admin1");
        return admin1.getPassword();
    }

    @GetMapping("/logIn")
    public String logInPage(User user) {
        return "signin";
    }

    @PostMapping("/logIn")
//    @ResponseBody
    public String login(User user, BindingResult bindingResult, HttpSession session, boolean rememberMe) {
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(), rememberMe);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            user = ShiroUtils.getUser();
            // 如果登陆成功 把用户名放到session域
            session.setAttribute("user", user);
            if (user.getUsername().equals("admin1")) {
                session.setAttribute("admin", true);
            }
            if (ShiroUtils.getSubject().hasRole("admin")) return "redirect:/user";
//            return "success";
            return "redirect:/userHome";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            String msg = "用户名或密码错误";
            if (StringUtils.isNotEmpty(e.getMessage()))msg=e.getMessage();
            bindingResult.rejectValue("username", "", msg);
            return "signin";
//            return "redirect:/message.jsp";
        }

    }

    @RequestMapping("/autoLogin")
    public String autoLogin() {
        if(ShiroUtils.getSubject().hasRole("admin"))return "redirect:/user";
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
                    userServiceImpl.insertUser(user); // 如果用户已注册 下层的service会抛出异常
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
            return "signin";
        }
    }

    @ResponseBody
    @RequestMapping("/checkUserName")
    public JSONObject checkUserName(String userName) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", userServiceImpl.selectUserByName(userName) == null);
        return JSONObject.parseObject(JSON.toJSONString(map));
    }

    @ResponseBody
    @RequestMapping("/checkInviteCode")
    public JSONObject checkInviteCode(String inviteCode) {
        Map<String, Boolean> map = new HashMap<>();
        map.put("valid", "6666".equals(inviteCode));
        return JSONObject.parseObject(JSON.toJSONString(map));
    }

    @GetMapping("/unauth")
    public String unautn(){
        return "/error/unauth";
    }
}
