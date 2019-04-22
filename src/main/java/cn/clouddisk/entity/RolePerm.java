package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 角色权限
 */
@Getter
@Setter
@ToString
public class RolePerm {
    private int role_id;
    private int perm_id;
}
