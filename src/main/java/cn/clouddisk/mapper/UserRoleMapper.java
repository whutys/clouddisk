package cn.clouddisk.mapper;

import cn.clouddisk.entity.UserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {
    @Insert("insert into user_role(user_id,role_id) values(#{user_id},#{role_id})")
    int insertUserRole(UserRole userRole);

    @Insert("insert into user_role(user_id) values(#{user_id})")
    void insertUserRoleByUserId(int userId);
}
