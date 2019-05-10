package cn.clouddisk.service.impl;

import cn.clouddisk.entity.User;
import cn.clouddisk.mapper.UserMapper;
import cn.clouddisk.service.IUserService;
import cn.clouddisk.shiro.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<User> findUserList(User user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int addUser(User user) {
        user.setSalt("whutys");
        user.setPassword(passwordService.encryptPassword(user.getPassword(), "whutys", 3));
        return userMapper.insertUser(user);
    }

    @Override
    public int removeUserById(int userId) {
        return userMapper.deleteUserById(userId);
    }

    @Override
    public int updateUser(User user) {
        user.setPassword(passwordService.encryptPassword(user.getPassword(), "whutys", 3));
        return userMapper.updateUser(user);
    }

    @Override
    public User findUserById(int userId) {
        return userMapper.selectUserById(userId);
    }

    public User findUserByName(String username) {
        return userMapper.selectUserByName(username);
    }
}


