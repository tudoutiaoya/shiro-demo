package com.zzqedu.shirospringboot.service;

import com.zzqedu.shirospringboot.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author zzq12
* @description 针对表【user】的数据库操作Service
* @createDate 2023-01-13 11:34:52
*/
public interface UserService {

    User getUserInfoByName(String username);

    int saveUser(User user);

}
