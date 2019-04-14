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
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;
    private PasswordService passwordService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, PasswordService passwordService) {
        this.userMapper = userMapper;
        this.passwordService = passwordService;
    }

    @Override
    public List<User> selectUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int insertUser(User user) {
        user.setSalt("whutys");
        user.setPassword(passwordService.encryptPassword(user.getPassword(), "whutys", 3));
        return userMapper.insertUser(user);
    }

    @Override
    public int deleteUserById(int userId) {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public int updateUser(User user) {
        user.setPassword(passwordService.encryptPassword(user.getPassword(), "whutys", 3));
        return userMapper.updateUser(user);
    }

    @Override
    public User selectUserById(int userId) {
        return userMapper.selectUserById(userId);
    }

    public User selectUserByName(String username) {
        return userMapper.selectUserByName(username);
    }
}


