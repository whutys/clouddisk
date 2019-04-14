package cn.clouddisk.service;

import cn.clouddisk.entity.MyFile;
import cn.clouddisk.entity.PageBean;
import cn.clouddisk.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FileService {

	@Autowired
	private FileMapper fileMapper;
	
	public  List<MyFile> getAllFiles(PageBean pageBean) throws Exception{
		return fileMapper.getAllFiles(pageBean);
	}
	
	public  int countShareFiles(String searchcontent)throws Exception{
		return fileMapper.count(searchcontent);
	}
	
	public  String findFilepathById(int id) throws Exception{
		return fileMapper.findFilepathById(id);
	}
	
	public  Integer insertFile(MyFile file) throws Exception{
		return fileMapper.insertFile(file);
	}

	public  List<MyFile> getUserFiles(Map<String, Object> map) throws Exception {
		return fileMapper.getUserFiles(map);
	}

	public  int countUserFiles(String filepath) throws Exception {
		return fileMapper.countUserFiles(filepath);
		
	}

	public  void updateFileById(MyFile myFile) throws Exception{
		 fileMapper.updateFileById(myFile);
	}

	public  void deleteFileById(int id) {
		fileMapper.deleteFileById(id);
	}

	public  String findFilenameById(int id) {
		return fileMapper.findFilenameById(id);
	}

}
