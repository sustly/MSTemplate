package com.sustly.dao;

import com.sustly.model.Menu;

import java.util.List;

/**
 * @author liyue
 * @date 2019/3/20 11:18
 */
public interface MenuDao /*extends JpaRepository<Menu,Integer>*/ {
    List<Menu> getMenuTree(Integer pid, String menuName, String url, String icon, Integer page, Integer rows);

    List<Menu> getMenuTree();

    Menu getMenuById(Integer id);

    void deleteMenuById(Integer id);

    void saveMenu(Menu menu);

    Integer findBrotherMenuByPid(Integer pid);

    Long getMenuCount(Integer pid, String menuName, String url, String icon);

    void updateMenu(Menu menu);
}
