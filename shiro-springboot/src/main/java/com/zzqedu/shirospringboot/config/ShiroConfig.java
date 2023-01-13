package com.zzqedu.shirospringboot.config;


import net.sf.ehcache.CacheManager;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.io.ResourceUtils;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ShiroConfig {

    @Autowired
    private MyRealm myRealm;

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        // 1. 创建defaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager =
                new DefaultWebSecurityManager();
        // 2 创建加密对象，并设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 2.1 采用md5加密
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        // 2.2 迭代加密次数
        matcher.setHashIterations(3);
        // 3. 将加密对象存储到myRealm中
        myRealm.setCredentialsMatcher(matcher);
        // 4. 将myRealm放入defaultWebSecurityManager 对象
        defaultWebSecurityManager.setRealm(myRealm);
        // 4.5 设置rememberMe
        defaultWebSecurityManager.setRememberMeManager(rememberMeManager());
        // 4.6 设置缓存管理器
        defaultWebSecurityManager.setCacheManager(getEhcacheManager());
        // 返回
        return defaultWebSecurityManager;
    }

    private EhCacheManager getEhcacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        InputStream is = null;
        try {
            is = ResourceUtils.getInputStreamForPath("classpath:ehcache/ehcache-shiro.xml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CacheManager cacheManager = new CacheManager(is);
        ehCacheManager.setCacheManager(cacheManager);
        return ehCacheManager;
    }


    //cookie 属性设置
    public SimpleCookie rememberMeCookie(){
        SimpleCookie cookie = new SimpleCookie("rememberMe");
        //设置跨域
        //cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(30*24*60*60);
        return cookie;
    }
    //创建 Shiro 的 cookie 管理对象
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new
                CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("1234567890987654".getBytes());
        return cookieRememberMeManager;
    }


    /**
     * 多realm实现
     * @return
     */
    // @Bean
    public DefaultWebSecurityManager defaultWebSecurityManagerMoreRealm() {
        // 1. 创建defaultWebSecurityManager对象
        DefaultWebSecurityManager defaultWebSecurityManager =
                new DefaultWebSecurityManager();
        // 2 创建加密对象，并设置相关属性
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 2.1 采用md5加密
        matcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        // 2.2 迭代加密次数
        matcher.setHashIterations(3);
        // 3. 将加密对象存储到myRealm中
        myRealm.setCredentialsMatcher(matcher);
        // 4. 将myRealm放入defaultWebSecurityManager 对象
        // defaultWebSecurityManager.setRealm(myRealm);

        ModularRealmAuthenticator modularRealmAuthenticator = new ModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());

        defaultWebSecurityManager.setAuthenticator(modularRealmAuthenticator);

        List<org.apache.shiro.realm.Realm> realms = new ArrayList<>();
        realms.add(myRealm);
        // 可以添加多个

        // 将多个realm加入
        defaultWebSecurityManager.setRealms(realms);

        // 返回
        return defaultWebSecurityManager;
    }

    //配置 Shiro 内置过滤器拦截范围
    @Bean
    public DefaultShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition definition = new DefaultShiroFilterChainDefinition();
        //设置不认证可以访问的资源
        definition.addPathDefinition("/myController/userLogin","anon");
        definition.addPathDefinition("/login","anon");
        //配置登出过滤器  要放在前面否则不生效！！！
        definition.addPathDefinition("/logout","logout");
        //设置需要进行登录认证的拦截范围
        definition.addPathDefinition("/**","authc");
        // 添加存在用户的拦截器(rememberMe)
        definition.addPathDefinition("/**", "user");
        return definition;
    }



}
