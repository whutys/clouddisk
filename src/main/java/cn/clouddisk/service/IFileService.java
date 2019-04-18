package cn.clouddisk.service;

import cn.clouddisk.entity.PageBean;
import cn.clouddisk.entity.UserFile;

import java.util.List;
import java.util.Map;

public interface IFileService {
    List<UserFile> getAllFiles(PageBean pageBean) ;

    int countShareFiles(String searchcontent);

    UserFile findFileById(int id);

    Integer insertFile(UserFile file);

    List<UserFile> getUserFiles(Map<String, Object> map);

    int countUserFiles(String filepath);

    void updateFileById(UserFile userFile);

    void deleteFileById(int id);

}
