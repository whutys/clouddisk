package cn.clouddisk.service.impl;

import cn.clouddisk.entity.UserFile;
import cn.clouddisk.mapper.UserFileMapper;
import cn.clouddisk.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FileServiceImpl implements IFileService {

    private UserFileMapper userFileMapper;

    @Autowired
    public FileServiceImpl(UserFileMapper userFileMapper) {
        this.userFileMapper = userFileMapper;
    }

    public List<UserFile> findAllFiles(String searchcontent) {
        return userFileMapper.getAllFiles(searchcontent);
    }

    public int countShareFiles(String searchcontent) {
        return userFileMapper.countSharedFile(searchcontent);
    }

    public Map<String,Object> findFileById(int id) {
        return userFileMapper.getFileById(id);
    }

    public Integer insertFile(UserFile file) {
        return userFileMapper.insertUserFile(file);
    }

    public List<UserFile> findUserFilesByType(Map<String, Object> map) {
        List<UserFile> userFilesList = userFileMapper.getUserFilesByType(map);
        return userFilesList;
    }

    public int countUserFiles(UserFile userFile) {
        return userFileMapper.countUserFiles(userFile);
    }

    public void updateFileById(Map fileInfo) {
        userFileMapper.updateFileById(fileInfo);
    }

    public void deleteFileById(int id) {
        userFileMapper.deleteFileById(id);
    }

}
