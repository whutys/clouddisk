package cn.clouddisk.controller;

import cn.clouddisk.entity.UserFile;
import cn.clouddisk.service.IFileService;
import cn.clouddisk.utils.ShiroUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@RequiresUser
@Controller
@PropertySource("classpath:settings.properties")
//@RequestMapping("/jsp")
public class FileController {
    @Value("${fileDir}")
    private String storePath; // 存储目录
    @Value("${category}")
    private String category;//文件夹

    private IFileService fileService;

    @Autowired
    public FileController(IFileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/deletefile")
    public String deleteFile(HttpServletRequest request, int id) {
        // 判断该用户是否拥有此文件
        try {
            Map fileInfo = fileService.findFileById(id);
            String username = (String) fileInfo.get("username");
            String login_user = ShiroUtils.getUsername();
            String filename = (String) fileInfo.get("filename"); // 查出文件名
            if (username != null && login_user.equals(username)) {
                // 从硬盘上删除文件
                String storepath = storePath + login_user + File.separator + filename;
                System.out.println(storepath);
                File file = new File(storepath);
                if (!file.exists() || file.delete()) {
                    fileService.deleteFileById(id); // 删除数据库的该文件记录
                }
                return "redirect:/userHome";
            } else { // 不通过，可能是人为篡改数据，转发至全局消息页面
                request.setAttribute("globalmessage", "该文件可能不属于你");
                return "forward:/message.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("globalmessage", "该文件可能不属于你");
            return "forward:/message.html";
        }

    }

    @GetMapping(value = "/userHome")
    public String searchUserFiles(HttpServletRequest request) {
        // 根据用户查找出它所有的文件
        Map<String, Object> map = new HashMap<>();
        map.put("owner", ShiroUtils.getUserId());
        String filetype = request.getParameter("filetype");
        if (filetype == null) {
            filetype = (String) request.getSession().getAttribute("filetype");
            if (filetype == null) {
                filetype = "all";
            }
        }
        request.getSession().setAttribute("filetype", filetype);
        List<String> types = null;
        JSONObject jsonObject = JSON.parseObject(category);
        if ("others".equals(filetype)) {
            map.put("others", true);
            types = new ArrayList<>();
            //List<String> categoryList=new ArrayList<>(Arrays.asList(category.split("/")));
            for (String string : jsonObject.keySet()) {
                types.addAll(Arrays.asList(jsonObject.get(string).toString().split("/")));
            }
        } else if (!"all".equals(filetype)) {
            types = new ArrayList<>(Arrays.asList(jsonObject.get(filetype).toString().split("/")));
        }
        map.put("types", types);
        List<UserFile> files = fileService.findUserFilesByType(map);
        PageInfo<UserFile> pageInfo = new PageInfo<>(files);

        request.setAttribute("pageInfo", pageInfo);
        return "userhome";
    }

    @RequestMapping("/searchfile")
    public String searchFile(Model model, String searchcontent, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserFile> list = fileService.findAllFiles(searchcontent);
        PageInfo<UserFile> pageInfo = new PageInfo<>(list);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("searchcontent", searchcontent);
        return "showsearchfiles";
    }

    @GetMapping("/changefilestatus")
    public String changeFileStatus(int id, HttpServletRequest request) {
        // 把canshare修改进数据库
        try {
            // 检查该文件是否属于该用户,否则不允许修改文件状态
            Map fileInfo = fileService.findFileById(id);
            String username = (String) fileInfo.get("username");
            String login_user = ShiroUtils.getUsername();
            if (username != null && login_user.equals(username)) {
                fileInfo.replace("canshare",!(boolean)fileInfo.get("canshare"));
                fileService.updateFileById(fileInfo);
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
        return "redirect:/userHome";
    }

    @GetMapping("/linkFiles")
    public String linkFiles() {
        String username = ShiroUtils.getUsername();
        File file = new File(storePath + username);
        linkFiles(file);
        return "redirect:/userHome";
    }

    public void linkFiles(File file) {
        if (file.isDirectory()) {
            Arrays.stream(file.listFiles()).forEach(this::linkFiles);
        } else {
            UserFile userFile = new UserFile();
            userFile.setFilename(file.getName());
            String path = file.getAbsolutePath();
            userFile.setFilepath(path.substring(storePath.length(), path.lastIndexOf(File.separatorChar)).replaceAll("\\\\","/"));
            userFile.setOwner(ShiroUtils.getUserId());
            if (fileService.countUserFiles(userFile) == 0) {
                userFile.setFilesize(file.length() / 1024 + 1);
                userFile.setCreatetime(new Date(file.lastModified()));
                fileService.insertFile(userFile);
            }
        }
    }
}
