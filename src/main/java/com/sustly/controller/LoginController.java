package com.sustly.controller;

import com.sustly.model.User;
import com.sustly.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyue
 * @date 2019/3/19 11:16
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/loginCheck")
    @ResponseBody
    public Map<String, Object> loginCheck(@RequestBody User user){
        Map<String, Object> map = new HashMap<String, Object>(2);
        try {
            //1. 创建令牌,身份证明
            UsernamePasswordToken upt = new UsernamePasswordToken(user.getLoginName(), user.getPassword());
            //2. 获取主题 subject: 封装当前用户的一些操作
            Subject subject = SecurityUtils.getSubject();
            //3. 执行login
            subject.login(upt);
            map.put("isSuccess",true);
        }catch (Exception e){
            e.printStackTrace();
            map.put("isSuccess",false);
            map.put("message","用户名或密码错误！");
        }

        return map;
    }

}
