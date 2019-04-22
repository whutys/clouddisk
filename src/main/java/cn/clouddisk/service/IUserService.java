package cn.clouddisk.service;

import cn.clouddisk.entity.User;

import java.util.List;

public interface IUserService {
    List<User> findUserList(User user);
    User findUserByName(String username);
    int addUser(User user);
    int removeUserById(int userId);
    int updateUser(User user);
    User findUserById(int userId);
}
