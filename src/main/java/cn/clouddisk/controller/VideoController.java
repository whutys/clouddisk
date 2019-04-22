package cn.clouddisk.controller;

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
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@RequiresUser
@Controller
public class VideoController {

    private IPlayListService playListService;

    @Autowired
    public VideoController(IPlayListService playListService) {
        this.playListService = playListService;
    }

    @GetMapping("/videoPlay")
    public String videoPlay(String username, String filename, Model model) {
        try {
            filename = URLDecoder.decode(filename, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.addAttribute("url",
                "\\fileDir\\" +  username + File.separator + filename);
        model.addAttribute("filename", filename);
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
