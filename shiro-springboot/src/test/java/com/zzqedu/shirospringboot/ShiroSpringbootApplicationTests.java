package com.zzqedu.shirospringboot;

import com.zzqedu.shirospringboot.entity.User;
import com.zzqedu.shirospringboot.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Resource
    UserService userService;

    @Test
    void contextLoads() {
        User zzq = new User();
        zzq.setName("zzq");
        zzq.setPwd("123456");
        zzq.setRid(0L);
        Md5Hash md5Hash = new Md5Hash(zzq.getPwd(), "zzqshuai", 3);
        zzq.setPwd(md5Hash.toHex());
        userService.saveUser(zzq);

        User bfw = new User();
        bfw.setName("zzq");
        bfw.setPwd("123456");
        bfw.setRid(0L);
        Md5Hash md5Hash2 = new Md5Hash(bfw.getPwd(), "zzqshuai", 3);
        bfw.setPwd(md5Hash2.toHex());
        userService.saveUser(bfw);
    }

}
