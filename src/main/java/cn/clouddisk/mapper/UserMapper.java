package cn.clouddisk.mapper;

import cn.clouddisk.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

	List<User> selectUserList(User user);
	User selectUserById(int userId);
	int insertUser(User user);
	User selectUserByName(String username);
	int deleteUserById(int userId);
	int updateUser(User user);
}

