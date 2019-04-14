package cn.clouddisk.mapper;

import cn.clouddisk.entity.User;

public interface UserMapper {
	
	void createUser(User user) throws Exception;
	String checkUser(User user) throws Exception;
	Integer findUser(String userName) throws Exception;
	Integer isVip(String user_name)throws Exception;
	
}

