package cn.clouddisk.mapper;

import cn.clouddisk.entity.Admin;
import cn.clouddisk.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminMapper {
    @Select("Select count(id) from admin where adminName=#{adminName} and adminPassWord=#{adminPassWord}")
    int checkAdmin(Admin admin);

    @Select("Select * from user")
    List<User> getAllUsers();

    @Delete("Delete from user where id=#{id}")
    int deleteUserById(int id);
}
