package com.sustly.model;

import javax.persistence.*;
import java.util.List;

/**
 * @author liyue
 * @date 2019/3/20 10:32
 */
@Entity
public class Menu {
    private Integer menuId;
    private String menuName;
    private String icon;
    private String url;
    private Integer pid;
    private List<Menu> menus;

    @Transient
    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    @Id
    @Column(name = "menu_id")
    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Basic
    @Column(name = "menu_name")
    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    @Basic
    @Column(name = "icon")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Basic
    @Column(name = "url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "pid")
    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Menu menu = (Menu) o;

        if (menuId != null ? !menuId.equals(menu.menuId) : menu.menuId != null) return false;
        if (menuName != null ? !menuName.equals(menu.menuName) : menu.menuName != null) return false;
        if (icon != null ? !icon.equals(menu.icon) : menu.icon != null) return false;
        if (url != null ? !url.equals(menu.url) : menu.url != null) return false;
        if (pid != null ? !pid.equals(menu.pid) : menu.pid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (menuName != null ? menuName.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        return result;
    }
}
