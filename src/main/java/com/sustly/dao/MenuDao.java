package com.sustly.dao;

import com.sustly.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author liyue
 * @date 2019/3/20 11:18
 */
public interface MenuDao extends JpaRepository<Menu,Integer>, JpaSpecificationExecutor<Menu> {
    /**
     * getMenuTree
     * @return List<Menu>
     */
    @Query("from Menu")
    List<Menu> getMenuTree();

    /**
     * getMenuByMenuId
     * @param id  id
     * @return Menu
     */
    Menu getMenuByMenuId(Integer id);

    /**
     * 根据id删除menu
     * @param id id
     */
    @Modifying
    void deleteMenuByMenuId(Integer id);


    /**
     * 根据父id查询兄弟的个数
     * @param pid pid
     * @return count
     */
    Integer countByPidIs(Integer pid);

}
