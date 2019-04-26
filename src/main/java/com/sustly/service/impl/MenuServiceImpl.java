package com.sustly.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sustly.dao.MenuDao;
import com.sustly.model.Menu;
import com.sustly.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liyue
 * @date 2019/3/20 11:17
 */
@Service("menuService")
@Transactional(rollbackOn = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Resource(name = "menuDao")
    private MenuDao menuDao;

    /**
     * getMenuTree 获取json格式的menu
     * @return
     */
    @Override
    public JSONObject getMenuTree() {
        List<Menu> menuTree = menuDao.getMenuTree();
        return dealMenuTree(menuTree);
    }

    /**
     * 获取menu list
     * @return  List<Menu>
     *
     */
    @Override
    public List<Menu> getMenuList(HttpServletRequest request) {
        String pid1 = request.getParameter("pid");
        int pid = (pid1 == null || "".equals(pid1.trim())) ?-1:Integer.parseInt(pid1);
        String menuName = request.getParameter("menuname");
        String url = request.getParameter("url");
        String icon = request.getParameter("icon");
        String page1 = request.getParameter("page");
        int page = (page1 == null || "".equals(page1.trim()))?-1:Integer.parseInt(page1);
        String rows1 = request.getParameter("rows");
        int rows = (rows1 == null || "".equals(rows1.trim()))?-1:Integer.parseInt(rows1);
        return menuDao.getMenuTree(pid, menuName, url, icon, page, rows);
    }

    /**
     * 根据id查询menu
     * @param id id
     * @return Menu
     */
    @Override
    public Menu getMenuById(Integer id) {
        return menuDao.getMenuById(id);
    }

    /**
     * 根据id删除menu
     * @param id id
     */
    @Override
    public void deleteMenuById(Integer id) {
        menuDao.deleteMenuById(id);
    }

    /**
     * 保存menu
     * @param menu menu
     */
    @Override
    public void saveMenu(Menu menu) {
        Integer count = menuDao.findBrotherMenuByPid(menu.getPid());
        if (menu.getPid() == 0){
            menu.setMenuid((count+1)*100);
        } else {
            menu.setMenuid(count+1+menu.getPid());
        }
        menuDao.saveMenu(menu);
    }

    @Override
    public Long getMenuCount(HttpServletRequest request) {
        String pid1 = request.getParameter("pid");
        int pid = (pid1 == null || "".equals(pid1.trim())) ?-1:Integer.parseInt(pid1);
        String menuName = request.getParameter("menuname");
        String url = request.getParameter("url");
        String icon = request.getParameter("icon");
        return menuDao.getMenuCount(pid, menuName, url, icon);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDao.updateMenu(menu);
    }

    /**
     * 处理menu tree 获取根节点
     * @param menuTree  menuTree
     * @return
     */
    private JSONObject dealMenuTree(List<Menu> menuTree) {
        Menu menuRoot = null;
        for (Menu menu:menuTree){
            if (menu.getMenuid()==0){
                menuRoot = menu;
            }
        }
        getTree(menuRoot, menuTree);
        return (JSONObject) JSON.toJSON(menuRoot);
    }

    /**
     * 递归获取menu tree
     * @param menuRoot      menuRoot
     * @param menuTree menuTree
     */
    private void getTree(Menu menuRoot, List<Menu> menuTree) {
        if (getChildTree(menuRoot,menuTree)){
            return ;
        }
        for(Menu menu : menuTree){
            if (menu.getPid() == menuRoot.getMenuid() ){
                if(menuRoot.getMenus() == null ){
                    List<Menu> list = new ArrayList<Menu>();
                    menuRoot.setMenus(list);
                }
                menuRoot.getMenus().add(menu);
                getTree(menu,menuTree);
            }
        }
    }

    /**
     * 判段该menu是否有子menu
     * @param menuRoot menuRoot
     * @param menuTree menuTree
     * @return
     */
    private boolean getChildTree(Menu menuRoot, List<Menu> menuTree) {
        for(Menu menu : menuTree){
            if (menu.getPid() == menuRoot.getMenuid() ){
                return false;
            }
        }
        return true;
    }
}
