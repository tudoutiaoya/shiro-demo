package com.zzqedu;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class shiroDemo {
    public static void main(String[] args) {
        // 1. 初始化获取SecurityManager
        IniSecurityManagerFactory factory =
                new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        // 2. 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        // 3. 创建token对象，web应用用户名密码从页面传递
        AuthenticationToken token = new UsernamePasswordToken("zhangsan","123456");
        // 4. 完成登录
        try {
            subject.login(token);
            System.out.println("登录成功");
            // 判断角色
            boolean isRole1 = subject.hasRole("role1");
            System.out.println(isRole1);
            // 判断权限
            boolean isPermitted = subject.isPermitted("user:insert");
            System.out.println(isPermitted);
            //也可以用 checkPermission 方法，但没有返回值，没权限抛 AuthenticationException
            subject.checkPermission("user:select12");
        } catch (UnknownAccountException e) {
            e.printStackTrace();
            System.out.println("用户不存在");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("密码错误");
        }   catch (AuthenticationException e) {
            e.printStackTrace();
        }
    }
}
