package cn.clouddisk.service;

import cn.clouddisk.entity.UserRole;

public interface IUserRoleService {
    int insertUserRole(UserRole userRole);

    void insertUserRoleByUserId(int userId);
}
