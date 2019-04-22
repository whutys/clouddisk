package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

/**
 * 角色
 */
@Component
@Getter
@Setter
@ToString
public class Role extends BaseEntity{
    private int id;
    private String roleName;
    private String roleKey;
}
