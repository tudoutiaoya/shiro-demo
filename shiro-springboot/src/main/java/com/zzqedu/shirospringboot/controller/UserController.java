package com.zzqedu.shirospringboot.controller;

import com.zzqedu.shirospringboot.entity.User;
import com.zzqedu.shirospringboot.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/myController")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("/{name}")
    public User getUser(@PathVariable("name")String name) {
        return userService.getUserInfoByName(name);
    }

    @GetMapping("/userLogin")
    public String userLogin(String name, String pwd) {
        // 1. 获取Subject对象
        Subject subject = SecurityUtils.getSubject();
        // 2. 封装请求数据到token对象中
        AuthenticationToken token = new UsernamePasswordToken(name,pwd);
        // 3. 调用login方法进行登录认证
        try {
            subject.login(token);
            return "登录成功";
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "登录失败";
        }
    }


}
