package cn.clouddisk.service.impl;

import cn.clouddisk.mapper.PlayListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PlayListService {
    @Autowired
    PlayListMapper playListMapper;

    public void setPlayList(String userName,String videoName){
        playListMapper.setVideoName(userName,videoName);
    }
    public Map<String,String> findVideoInfo(String userName){
        return  playListMapper.getVideoName(userName);
    }
    public void changeVideoInfo(String userName,String videoName,String videoAddress){
        playListMapper.updateVideoInfo(userName,videoName,videoAddress);
    }
}
