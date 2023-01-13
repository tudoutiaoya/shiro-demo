package com.zzqedu.shirospringboot.config;

import com.zzqedu.shirospringboot.entity.User;
import com.zzqedu.shirospringboot.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    // 自定义授权的方法
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    // 自定义登录认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 1. 从token中获取到用户提交的username
        String name = token.getPrincipal().toString();
        // 2. 调用业务层获取用户信息（从数据库中）
        User user = userService.getUserInfoByName(name);
        // 3. 判断并将数据完成封装
        if(user != null) {
            // 把查询出来的用户信息、密码、加密盐值、Realm名称包装进SimpleAuthenticationInfo返回
            AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPwd(), ByteSource.Util.bytes("zzqshuai"), getName());
            return info;
        }
        return null;
    }
}
