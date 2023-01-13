package com.zzqedu.shirospringboot.mapper;

import com.zzqedu.shirospringboot.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author zzq12
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-01-13 11:34:52
* @Entity com.zzqedu.shirospringboot.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    @Select("select name from role\n" +
            "            where id in (select rid from role_user\n" +
            "                                   where uid = (select id from user where user.name = #{name})\n" +
            "                                   )")
    List<String> getUserRolesByName(String name);

    List<String> getUserPermissions(List<String> roles);

}




