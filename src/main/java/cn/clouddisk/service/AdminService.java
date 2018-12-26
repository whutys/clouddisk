package cn.clouddisk.service;

import cn.clouddisk.entity.Admin;
import cn.clouddisk.entity.User;
import cn.clouddisk.mapper.AdminMapper;
import cn.clouddisk.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserMapper userMapper;

    public List<User> findAllUsers() {
        return adminMapper.getAllUsers();
    }

    public int deleteUserById(int id) {
        return adminMapper.deleteUserById(id);
    }

    public boolean checkAdmin(Admin admin) {
        if (adminMapper.checkAdmin(admin) == 0) {
            return false;
        } else {
            return true;
        }
    }
}
