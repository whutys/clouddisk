package cn.clouddisk.service;

import java.util.Map;

public interface IPlayListService {
    void setPlayList(String userName, String videoName);

    Map<String, String> findVideoInfo(String userName);

    void changeVideoInfo(String userName, String videoName, String videoAddress);
}
