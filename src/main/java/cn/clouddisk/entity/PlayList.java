package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * 播放记录
 */
@Getter
@Setter
@Component
public class PlayList {
    int id;
    String userName;
    String videoName;
    String videoAddress;
}
