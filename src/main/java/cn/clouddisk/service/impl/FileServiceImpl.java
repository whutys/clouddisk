package cn.clouddisk.service.impl;

import cn.clouddisk.entity.PageBean;
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
public class FileServiceImpl implements IFileService{

	@Autowired
	private UserFileMapper userFileMapper;
	
	public  List<UserFile> getAllFiles(PageBean pageBean) {
		return userFileMapper.getAllFiles(pageBean);
	}
	
	public  int countShareFiles(String searchcontent){
		return userFileMapper.countSharedFile(searchcontent);
	}
	
	public  UserFile findFileById(int id) {
		return userFileMapper.getFileById(id);
	}
	
	public  Integer insertFile(UserFile file) {
		return userFileMapper.insertFile(file);
	}

	public  List<UserFile> getUserFiles(Map<String, Object> map){
		return userFileMapper.getUserFiles(map);
	}

	public  int countUserFiles(String filepath){
		return userFileMapper.countUserFiles(filepath);
	}

	public  void updateFileById(UserFile userFile){
		 userFileMapper.updateFileById(userFile);
	}

	public  void deleteFileById(int id) {
		userFileMapper.deleteFileById(id);
	}

}
