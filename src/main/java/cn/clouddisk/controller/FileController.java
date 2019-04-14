package cn.clouddisk.controller;

import cn.clouddisk.entity.MyFile;
import cn.clouddisk.entity.PageBean;
import cn.clouddisk.service.FileService;
import cn.clouddisk.service.UserService;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller
public class FileController {
	private static Properties properties;
	static {
		properties = new Properties();
		try {
			properties.load(Resources.getResourceAsStream("settings.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static final String storePath = "E:"+File.separatorChar+properties.getProperty("storePath"); // 存储目录 E:\\BaiduYunDownload
	@Autowired
	private UserService userService;
	@Autowired
	private FileService fileService;

	@RequestMapping("/deletefile")
	public String deleteFile(HttpServletRequest request, int id) {
		// 判断该用户是否拥有此文件
		try {
			String username = fileService.findFilepathById(id);
			String login_user = (String) request.getSession().getAttribute("user_name");
			String filename = fileService.findFilenameById(id); // 查出文件名
			if (username != null && login_user.equals(username)) {
				// 从硬盘上删除文件
				String storepath = storePath + File.separator + login_user + File.separator + filename;
				System.out.println(storepath);
				File file = new File(storepath);
				if (file.exists()) {
					file.delete();
				}
				fileService.deleteFileById(id); // 删除数据库的该文件记录
				return "redirect:/searchUserFile";
			} else { // 不通过，可能是人为篡改数据，转发至全局消息页面
				request.setAttribute("globalmessage", "该文件可能不属于你");
				return "forward:/message.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("globalmessage", "该文件可能不属于你");
			return "forward:/message.jsp";
		}

	}

	@RequestMapping(value = "/searchUserFile")
	public String searchUserFile(HttpServletRequest request, PageBean pageBean)
			throws Exception {
		// 根据用户查找出它所有的文件
		String filepath;// file表的文件路径就是所属的用户的用户名
		List<MyFile> list;
		int countUserFiles = 0;
		try {
			String username = (String) request.getSession().getAttribute("user_name");
			// session没有用户名说明没有登陆，让他转去主页
			if (username == null || "".equals(username)) {
				return "redirect:/signInPage";
			}
			filepath = username;
			Map<String, Object> map = new HashMap<>();
			countUserFiles = fileService.countUserFiles(filepath);
			map.put("filepath", filepath);
			String filetype = (String) request.getParameter("filetype");
			if (filetype == null) {
				filetype = (String) request.getSession().getAttribute("filetype");
				if (filetype == null) {
					filetype = "all";
				}
			}
			request.getSession().setAttribute("filetype", filetype);
			List<String> types = null;
			if ("others".equals(filetype)) {
				map.put("others", true);
				types = new ArrayList<>();
				List<String> category=new ArrayList<>(Arrays.asList(properties.getProperty("category").split("/")));
				for (String string : category) {
					types.addAll(Arrays.asList(properties.getProperty(string).split("/")));
				}
			} else if ("all".equals(filetype)) {
			} else {
				types = new ArrayList<>(Arrays.asList(properties.getProperty(filetype).split("/")));
			}
			map.put("types", types);
			list = fileService.getUserFiles(map);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/signInPage";
		}
		Integer isVip = (Integer) request.getAttribute("isVip");
		if (isVip == null) { // 没有上传文件之前会调用到这里的代码，上传的时候在uploadAction里会添加isvip
			isVip = userService.isVip(filepath);
			request.setAttribute("isVip", isVip);
		}
		// 拿到每页的数据，每个元素就是一条记录
		pageBean.setList(list);
		pageBean.setPagesize(countUserFiles);
		pageBean.setTotalrecord(countUserFiles);

		request.setAttribute("pagebean", pageBean);
		return "userhome";
	}

	@RequestMapping("/searchfile")
	public String searchFile(Model model, PageBean pageBean) {
		List<MyFile> list;
		try {
			if (pageBean.getPagesize() == 0) {
				pageBean.setPagesize(5);
			}
			list = fileService.getAllFiles(pageBean);
			pageBean.setTotalrecord(fileService.countShareFiles(pageBean.getSearchcontent()));
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/index.jsp";
		}
		// 拿到每页的数据，每个元素就是一条记录
		pageBean.setList(list);
		// pageBean.setCurrentpage(currentpage);
		// pageBean.setPagesize(pagesize);

		model.addAttribute("pagebean", pageBean);
		// model.addAttribute("searchcontent", pageBean.getSearchcontent());

		return "showsearchfiles";
	}

	@RequestMapping("/videoPlay")
	public String videoPlay(String userName, String filename, Model model) {
		try {
			filename=URLDecoder.decode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("url",
				File.separator + storePath.substring(storePath.indexOf(File.separatorChar) + 1) + File.separator + userName + File.separator + filename);
		model.addAttribute("filename", filename);
		return "videoplay";
	}
	@RequestMapping("/vipPlayer")
	public String vipAnalysis() {
		return "vip_analysis";
	}
}
