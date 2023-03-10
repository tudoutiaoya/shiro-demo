package com.zzqedu;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthenticatingRealm {
    //自定义的登录认证方法，Shiro 的 login 方法底层会调用该类的认证方法完成登录认证
    //需要配置自定义的 realm 生效，在 ini 文件中配置，或 Springboot 中配置
    //该方法只是获取进行对比的信息，认证逻辑还是按照 Shiro 的底层认证逻辑完成认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 1. 获取身份信息
        String principal = authenticationToken.getPrincipal().toString();
        // 2. 获取凭证信息
        String password = new String((char[]) authenticationToken.getCredentials());
        System.out.println("认证用户信息：" + principal + "---" + password);
        // 3.1. 获取数据库中存储的家宴迭代3次的密码
        // 3.2 创建封装了校验逻辑的对象，将要对比的数据给该对象
        if(principal.equals("zhangsan")) {
            String pwdInfo = "736108d20b0d56ef36fffc4de514073c";
            AuthenticationInfo info = new SimpleAuthenticationInfo(
                    authenticationToken.getPrincipal(),
                    pwdInfo,
                    ByteSource.Util.bytes("zzqshuai"),
                    authenticationToken.getPrincipal().toString()
            );
            return info;
        }
        return null;
    }
}
