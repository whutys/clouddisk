package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 权限实体
 */
@Component
@Getter
@Setter
@ToString
public class Perm extends BaseEntity{
    private int id;
    private int parentId;
    private String permName;
    private String url;
    private String permKey;
}
