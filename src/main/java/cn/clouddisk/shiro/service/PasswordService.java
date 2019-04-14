package cn.clouddisk.shiro.service;

import cn.clouddisk.entity.User;
import cn.clouddisk.shiro.web.exception.user.UserPasswordNotMatchException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {
    public void validate(User user, String password) {
        String username = user.getUsername();
        if (!matches(user, password)) {
            throw new UserPasswordNotMatchException();
        } else {

        }
    }

    public boolean matches(User user, String password) {
        return user.getPassword().equals(encryptPassword(password, user.getSalt(), 3));
    }

    public String encryptPassword(String password, String salt, int iterations) {
        return new Md5Hash(password, salt, iterations).toString();
    }
}
