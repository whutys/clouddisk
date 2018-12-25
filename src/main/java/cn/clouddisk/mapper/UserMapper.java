package cn.clouddisk.mapper;

import cn.clouddisk.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

public interface UserMapper {
	
	void createUser(User user) throws Exception;

	Map<String,Object> checkUser(User user) throws Exception;
	Integer findUser(String userName) throws Exception;
	Integer isVip(String user_name)throws Exception;
	
}

