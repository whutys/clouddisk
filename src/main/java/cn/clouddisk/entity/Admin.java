package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Getter
@Setter
@ToString
@Component
public class Admin {
    private int id;
    private  String adminName;
    private  String adminPassWord;
}
