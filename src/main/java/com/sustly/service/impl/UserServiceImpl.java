package com.sustly.service.impl;

import com.sustly.dao.UserDao;
import com.sustly.model.User;
import com.sustly.service.UserService;
import com.sustly.utils.BeanUtil;
import com.sustly.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author liyue
 * @date 2019/3/19 18:13
 */
@Service("userService")
@Transactional(rollbackOn = Exception.class)
public class UserServiceImpl implements UserService {

    @Resource(name = "userDao")
    private UserDao userDao;

    @Override
    public User login(String name, String password) {
        String encryptPassword = MD5Util.encrypt(password);
        return userDao.findUserByUsernameAndPassword(name,encryptPassword);
    }

    @Override
    public boolean updatePassword(String oldPass, String newPass) {
        String newPassword = MD5Util.encrypt(newPass);
        String oldPassword = MD5Util.encrypt(oldPass);
        //获取主题
        Subject subject = SecurityUtils.getSubject();
        //提取主角,拿到User
        User user = (User)subject.getPrincipal();
        String loginName = user.getLoginName();
        User user1 = userDao.findUserByUsernameAndPassword(loginName, oldPassword);
        if (user1.getPassword().equals(oldPassword)){
            userDao.updataPasswordById(user1.getId(), newPassword);
            return true;
        }
        return false;
    }

    @Override
    public List<User> getUser(HttpServletRequest request) {
        String page1 = request.getParameter("page");
        int page = (page1 == null || "".equals(page1.trim()))?-1:Integer.parseInt(page1);
        String rows1 = request.getParameter("rows");
        int rows = (rows1 == null || "".equals(rows1.trim()))?-1:Integer.parseInt(rows1);
        String departmentId1 = request.getParameter("departmentId");
        Integer departmentId = (departmentId1 == null || "".equals(departmentId1.trim()))?-1:Integer.parseInt(departmentId1);
        String loginName = request.getParameter("loginName");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String isAvailable1 = request.getParameter("isAvailable");
        Integer isAvailable = null;
        if (isAvailable1 != null && !"".equals(isAvailable1.trim())){
            isAvailable = Integer.parseInt(isAvailable1);
        }

        return userDao.getUser(loginName,name,email,phone,isAvailable,departmentId,page,rows);
    }

    @Override
    public void updateUser(User user) throws Exception {
        User userById = userDao.findUserById(user.getId());
        User bean = (User) BeanUtil.updateBean(userById, user);
        userDao.updateUser(bean);
    }

    @Override
    public void saveUser(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDao.deleteUserById(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public void updateLoginTime(User user) {
        userDao.updateLoginTime(user);
    }
}
