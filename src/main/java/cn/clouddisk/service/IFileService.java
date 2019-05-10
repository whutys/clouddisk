package cn.clouddisk.service;

import cn.clouddisk.entity.UserFile;

import java.util.List;
import java.util.Map;

public interface IFileService {
    List<UserFile> findAllFiles(String searchcontent) ;

    int countShareFiles(String searchcontent);

    Map<String,Object> findFileById(int id);

    Integer insertFile(UserFile file);

    List<UserFile> findUserFilesByType(Map<String, Object> map);

    int countUserFiles(UserFile userFile);

    void updateFileById(Map fileInfo);

    void deleteFileById(int id);

}
