package cn.clouddisk.controller;

import cn.clouddisk.entity.MyFile;
import cn.clouddisk.entity.PageBean;
import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.FileService;
import cn.clouddisk.service.impl.PlayListService;
import cn.clouddisk.utils.MyUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

@Controller
@PropertySource("classpath:settings.properties")
//@RequestMapping("/jsp")
public class FileController {
    @Value("${storePath}")
    private String storePath; // 存储目录 E:\\BaiduYunDownload
    @Value("${category}")
    private String category;
    private final FileService fileService;
    private final PlayListService playListService;

    @Autowired
    public FileController(FileService fileService, PlayListService playListService) {
        this.fileService = fileService;
        this.playListService = playListService;
    }

    @RequestMapping("/deletefile")
    public String deleteFile(HttpServletRequest request, int id) {
        // 判断该用户是否拥有此文件
        try {
            String username = fileService.findFilepathById(id);
            String login_user = ((User) request.getSession().getAttribute("user")).getUsername();
            String filename = fileService.findFilenameById(id); // 查出文件名
            if (username != null && login_user.equals(username)) {
                // 从硬盘上删除文件
                String storepath = storePath + File.separator + login_user + File.separator + filename;
                System.out.println(storepath);
                File file = new File(storepath);
                if (file.exists() && file.delete()) {
                    fileService.deleteFileById(id); // 删除数据库的该文件记录
                }
                return "redirect:/userHome";
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

    @RequestMapping(value = "/userHome")
    public String searchUserFile(HttpServletRequest request, PageBean pageBean, Model model) {
        // 根据用户查找出它所有的文件
        String filepath;// file表的文件路径就是所属的用户的用户名
        List<MyFile> list;
        int countUserFiles;
        try {
            User user = (User) request.getSession().getAttribute("user");
            // session没有用户名说明没有登陆，让他转去主页
            if (user == null) {
                return "redirect:/signInPage";
            }
            model.addAttribute("user", user);
            String username = user.getUsername();
            filepath = username;
            Map<String, Object> map = new HashMap<>();
            countUserFiles = fileService.countUserFiles(filepath);
            map.put("filepath", filepath);
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
            list = fileService.getUserFiles(map);

//            request.getSession().setAttribute("videoName", videoName);
//            request.getSession().setAttribute();
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/signInPage";
        }
//		Integer isVip = (Integer) request.getAttribute("isVip");
//		if (isVip == null) { // 没有上传文件之前会调用到这里的代码，上传的时候在uploadAction里会添加isvip
//			isVip = userService.isVip(filepath);
//			request.setAttribute("isVip", isVip);
//		}
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
            filename = URLDecoder.decode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("url",
                File.separator + storePath.substring(storePath.indexOf(File.separatorChar) + 1) + File.separator + userName + File.separator + filename);
        model.addAttribute("filename", filename);
        return "videoplay";
    }

    @RequestMapping("/vipPlayer")
    public String vipAnalysis(HttpSession httpSession, Model model) {
        User user = (User) httpSession.getAttribute("user");
        String username = user.getUsername();
        //播放信息
        Map<String, String> videoInfo = playListService.findVideoInfo(username);
        if (videoInfo == null) {
            playListService.setPlayList(username, "无");
        } else {
            String videoName = videoInfo.get("videoName");
            if (videoName == null) {
                videoName = "无";
                playListService.setPlayList(username, videoName);
            }
        }
        model.addAttribute("videoInfo", videoInfo);
        return "vip_analysis";
    }

    @ResponseBody
    @GetMapping("/getTitle")
    public String getTitle(HttpSession httpSession, String url) {
        User user = (User) httpSession.getAttribute("user");
        String username = null;
        if (user != null)
            username = user.getUsername();
        String videoName = MyUtils.getTitle(url);
//        httpSession.setAttribute("videoName", videoName);
        playListService.changeVideoInfo(username, videoName, url);
        return videoName;
    }

    @ResponseBody
    @GetMapping("/getVideoItem")
    public Map<String, String> getVideoItem(String url) {
        return MyUtils.crawlVideo(url);
    }

}
