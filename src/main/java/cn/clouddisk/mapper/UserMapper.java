package cn.clouddisk.mapper;

import cn.clouddisk.entity.User;

import java.util.List;

public interface UserMapper {

	List<User> selectUserList(User user);
	User selectUserById(int userId);
	int insertUser(User user);
	User selectUserByName(String username);
	int deleteUserById(int userId);
	int updateUser(User user);
}

