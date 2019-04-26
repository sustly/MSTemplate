package com.sustly.dao;

import com.sustly.model.User;

import java.util.List;

/**
 * @author admin
 */
public interface UserDao /*extends JpaRepository<User, Integer>*/ {
    /**
     * findUserByUsernameAndPassword
     * @param name name
     * @param password password
     * @return
     */
    User findUserByUsernameAndPassword(String name, String password);

    void updataPasswordById(Integer id, String newPasseord);

    List<User> getUser(String loginName, String name, String email, String phone, Integer isAvailable, Integer departmentId, int page, int rows);

    void updateUser(User user);

    void saveUser(User user);

    void deleteUserById(Integer id);

    User findUserById(Integer id);

    void updateLoginTime(User user);
}
