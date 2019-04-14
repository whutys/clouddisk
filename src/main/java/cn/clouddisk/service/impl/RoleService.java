package cn.clouddisk.service.impl;

import cn.clouddisk.entity.Role;
import cn.clouddisk.mapper.RoleMapper;
import cn.clouddisk.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> selectRoleKeys(int userId) {
        List<Role> roles = roleMapper.selectRoleByUserId(userId);
        List<String> rolesList = new ArrayList<>();
        for (Role role : roles) {
            rolesList.add(role.getRoleKey());
        }
        return rolesList;
    }
}
