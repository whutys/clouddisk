package cn.clouddisk.service;

import cn.clouddisk.mapper.PlayListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayListService {
    @Autowired
    PlayListMapper playListMapper;

    public void setPlayList(String userName,String videoName){
        playListMapper.setVideoName(userName,videoName);
    }
    public String findVideoName(String userName){
        return  playListMapper.getVideoName(userName);
    }
    public void changeVideoName(String userName,String videoName){
        playListMapper.updateVideoName(userName,videoName);
    }
}
