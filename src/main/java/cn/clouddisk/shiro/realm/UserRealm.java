package cn.clouddisk.shiro.realm;

import cn.clouddisk.entity.User;
import cn.clouddisk.service.impl.PermService;
import cn.clouddisk.service.impl.RoleService;
import cn.clouddisk.shiro.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermService permService;
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
            perms = permService.selectPermsByUserId(user.getId());
            roles = roleService.selectRoleKeys(user.getId());
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
        User user = null;
        try {
            user = loginService.login(username, password);
        } catch (Exception e) {

        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    public void clearCachedAuthorizationInfo() {
        this.clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
    }
}
