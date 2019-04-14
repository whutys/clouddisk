package cn.clouddisk.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PlayListMapper {
    @Insert("insert into playlist(username,videoName) values(#{username},#{videoName})")
    void setVideoName(@Param("username") String username,@Param("videoName") String videoName);
    @Select("select videoName,videoAddress from playlist where username=#{username}")
    Map<String,String> getVideoName(@Param("username") String username);
    @Update("Update playlist set videoName=#{videoName}, videoAddress=#{videoAddress} where username=#{username}")
    void updateVideoInfo(@Param("username") String username,@Param("videoName") String videoName,@Param("videoAddress")String videoAddress);
}
