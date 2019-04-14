package cn.clouddisk.service;

import cn.clouddisk.entity.Role;

import java.util.List;

public interface IRoleService {
    List<String> selectRoleKeysByUserId(int userId);

    List<String> selectRoleNamesByUserId(int userId);

    List<Role> selectRoleByUserId(int userId);
}
