package cn.clouddisk.controller;

import cn.clouddisk.entity.User;
import cn.clouddisk.entity.UserFile;
import cn.clouddisk.service.IFileService;
import cn.clouddisk.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RequiresUser
@Controller
public class FileUpDown {

    @Value("${fileDir}")
    private String fileDir; // 存储目录 E:\\BaiduYunDownload
    private static final long time = System.currentTimeMillis();
    private static final int normallimit = 1000 * 1024 * 1024; // 普通用户上传单个文件的最大体积 1G
    private static final long viplimit = 2000 * 1024 * 1024; // vip用户上传单个文件的最大体积 2G
    private static final int factor = 1024 * 1024; // Mb到字节的转换因子

    @Autowired
    private IFileService fileService;

    @ResponseBody
    @PostMapping(value = "/uploadfile")
    public Map<String, Object> upLoad(@RequestParam("file") MultipartFile multipartFile,UserFile userFile) {
        String fileFileName = multipartFile.getOriginalFilename();
        Map<String, Object> map = new HashMap<>();
        User user = ShiroUtils.getUser();
        String user_name = user.getUsername();
        int isvip = 0;
        try {
            isvip = user.getIsVip();//userService.isVip(user_name);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        File dir = new File(fileDir + File.separator + user_name);
        if (!dir.exists()) dir.mkdirs();
        File fileToSave = new File(fileDir + File.separator + user_name, fileFileName);
        long size = multipartFile.getSize(); // 上传文件的大小
        if (size == 0) {
            map.put("error", "文件大小不能为0");
        } else {
            if (fileToSave.exists()) {
                int index = fileFileName.lastIndexOf(".");
                String uuid = UUID.randomUUID() + ((index < 0 ? "" : fileFileName.substring(index)));
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
                fileToSave = new File(fileDir + File.separator + user_name, fileFileName);
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
                    multipartFile.transferTo(fileToSave);
                    // FileUtils.copyFile(file, store); // 上传文件到本地硬盘
                    // 把文件信息存入数据库
                    userFile.setCreatetime(new java.util.Date());
                    userFile.setFilename(fileFileName);
                    userFile.setFilepath(user_name);
                    userFile.setFilesize(String.valueOf(size / 1024 + 1));
                    userFile.setCanshare(0);
                    fileid = fileService.insertFile(userFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    if (fileToSave.exists()) { // 中途出现异常，把拷贝的文件删除
                        fileToSave.delete();
                    }
                    if (fileid != null) {
                        fileService.deleteFileById(fileid);
                    }
                    map.put("error", "上传失败！请重试");
                }
            }
        }
//        System.out.println(JSONObject.parseObject(JSON.toJSONString(map)));
        return map;//JSONObject.parseObject(JSON.toJSONString(map)).toJSONString();
    }

    @GetMapping("/download")
    public String downLoad(HttpServletRequest request, HttpServletResponse response, int id) {
        FileInputStream in = null;
        try {
            UserFile userFile = fileService.findFileById(id); // 相对于/upload的路径
            if (userFile == null ) {
                request.setAttribute("message", "对不起，您要下载的资源已被删除");
                return "message";
            }
            File file = new File(fileDir + File.separator + userFile.getFilepath() + File.separator + userFile.getFilename());
            // 通知浏览器以下载方式打开
            response.setHeader("content-disposition",
                    "attachment;filename=" + URLEncoder.encode(userFile.getFilename(), "UTF-8"));

            in = new FileInputStream(file);
            int len;
            byte[] buffer = new byte[1024];
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
