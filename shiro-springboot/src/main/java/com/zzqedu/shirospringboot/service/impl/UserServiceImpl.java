package com.zzqedu.shirospringboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzqedu.shirospringboot.entity.User;
import com.zzqedu.shirospringboot.service.UserService;
import com.zzqedu.shirospringboot.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author zzq12
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-01-13 11:34:52
*/
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getUserInfoByName(String username) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getName, username);
        return userMapper.selectOne(lambdaQueryWrapper);
    }

    @Override
    public int saveUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public List<String> getUserRolesByName(String name) {
        return userMapper.getUserRolesByName(name);
    }

    @Override
    public List<String> getUserPermissions(List<String> roles) {
        return userMapper.getUserPermissions(roles);
    }
}




