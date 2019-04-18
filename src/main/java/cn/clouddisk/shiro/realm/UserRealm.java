package cn.clouddisk.shiro.realm;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.PermServiceImpl;
import cn.clouddisk.service.impl.RoleServiceImpl;
import cn.clouddisk.shiro.service.LoginService;
import cn.clouddisk.shiro.web.exception.user.CaptchaException;
import cn.clouddisk.shiro.web.exception.user.UserNotExistsException;
import cn.clouddisk.shiro.web.exception.user.UserPasswordNotMatchException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Autowired
    private PermServiceImpl permServiceImpl;
    @Autowired
    private LoginService loginService;

    @Override
    public String getName() {
        return "UserRealm";
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        List<String> roles;
        List<String> perms;
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (user.getId() == 1) {
            info.addRole("admin");
            info.addStringPermission("*:*");
        } else {
            perms = permServiceImpl.selectPermKeysByUserId(user.getId());
            roles = roleServiceImpl.selectRoleKeysByUserId(user.getId());
            info.addRoles(roles);
            info.addStringPermissions(perms);
        }
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) password = new String(upToken.getPassword());
        User user;
        try {
            user = loginService.login(username, password);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (Exception e) {
            log.info("用户【" + username + "】登录失败", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()),getName());
        return info;
    }

    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
//    public void clearCached(){
//        super.clearCache(ShiroUtils.getSubject().getPrincipals());
//    }
}
