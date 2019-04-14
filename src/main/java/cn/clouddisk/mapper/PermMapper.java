package cn.clouddisk.mapper;

import cn.clouddisk.entity.Perm;
import cn.clouddisk.entity.Role;

import java.util.List;

public interface PermMapper {
    public List<Perm> selectPermList(Perm perm);
    public List<String> selectPermByUserId(int userId);
    public Perm selectPermById(int permId);
}
