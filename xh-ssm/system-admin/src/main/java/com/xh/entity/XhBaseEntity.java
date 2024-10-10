package com.xh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

/**
 * 角色和菜单关联表(XhRoleMenu)实体类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class XhBaseEntity implements Serializable {
    //分页使用的字段
    private static final long serialVersionUID = 1L;
    private int page;
    private int size;
    private Sort sort;

}

