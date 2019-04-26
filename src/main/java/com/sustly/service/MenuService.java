package com.sustly.service;

import com.alibaba.fastjson.JSONObject;
import com.sustly.model.Menu;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface MenuService {
    JSONObject getMenuTree();

    List<Menu> getMenuList(HttpServletRequest request);

    Menu getMenuById(Integer id);

    void deleteMenuById(Integer id);

    void saveMenu(Menu menu);

    Long getMenuCount(HttpServletRequest request);

    void updateMenu(Menu menu);
}
