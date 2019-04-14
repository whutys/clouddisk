package cn.clouddisk.mapper;

import cn.clouddisk.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

	List<User> selectUserList(User user);
	int insertUser(User user);

	Map<String,Object> checkUser(User user) throws Exception;
	Integer findUser(String username) throws Exception;
	Integer isVip(String user_name)throws Exception;

	User selectUserByName(String username);


	int deleteUserById(int userId);

	int updateUser(User user);

    User selectUserById(int userId);
}

