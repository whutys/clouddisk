package cn.clouddisk.service;

import java.util.List;

public interface IRoleService {
    public List<String> selectRoleKeys(int userId);
}
