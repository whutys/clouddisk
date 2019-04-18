package cn.clouddisk.mapper;

import cn.clouddisk.entity.UserFile;
import cn.clouddisk.entity.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserFileMapper {

    List<UserFile> getAllFiles(PageBean pageBean);

    int countSharedFile(String searchcontent);

    UserFile getFileById(@Param("id") int id);

    Integer insertFile(UserFile file);

    List<UserFile> getUserFiles(Map<String, Object> map);

    int countUserFiles(@Param("filepath") String filepath);

    void updateFileById(UserFile userFile);

    void deleteFileById(int id);

}
