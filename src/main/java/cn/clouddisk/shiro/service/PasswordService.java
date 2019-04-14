package cn.clouddisk.shiro.service;

import cn.clouddisk.entity.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

@Component
public class PasswordService {
    public void validate(User user,String password){
        String username = user.getUsername();
        if (!matches(user,password)){
        }else {
        }
    }

    public boolean matches(User user, String password){
        return user.getPassword().equals(encryptPassword(password,"whutys",3));
    }

    public String encryptPassword(String password, String salt,int iterations) {
        return new Md5Hash(password,salt,iterations).toString();
    }
}
