package com.renguangli.shiro.shiro;

import com.renguangli.shiro.User;
import com.renguangli.shiro.service.PermissionService;
import com.renguangli.shiro.service.RoleService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * ShiroRealm
 *
 * @author renguangli 2018/10/29 20:16
 * @since JDK 1.8
 */
@Service("shiroRealm")
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = roleService.listRolesByUsername(username);
        Set<String> permissions = permissionService.listPermissionsByUsername(username);
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(permissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        String username = token.getUsername();
        User user = new User(1L, "admin", "admin", LocalDateTime.now());
      /*  if (user == null) { // 用户不存在
            throw new UnknownAccountException();
        }
        if (User.LOCKED.equals(user.getLocked())) {// 账号锁定
            throw new LockedAccountException();
        }
        long days = ChronoUnit.DAYS.between(user.getPasswordUpdateDatetime(), LocalDateTime.now());
        if (user.getExpired() < days) { // 密码过期
            throw new ExpiredCredentialsException();
        }*/
        String password = user.getPassword();
        //构造一个用户认证信息并返回，后面会通过这个和token的pwd进行对比。
        return new SimpleAuthenticationInfo(username,password,this.getName());
    }

}

