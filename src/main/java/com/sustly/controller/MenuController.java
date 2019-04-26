package com.sustly.controller;

import com.sustly.model.Menu;
import com.sustly.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liyue
 * @date 2019/3/21 16:23
 */
@Controller
public class MenuController {

    @Resource(name = "menuService")
    private MenuService menuService;

    @GetMapping("/menu")
    public String shoeMenu(){
        return "menu";
    }

    @PostMapping("/menu_listByPage")
    @ResponseBody
    public Map<String, Object> listPage(HttpServletRequest request){
        Map<String, Object> map = new HashMap<String, Object>(20);

        List<Menu> menuList = menuService.getMenuList(request);
        Long count = menuService.getMenuCount(request);
        map.put("total", count);
        map.put("rows",menuList);
        return map;
    }

    @GetMapping("/menu_get")
    @ResponseBody
    public Menu menuGet(@RequestParam("id")Integer id){
        return menuService.getMenuById(id);
    }

    @PostMapping("menu_delete")
    @ResponseBody
    public Map<String, Object> menuDelete(@RequestParam("id")Integer id){
        Map<String, Object> map = new HashMap<String, Object>(2);
        menuService.deleteMenuById(id);
        map.put("message","删除成功！");
        return map;
    }

    @PostMapping("menu_add")
    @ResponseBody
    public Map<String, Object> menuAdd(@RequestBody Menu menu)throws UnsupportedEncodingException {
        String menuname = URLDecoder.decode(menu.getMenuname(), "utf-8");
        menu.setMenuname(menuname);
        Map<String, Object> map = new HashMap<String, Object>(2);
        menuService.saveMenu(menu);
        map.put("message","保存成功！");
        return map;
    }

    @PostMapping("menu_update")
    @ResponseBody
    public Map<String, Object> menuUpdate(@RequestBody Menu menu)throws UnsupportedEncodingException{
        String menuname = URLDecoder.decode(menu.getMenuname(), "utf-8");
        menu.setMenuname(menuname);
        Map<String, Object> map = new HashMap<String, Object>(2);
        menuService.updateMenu(menu);
        map.put("message","修改成功！");
        return map;
    }
}
