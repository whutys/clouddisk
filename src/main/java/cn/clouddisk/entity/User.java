package cn.clouddisk.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * 用户
 */
@Getter
@Setter
@ToString
@Component
//@Scope("prototype")
public class User extends BaseEntity {
    private int id;
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 6, max = 20, message = "用户名长度必须为6-20")
    private String username;
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 20, message = "密码长度必须为6-20")
    private String password;

    private String confirmPassword;
    @Email(message = "邮箱地址不合法")
    private String email;

    @NotBlank(message = "昵称不能为空")
    private String nickname;

    private int isVip;
    @NotEmpty
    private String inviteCode;
    private String salt;
    private List<Role> roles;
}
