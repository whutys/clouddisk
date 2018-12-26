package cn.clouddisk.controller;

import cn.clouddisk.entity.MyFile;
import cn.clouddisk.entity.User;
import cn.clouddisk.service.FileService;
import cn.clouddisk.service.UserService;
import cn.clouddisk.utils.MyUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.io.Resources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@Controller
public class FileUpDown {

    @Value("${storePath}")
    private String storePath; // 存储目录 E:\\BaiduYunDownload
    private static final long time = System.currentTimeMillis();
    private static final int normallimit = 1000 * 1024 * 1024; // 普通用户上传单个文件的最大体积 1G
    private static final long viplimit = 2000 * 1024 * 1024; // 普通用户上传单个文件的最大体积 2G
    private static final int factor = 1024 * 1024; // Mb到字节的转换因子

    @Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private MyFile myFile;

    @ResponseBody
    @PostMapping(value = "/uploadfile", produces = "text/html;charset=UTF-8")
    public String upLoad(@RequestParam("file") MultipartFile multipartFile, HttpSession session) {
        String fileFileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID() + MyUtils.getFileType(fileFileName);
        Map<String, Object> map = new HashMap<>();
        // session域存的username和传进来的username一致，说明用户名没有造假
        User user = (User) session.getAttribute("user");
        String user_name = user.getUserName();
        int isvip = 0;
        try {
            isvip = user.getIsVip();//userService.isVip(user_name);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        File filetostore = new File(storePath + File.separator + user_name, fileFileName);
        long size = multipartFile.getSize(); // 上传文件的大小
        if (user == null) {
            map.put("error", "未登录");
        } else if (size == 0) {
            map.put("error", "文件大小不能为0");
        } else {
            if (filetostore.exists()) {
                int index = fileFileName.lastIndexOf(".");
                StringBuilder sb;
                String str = Long.toString((System.currentTimeMillis() - time) >> 3);
                if (index < 0) {
                    sb = new StringBuilder(fileFileName);
                    sb.append("_").append(str);
                } else {
                    sb = new StringBuilder(fileFileName.substring(0, index));
                    sb.append("_").append(str);
                    sb.append(fileFileName.substring(index));
                }
                fileFileName = sb.toString();
                filetostore = new File(storePath + File.separator + user_name, fileFileName);
            }
            if (isvip == 0 && size > normallimit) {
                map.put("error", "普通用户最大只能上传" + normallimit / factor + "Mb的文件");
            } else if (isvip == 1 && size > viplimit) {
                map.put("error", "VIP用户最大只能上传" + viplimit / factor + "Mb的文件");
            } else {
                // todo 检查用户的云空间是否超过限额
                // 验证全部通过，把文件复制到本地硬盘的用户的目录下
                Integer fileid = null;
                try {
                    multipartFile.transferTo(filetostore);
                    // FileUtils.copyFile(file, store); // 上传文件到本地硬盘
                    // 把文件信息存入数据库
                    myFile.setCreatetime(new java.util.Date());
                    myFile.setFilename(fileFileName);
                    myFile.setFilepath(user_name);
                    myFile.setFilesize(String.valueOf(size / 1024 + 1));
                    myFile.setCanshare(0);
                    fileid = fileService.insertFile(myFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (filetostore.exists()) { // 中途出现异常，把拷贝的文件删除
                        filetostore.delete();
                    }
                    if (fileid != null) {
                        fileService.deleteFileById(fileid);
                    }
                    map.put("error", "上传失败！请重试");
                }
            }
        }
        System.out.println(JSONObject.parseObject(JSON.toJSONString(map)));
        return JSONObject.parseObject(JSON.toJSONString(map)).toJSONString();
    }

    @RequestMapping("/download")
    public String downLoad(HttpServletRequest request, HttpServletResponse response, MyFile myFile) {
        FileInputStream in = null;
        try {
            String path = fileService.findFilepathById(myFile.getId()); // 相对于/upload的路径
            if (path == null || "".equals(path)) {
                request.setAttribute("message", "对不起，您要下载的资源已被删除");
                return "message";
            }
            path = storePath + File.separator + path;
            File file = new File(path + File.separator + myFile.getFilename());
            // 通知浏览器以下载方式打开
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(myFile.getFilename(), "UTF-8"));

            in = new FileInputStream(file);
            int len = 0;
            byte buffer[] = new byte[1024];
            OutputStream out = response.getOutputStream();
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return "message";
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
