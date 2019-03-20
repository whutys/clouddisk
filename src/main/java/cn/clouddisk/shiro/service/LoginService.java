package cn.clouddisk.shiro.service;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordService passwordService;

    public User login(String username, String password) {
        User user = userService.selectUserByName(username);
        if (user == null) {
        }
        passwordService.validate(user, password);
        return user;
    }

}
