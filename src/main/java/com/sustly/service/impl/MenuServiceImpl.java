package com.sustly.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sustly.dao.MenuDao;
import com.sustly.model.Menu;
import com.sustly.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liyue
 * @date 2019/3/20 11:17
 */
@Service("menuService")
@Transactional(rollbackOn = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
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

        Specification<Menu> specification = getMenuSpecification(pid, menuName, url, icon);
        int firstResult = (page -1) * rows;
        Pageable pageable = new PageRequest(firstResult, rows);
        return menuDao.findAll(specification,pageable).stream().collect(Collectors.toList());
    }

    private Specification<Menu> getMenuSpecification(int pid, String menuName, String url, String icon) {
        return (Specification<Menu>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (pid != -1) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("pid"), pid));
            }
            if (menuName != null && !"".equals(menuName.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("menuname"), menuName));
            }
            if (url != null && !"".equals(url.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("url"), url));
            }
            if (icon != null && !"".equals(icon.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("icon"), icon));
            }
            return predicate;
        };
    }

    /**
     * 根据id查询menu
     * @param id id
     * @return Menu
     */
    @Override
    public Menu getMenuById(Integer id) {
        return menuDao.getMenuByMenuid(id);
    }

    /**
     * 根据id删除menu
     * @param id id
     */
    @Override
    public void deleteMenuById(Integer id) {
        menuDao.deleteMenuByMenuid(id);
    }

    /**
     * 保存menu
     * @param menu menu
     */
    @Override
    public void saveMenu(Menu menu) {
        Integer count = menuDao.countByPidIs(menu.getPid());
        if (menu.getPid() == 0){
            menu.setMenuid((count+1)*100);
        } else {
            menu.setMenuid(count+1+menu.getPid());
        }
        menuDao.save(menu);
    }

    @Override
    public Integer getMenuCount(HttpServletRequest request) {
        String pid1 = request.getParameter("pid");
        int pid = (pid1 == null || "".equals(pid1.trim())) ?-1:Integer.parseInt(pid1);
        String menuName = request.getParameter("menuname");
        String url = request.getParameter("url");
        String icon = request.getParameter("icon");

        Specification<Menu> specification = getMenuSpecification(pid, menuName, url, icon);
        return new ArrayList<>(menuDao.findAll(specification)).size();
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDao.save(menu);
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
