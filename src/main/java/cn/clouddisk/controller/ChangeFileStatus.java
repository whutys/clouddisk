package cn.clouddisk.controller;

import cn.clouddisk.entity.UserFile;
import cn.clouddisk.service.impl.FileServiceImpl;
import cn.clouddisk.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
@RequiresUser
@Controller
//@RequestMapping("/jsp")
public class ChangeFileStatus {

	@Autowired
	private FileServiceImpl fileServiceImpl;
	
	@RequestMapping("/changefilestatus")
	public String changeFileStatus(UserFile userFile, HttpServletRequest request) {
		// 把canshare修改进数据库
				try {
					// 检查该文件是否属于该用户,否则不允许修改文件状态
					String username = fileServiceImpl.findFileById(userFile.getId()).getFilepath();
					String login_user = ShiroUtils.getUsername();
					if (username != null && login_user.equals(username)) {
						fileServiceImpl.updateFileById(userFile);
					} else { // 不通过，可能是人为篡改数据，转发至首页
						request.setAttribute("globalmessage", "该文件可能不属于你");
						return "/message";
					}
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("globalmessage", "未知错误，可能是参数异常");
					return "/message";
				}

				// 转发到searchUserFile显示用户的文件
				return "forward:/userHome";
	}
}
