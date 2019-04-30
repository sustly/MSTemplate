package com.sustly.service.impl;

import com.sustly.dao.UserDao;
import com.sustly.model.User;
import com.sustly.service.UserService;
import com.sustly.utils.BeanUtil;
import com.sustly.utils.DateUtil;
import com.sustly.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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
        return userDao.findUserByLoginNameAndPassword(name,encryptPassword);
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
        User user1 = userDao.findUserByLoginNameAndPassword(loginName, oldPassword);
        if (user1.getPassword().equals(oldPassword)){
            userDao.updatePasswordById(user1.getId(), newPassword);
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

        Specification<User> specification = getUserSpecification(departmentId, loginName, name, email, phone, isAvailable);
        int firstResult = (page -1) * rows;
        Pageable pageable = new PageRequest(firstResult, rows);
        return userDao.findAll(specification,pageable).stream().collect(Collectors.toList());
    }

    /**
     * 带条件的分页查询
     * @param departmentId 用户组
     * @param loginName loginName
     * @param name name
     * @param email email
     * @param phone phone
     * @param isAvailable isAvailable
     * @return Specification
     */
    private Specification<User> getUserSpecification( Integer departmentId, String loginName, String name, String email, String phone, Integer isAvailable) {
        return (Specification<User>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (departmentId != null && departmentId != -1){
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("departmentId"), departmentId));
            }
            if (loginName != null && !"".equals(loginName.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("loginName"), loginName));
            }
            if (name != null && !"".equals(name.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("name"), name));
            }
            if (email != null && !"".equals(email.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("email"), email));
            }
            if (phone != null && !"".equals(phone.trim())) {
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("phone"), phone));
            }
            if (isAvailable != null && isAvailable != -1){
                predicate.getExpressions().add(criteriaBuilder.equal(root.get("isAvailable"), isAvailable));
            }
            return predicate;
        };
    }

    @Override
    public void updateUser(User user) throws Exception {
        User userById = userDao.findUserById(user.getId());
        User bean = (User) BeanUtil.updateBean(userById, user);
        userDao.save(bean);
    }

    @Override
    public void saveUser(User user) {
        user.setIsAvailable(1);
        userDao.save(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDao.deleteUserById(id);
    }

    @Override
    public User getUserById(Integer id) {
        return userDao.findUserById(id);
    }

    /**
     * 更新登录时间
     * @param user user
     * @throws Exception updateBean
     */
    @Override
    public void updateLoginTime(User user) throws Exception {
        String localTime = DateUtil.getLocalTime();
        user.setLastLoginTime(localTime);
        User oldUser = userDao.findUserByLoginNameAndPassword(user.getLoginName(),MD5Util.encrypt(user.getPassword()));
        BeanUtil.updateBean(oldUser, user);
        userDao.save(oldUser);
    }
}
