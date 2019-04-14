package cn.clouddisk.mapper;

import cn.clouddisk.entity.Role;

import java.util.List;

public interface RoleMapper {
    public List<Role> selectRoleList(Role role);
    public List<Role> selectRoleByUserId(int userId);
    public Role selectRoleById(int roleId);
}
