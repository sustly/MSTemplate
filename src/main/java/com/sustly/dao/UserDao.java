package com.sustly.dao;

import com.sustly.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author admin
 */
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    /**
     * findUserByUsernameAndPassword
     * @param name name
     * @param password password
     * @return User
     */
    User findUserByLoginNameAndPassword(String name, String password);

    /**
     * updatePasswordById
     * @param id id
     * @param newPassword newPassword
     */
    @Modifying
    @Query("update User u set u.password=:newPassword where u.id=:id")
    void updatePasswordById(@Param("id") Integer id, @Param("newPassword") String newPassword);

    /**
     * deleteUserById
     * @param id id
     */
    @Modifying
    void deleteUserById(Integer id);

    /**
     * findUserById
     * @param id id
     * @return User
     */
    User findUserById(Integer id);
}
