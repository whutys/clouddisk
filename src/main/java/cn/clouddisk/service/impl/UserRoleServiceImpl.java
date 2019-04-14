package cn.clouddisk.service.impl;

import cn.clouddisk.entity.UserRole;
import cn.clouddisk.mapper.UserRoleMapper;
import cn.clouddisk.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements IUserRoleService {
    private UserRoleMapper userRoleMapper;

    @Autowired
    public UserRoleServiceImpl(UserRoleMapper userRoleMapper) {
        this.userRoleMapper = userRoleMapper;
    }

    @Override
    public int insertUserRole(UserRole userRole) {
        return userRoleMapper.insertUserRole(userRole);
    }

    @Override
    public void insertUserRoleByUserId(int userId) {
        userRoleMapper.insertUserRoleByUserId(userId);
    }
}
