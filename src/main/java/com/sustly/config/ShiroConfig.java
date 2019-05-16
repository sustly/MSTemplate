package com.sustly.config;

import com.sustly.realm.MstRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * @author liyue
 * @date 2019/4/25 15:46
 */
@Configuration
public class ShiroConfig {


    @Bean
    public DefaultWebSecurityManager securityManager(@Autowired MstRealm mstRealm){
        return new DefaultWebSecurityManager(mstRealm);
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Autowired DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setUnauthorizedUrl("/error");
        HashMap<String, String> map = new HashMap<>(4);
        map.put("/error", "anon");
        map.put("/login*", "anon");
        map.put("/*", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

}
