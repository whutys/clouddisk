package cn.clouddisk.service.impl;

import cn.clouddisk.entity.Role;
import cn.clouddisk.mapper.RoleMapper;
import cn.clouddisk.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<String> getRoleKeysByUserId(int userId) {
        List<Role> roles = roleMapper.selectRoleByUserId(userId);
        List<String> rolesList = new ArrayList<>();
        for (Role role : roles) {
            rolesList.add(role.getRoleKey());
        }
        return rolesList;
    }

    @Override
    public List<String> selectRoleNamesByUserId(int userId) {
        List<Role> roles = roleMapper.selectRoleByUserId(userId);
        List<String> rolesNameList = new ArrayList<>();
        roles.forEach((role) -> rolesNameList.add(role.getRoleName()));
        return rolesNameList;
    }

    @Override
    public List<Role> getRoleByUserId(int userId) {
        return roleMapper.selectRoleByUserId(userId);
    }
}
