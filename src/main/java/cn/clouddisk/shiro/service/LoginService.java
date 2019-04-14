package cn.clouddisk.shiro.service;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.UserServiceImpl;
import cn.clouddisk.shiro.web.exception.user.UserNotExistsException;
import cn.clouddisk.shiro.web.exception.user.UserPasswordNotMatchException;
import cn.clouddisk.utils.StringUtils;
import cn.clouddisk.utils.constant.UserConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private PasswordService passwordService;

    public User login(String username, String password) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new UserNotExistsException();
        }
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH || password.length() > UserConstants.PASSWORD_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH || username.length() > UserConstants.USERNAME_MAX_LENGTH) {
            throw new UserPasswordNotMatchException();
        }

        User user = userServiceImpl.selectUserByName(username);
        if (user == null) {
            throw new UserNotExistsException();
        }
        passwordService.validate(user, password);
        return user;
    }

}
