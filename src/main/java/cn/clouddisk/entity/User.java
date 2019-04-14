package cn.clouddisk.entity;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class User  implements Serializable{
	private static final long serialVersionUID = 1L;
	private int id;
	@NotEmpty(message="用户名不能为空")
	@Length(min=6, max=20, message="用户名长度必须为6-20")
	private String userName;
	@NotEmpty(message="密码不能为空")
	@Length(min=6,max=20,message="密码长度必须为6-20")
	private String passWord;
	
	private String confirmPassword;
	@Email(message="邮箱地址不合法")
	private String email;
	
	@NotBlank(message="昵称不能为空")
	private String nickName;
	private int isVip;
	@NotEmpty
	private String inviteCode;
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getIsVip() {
		return isVip;
	}
	public void setIsVip(int isVip) {
		this.isVip = isVip;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", passWord=" + passWord + ", confirmPassword="
				+ confirmPassword + ", email=" + email + ", nickName=" + nickName + ", isVip=" + isVip + ", inviteCode="
				+ inviteCode + "]";
	}
	
}
