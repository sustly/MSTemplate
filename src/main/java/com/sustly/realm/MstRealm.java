package com.sustly.realm;

import com.sustly.model.User;
import com.sustly.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author liyue
 * @date 2019/4/18 15:07
 */
@Component("mstRealm")
public class MstRealm extends AuthorizingRealm {

    @Resource(name = "userService")
    private UserService userService;

    /**
     * 授权
     * @param principalCollection principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     * @param authenticationToken authenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //通过令牌得到用户名和密码
        UsernamePasswordToken upt = (UsernamePasswordToken)authenticationToken;
        //得到密码
        String pwd = new String(upt.getPassword());
        //调用登录查询
       User user = userService.login(upt.getUsername(), pwd);
        if(null != user){
            return new SimpleAuthenticationInfo(user,pwd,getName());
        }
        return null;
    }
}
