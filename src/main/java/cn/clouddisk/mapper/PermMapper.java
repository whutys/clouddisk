package cn.clouddisk.mapper;

import cn.clouddisk.entity.Perm;

import java.util.List;

public interface PermMapper {
    List<Perm> selectPermList(Perm perm);
    List<Perm> selectPermByUserId(int userId);
    Perm selectPermById(int permId);
}
