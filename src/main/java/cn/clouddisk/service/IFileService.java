package cn.clouddisk.service;

import cn.clouddisk.entity.UserFile;

import java.util.List;
import java.util.Map;

public interface IFileService {
    List<UserFile> findAllFiles(String searchcontent) ;

    int countShareFiles(String searchcontent);

    UserFile findFileById(int id);

    Integer insertFile(UserFile file);

    List<UserFile> findUserFilesByType(Map<String, Object> map);

    int countUserFiles(UserFile userFile);

    void updateFileById(UserFile userFile);

    void deleteFileById(int id);

}
