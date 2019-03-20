package cn.clouddisk.service;

import cn.clouddisk.entity.User;

import java.util.List;

public interface IUserService {
    public List<User> selectUserList(User user);
    User selectUserByName(String username);
    int insertUser(User user);
    int deleteUserById(int userId);
    int updateUser(User user);

    User selectUserById(int userId);
}
