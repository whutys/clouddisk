package cn.clouddisk.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PlayListMapper {
    @Insert("insert into playlist(userName,videoName) values(#{userName},#{videoName})")
    void setVideoName(@Param("userName") String userName,@Param("videoName") String videoName);
    @Select("select videoName from playlist where userName=#{userName}")
    String getVideoName(@Param("userName") String userName);
    @Update("Update playlist set videoName=#{videoName} where userName=#{userName}")
    void updateVideoName(@Param("userName") String userName,@Param("videoName") String videoName);
}
