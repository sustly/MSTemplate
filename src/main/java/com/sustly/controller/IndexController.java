package com.sustly.controller;

import com.sustly.model.User;
import com.sustly.service.MenuService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liyue
 * @date 2019/3/20 10:03
 */
@Controller
public class IndexController {

    @Resource(name = "menuService")
    private MenuService menuService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @PostMapping("/menu_getMenuTree")
    @ResponseBody
    public String getMenuTree(){
        return menuService.getMenuTree().toString();
    }

    @PostMapping("/login_loginOut")
    @ResponseBody
    public Map<String, Object> loginOut(){
        SecurityUtils.getSubject().logout();
        Map<String, Object> map = new HashMap<String, Object>(3);
        map.put("isSuccess", true);
        return map;
    }

    @PostMapping("/login_showName")
    @ResponseBody
    public Map<String, Object> showName(){
        Map<String, Object> map = new HashMap<String, Object>(3);
        //获取主题
        Subject subject = SecurityUtils.getSubject();
        //提取主角,拿到user
        User user = (User)subject.getPrincipal();
        if (user == null){
            map.put("isSuccess", false);
        }else {
            map.put("isSuccess", true);
            map.put("message", user.getLoginName());
        }
        return map;
    }
}
