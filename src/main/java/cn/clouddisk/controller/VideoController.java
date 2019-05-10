package cn.clouddisk.controller;

import cn.clouddisk.service.IFileService;
import cn.clouddisk.service.IPlayListService;
import cn.clouddisk.utils.CrawlUtils;
import cn.clouddisk.utils.ShiroUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RequiresUser
@Controller
public class VideoController {

    private IPlayListService playListService;
    private IFileService fileService;

    @Autowired
    public VideoController(IPlayListService playListService, IFileService fileService) {
        this.playListService = playListService;
        this.fileService = fileService;
    }

    @GetMapping("/videoPlay")
    public String videoPlay(int id, Model model) {
        Map fileInfo = fileService.findFileById(id);
        model.addAttribute("url",
                "/fileDir/" + fileInfo.get("username") + File.separator + fileInfo.get("filename"));
        model.addAttribute("filename", fileInfo.get("filename"));
        return "videoplay";
    }

    @GetMapping("/vipPlayer")
    public String vipAnalysis(HttpSession httpSession, Model model) {
        String username = ShiroUtils.getUsername();
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
        String[] apis = {"http://jiexi.92fz.cn/player/vip.php?url=", "http://jqaaa.com/jx.php?url=",
                "http://api.bbbbbb.me/jx/?url=", "http://api.hlglwl.com/jx.php?url=",
                "http://vip.jlsprh.com/index.php?url=", "http://app.baiyug.cn:2019/vip/?url="};
        model.addAttribute("apis", apis);
        Map<String, String> searchengines = new HashMap<String, String>() {
            {
                put("爱奇艺", "https://so.iqiyi.com/so/q_");
                put("优酷", "https://so.youku.com/search_video/q_");
                put("腾讯视频", "https://v.qq.com/x/search/?q=");
                put("搜狐视频", "https://so.tv.sohu.com/mts?wd=");
                put("乐视视频", "http://so.le.com/s?wd=");
            }
        };
        model.addAttribute("searchengines",searchengines);
        return "vip_analysis";
    }

    @ResponseBody
    @GetMapping("/getTitle")
    public String getTitle(HttpSession httpSession, String url) {
        String username = ShiroUtils.getUsername();
        String videoName = CrawlUtils.getTitle(url);
//        httpSession.setAttribute("videoName", videoName);
        playListService.changeVideoInfo(username, videoName, url);
        return videoName;
    }

    @ResponseBody
    @GetMapping("/getVideoItem")
    public Map<String, String> getVideoItem(String url) {
        return CrawlUtils.crawlVideo(url);
    }
}
