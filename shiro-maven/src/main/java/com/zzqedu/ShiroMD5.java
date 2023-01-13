package com.zzqedu;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShiroMD5 {
    public static void main(String[] args) {
        // 密码明文
        String password = "123456";
        // 使用MD5加密
        Md5Hash md5Hash1 = new Md5Hash(password);
        System.out.println("md5加密：" + md5Hash1.toHex());
        // 带盐的 md5 加密，盐就是在密码明文后拼接新字符串，然后再进行加密
        Md5Hash md5Hash2 = new Md5Hash(password,"zzqshuai");
        System.out.println("带盐的md5加密：" + md5Hash2.toHex());
        // 为了保证安全，避免被破解还可以多次迭代加密，保证数据安全
        Md5Hash md5Hash3 = new Md5Hash(password, "zzqshuai", 3);
        System.out.println("多次带盐加密；" + md5Hash3.toHex());
        // 使用父类实现，可以指定加密算法
        SimpleHash simpleHash = new SimpleHash(Md5Hash.ALGORITHM_NAME ,password, "zzqshuai", 3);
           System.out.println("使用父类多次带盐加密；" + md5Hash3.toHex());
    }
}
