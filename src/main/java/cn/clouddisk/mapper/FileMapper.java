package cn.clouddisk.mapper;

import cn.clouddisk.entity.MyFile;
import cn.clouddisk.entity.PageBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FileMapper {

    List<MyFile> getAllFiles(PageBean pageBean) throws Exception;

    int count(String searchcontent) throws Exception;

    String findFilepathById(int id) throws Exception;

    Integer insertFile(MyFile file) throws Exception;

    List<MyFile> getUserFiles(Map<String, Object> map) throws Exception;

    int countUserFiles(@Param("filepath") String filepath) throws Exception;

    void updateFileById(MyFile myFile) throws Exception;

    void deleteFileById(int id);

    String findFilenameById(int id);

}
