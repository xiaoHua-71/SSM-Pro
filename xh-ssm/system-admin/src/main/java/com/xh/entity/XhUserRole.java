package com.xh.entity;

import java.io.Serializable;

/**
 * 用户和角色关联表(XhUserRole)实体类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public class XhUserRole implements Serializable {
    private static final long serialVersionUID = 635687708661493451L;
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 角色ID
     */
    private Long roleId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

}

