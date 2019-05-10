package cn.clouddisk.mapper;

import cn.clouddisk.entity.UserFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserFileMapper {

    List<UserFile> getAllFiles(@Param(value = "searchcontent") String searchcontent);

    int countSharedFile(String searchcontent);

    Map<String,Object> getFileById(@Param("id") int id);

    Integer insertUserFile(UserFile file);

    List<UserFile> getUserFilesByType(Map<String, Object> map);

    int countUserFiles(UserFile userFile);

    void updateFileById(Map fileInfo);

    void deleteFileById(int id);

}
