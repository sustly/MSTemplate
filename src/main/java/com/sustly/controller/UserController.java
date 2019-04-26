package com.sustly.controller;

import com.sustly.model.User;
import com.sustly.service.UserService;
import com.sustly.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyue
 * @date 2019/3/22 9:01
 */
@Controller
public class UserController {

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("/updatePwd")
    @ResponseBody
    public Map<String, Object> updatePassword(@RequestParam("oldPwd")String oldPass,
                                              @RequestParam("newPwd")String newPass){
        boolean flag = userService.updatePassword(oldPass,newPass);
        Map<String, Object> map =new HashMap<String, Object>(2);
        if (flag){
            map.put("isSuccess",true);
            map.put("message","修改密码成功！");
        }else {
            map.put("isSuccess",false);
            map.put("message","修改密码失败！");
        }
        return map;
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @PostMapping("/user_listByPage")
    @ResponseBody
    public List<User> userListPage(HttpServletRequest request){
        return userService.getUser(request);
    }

    @GetMapping("/user_get")
    @ResponseBody
    public User menuGet(@RequestParam("id")Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("user_delete")
    @ResponseBody
    public Map<String, Object> menuDelete(@RequestParam("id")Integer id){
        Map<String, Object> map = new HashMap<String, Object>(2);
        userService.deleteUserById(id);
        map.put("message","删除成功！");
        return map;
    }

    @PostMapping("user_add")
    @ResponseBody
    public Map<String, Object> menuAdd(@RequestBody User user)throws UnsupportedEncodingException {
        String name = URLDecoder.decode(user.getName(), "utf-8");
        String loginName = URLDecoder.decode(user.getLoginName(), "utf-8");
        user.setName(name);
        user.setLoginName(loginName);
        user.setPassword(MD5Util.encrypt("000000"));
        Map<String, Object> map = new HashMap<String, Object>(2);
        userService.saveUser(user);
        map.put("message","保存成功！");
        return map;
    }

    @PostMapping("user_update")
    @ResponseBody
    public Map<String, Object> menuUpdate(@RequestBody User user) throws Exception {
        String name = URLDecoder.decode(user.getName(), "utf-8");
        String loginName = URLDecoder.decode(user.getLoginName(), "utf-8");
        user.setName(name);
        user.setLoginName(loginName);
        Map<String, Object> map = new HashMap<String, Object>(2);
        userService.updateUser(user);
        map.put("message","修改成功！");
        return map;
    }

}
