package com.sustly.dao.impl;

import com.sustly.dao.MenuDao;
import com.sustly.model.Menu;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author liyue
 * @date 2019/3/20 11:20
 */
@SuppressWarnings({"unchecked"})
public class MenuDaoImpl extends HibernateDaoSupport implements MenuDao {

    @Autowired
    public MenuDaoImpl(HibernateTemplate hibernateTemplate) {
        this.setHibernateTemplate(hibernateTemplate);
    }

    @Override
    public List<Menu> getMenuTree(Integer pid, String menuName, String url, String icon, Integer page, Integer rows) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Menu.class);
        if (pid != null && !pid .equals(-1) ){
            detachedCriteria.add(Restrictions.eq("pid",pid));
        }
        if (menuName != null && !"".equals(menuName.trim())){
            detachedCriteria.add(Restrictions.eq("menuname",menuName));
        }
        if (url != null && !"".equals(url.trim())){
            detachedCriteria.add(Restrictions.eq("url",url));
        }
        if (icon != null && !"".equals(icon.trim())){
            detachedCriteria.add(Restrictions.eq("icon",icon));
        }
        int firstResult = (page -1) * rows;
        return (List<Menu>) this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, rows);
    }

    @Override
    public List<Menu> getMenuTree() {
        return (List<Menu>) this.getHibernateTemplate().find("from Menu");
    }

    @Override
    public Menu getMenuById(Integer id) {
        List<Menu> menus = (List<Menu>) this.getHibernateTemplate().find("from Menu where menuid=?0", id);
        if (menus.size()>0){
            return menus.get(0);
        }
        return null;
    }

    @Override
    public void deleteMenuById(Integer id) {
        Menu menu = getMenuById(id);
        this.getHibernateTemplate().delete(menu);
    }

    @Override
    public void saveMenu(Menu menu) {
        this.getHibernateTemplate().save(menu);
    }

    @Override
    public Integer findBrotherMenuByPid(Integer pid) {
        String hql =  "from Menu where pid=?0";
        List<?> list = this.getHibernateTemplate().find(hql, pid);
        return list.size();
    }

    @Override
    public Long getMenuCount(Integer pid, String menuName, String url, String icon) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Menu.class);
        if (pid != null){
            detachedCriteria.add(Restrictions.eq("pid",pid));
        }
        if (menuName != null && !"".equals(menuName.trim())){
            detachedCriteria.add(Restrictions.eq("menuname",menuName));
        }
        if (url != null && !"".equals(url.trim())){
            detachedCriteria.add(Restrictions.eq("url",url));
        }
        if (icon != null && !"".equals(icon.trim())){
            detachedCriteria.add(Restrictions.eq("icon",icon));
        }

        detachedCriteria.setProjection(Projections.rowCount());

        List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
        return list.get(0);
    }

    @Override
    public void updateMenu(Menu menu) {
        this.getHibernateTemplate().update(menu);
    }
}
