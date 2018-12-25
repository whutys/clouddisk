package cn.clouddisk.service;

import cn.clouddisk.entity.User;
import cn.clouddisk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserMapper userMapper;

	public void createUser(User user) throws Exception {
		Boolean found = findUser(user.getUserName());
		if (!found) {
			userMapper.createUser(user);
		} else {
			throw new RuntimeException();
		}
	}

	public Map<String,Object> checkUser(User user) throws Exception {
		return userMapper.checkUser(user);
	}

	public boolean findUser(String userName) throws Exception {
		Integer found = userMapper.findUser(userName);
		if (found == null || found < 1)
			return false;
		return true;
	}

	public int isVip(String user_name) throws Exception {
		return userMapper.isVip(user_name);
	}
}


