package cn.clouddisk.service.impl;

import cn.clouddisk.entity.User;
import cn.clouddisk.mapper.UserMapper;
import cn.clouddisk.service.IUserService;
import cn.clouddisk.shiro.service.PasswordService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService implements IUserService {

    private UserMapper userMapper;
    private PasswordService passwordService;

    @Autowired
    public UserService(UserMapper userMapper, PasswordService passwordService) {
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int insertUser(User user) {
            user.setPassword(passwordService.encryptPassword(user.getPassword(), "whutys", 3));
            return userMapper.insertUser(user);
    }

    @Override
    public int deleteUserById(int userId){
        return userMapper.deleteUserById(userId);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public User selectUserById(int userId) {
        return userMapper.selectUserById(userId);
    }

    public Map<String, Object> checkUser(User user) throws Exception {
        user.setPassword(new Md5Hash(user.getPassword(), "whutys", 3).toString());
        return userMapper.checkUser(user);
    }

    public boolean findUser(String username) throws Exception {
        Integer found = userMapper.findUser(username);
        if (found == null || found < 1)
            return false;
        return true;
    }

    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }

    public int isVip(String user_name) throws Exception {
        return userMapper.isVip(user_name);
    }


}


