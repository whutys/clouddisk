package cn.clouddisk.mapper;

import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface PlayListMapper {
    @Insert("insert into playlist(userName,videoName) values(#{userName},#{videoName})")
    void setVideoName(@Param("userName") String userName,@Param("videoName") String videoName);
    @Select("select videoName,videoAddress from playlist where userName=#{userName}")
    Map<String,String> getVideoName(@Param("userName") String userName);
    @Update("Update playlist set videoName=#{videoName}, videoAddress=#{videoAddress} where userName=#{userName}")
    void updateVideoInfo(@Param("userName") String userName,@Param("videoName") String videoName,@Param("videoAddress")String videoAddress);
}
