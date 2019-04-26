package com.sustly.service;

import com.sustly.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author liyue
 * @date 2019/3/19 17:39
 */
public interface UserService {
    /**
     * login
     * @param name name
     * @param password password
     * @return user
     */
    User login(String name, String password);

    /**
     * updatePassword
     * @param oldPass oldPass
     * @param newPass newPass
     * @return boolean
     */
    boolean updatePassword(String oldPass, String newPass);

    List<User> getUser(HttpServletRequest request);

    void updateUser(User user) throws Exception;

    void saveUser(User user);

    void deleteUserById(Integer id);

    User getUserById(Integer id);

    void updateLoginTime(User user);
}
