package com.xh.entity;

import java.io.Serializable;

/**
 * 角色和菜单关联表(XhRoleMenu)实体类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public class XhRoleMenu implements Serializable {
    private static final long serialVersionUID = 443012079089661280L;
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

}

