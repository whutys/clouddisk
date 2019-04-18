package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@ToString
@Component
public class UserFile extends BaseEntity {
    private int id;
    private String filename;
    private String filepath;
    private String filesize;
    private Date createtime;
    private int canshare;

}
