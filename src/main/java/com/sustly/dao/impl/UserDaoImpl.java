package com.sustly.dao.impl;

import com.sustly.dao.UserDao;
import com.sustly.model.User;
import com.sustly.utils.DateUtil;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author liyue
 * @date 2019/3/19 17:32
 */
@SuppressWarnings({"unchecked"})
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    @Autowired
    public UserDaoImpl(HibernateTemplate hibernateTemplate) {
        setHibernateTemplate(hibernateTemplate);
    }

    @Override
    public User findUserByUsernameAndPassword(String name, String password) {
        String hql = "from User user where user.loginName=?0 and user.password=?1";
        List<User> userList = (List<User>) this.getHibernateTemplate().find(hql, name, password);
        if (userList != null && userList.size() > 0){
            return userList.get(0);
        }else {
            return null;
        }

    }

    @Override
    public void updataPasswordById(Integer id, String newPasseord) {
        String hql = "update User user set user.password=?0 where user.id=?1";
        this.getHibernateTemplate().bulkUpdate(hql, newPasseord, id);
    }

    @Override
    public List<User> getUser(String loginName, String name, String email, String phone, Integer isAvailable, Integer departmentId, int page, int rows) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(User.class);
        if (loginName != null && !"".equals(loginName.trim())) {
            detachedCriteria.add(Restrictions.eq("loginName",loginName));
        }
        if (name != null && !"".equals(name.trim())){
            detachedCriteria.add(Restrictions.eq("name",name));
        }
        if (email != null && !"".equals(email.trim())){
            detachedCriteria.add(Restrictions.eq("email",email));
        }
        if (phone != null && !"".equals(phone.trim())){
            detachedCriteria.add(Restrictions.eq("phone",phone));
        }
        if (isAvailable != null && isAvailable != -1) {
            detachedCriteria.add(Restrictions.eq("isAvailable", isAvailable));
        }
        if (departmentId != null && departmentId != -1){
            detachedCriteria.add(Restrictions.eq("departmentId",departmentId));
        }
        int firstResult = (page -1) * rows;
        return (List<User>) this.getHibernateTemplate().findByCriteria(detachedCriteria, firstResult, rows);
    }

    @Override
    public void updateUser(User user) {
        this.getHibernateTemplate().update(user);
    }

    @Override
    public void saveUser(User user) {
        user.setIsAvailable(1);
        this.getHibernateTemplate().save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = findUserById(id);
        this.getHibernateTemplate().delete(user);
    }

    @Override
    public User findUserById(Integer id) {
        List<User> list = (List<User>) this.getHibernateTemplate().find("from User where id=?0", id);
        return list.get(0);
    }

    @Override
    public void updateLoginTime(User user) {

        String localTime = DateUtil.getLocalTime();
        user.setLastLoginTime(localTime);
        this.getHibernateTemplate().saveOrUpdate(user);
    }
}
