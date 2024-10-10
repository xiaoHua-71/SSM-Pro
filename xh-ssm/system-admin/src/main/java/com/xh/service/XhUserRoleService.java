package com.xh.service;

import com.xh.entity.XhUserRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 用户和角色关联表(XhUserRole)表服务接口
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhUserRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    XhUserRole queryById(Long userId);

    /**
     * 分页查询
     *
     * @param xhUserRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<XhUserRole> queryByPage(XhUserRole xhUserRole, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param xhUserRole 实例对象
     * @return 实例对象
     */
    XhUserRole insert(XhUserRole xhUserRole);

    /**
     * 修改数据
     *
     * @param xhUserRole 实例对象
     * @return 实例对象
     */
    XhUserRole update(XhUserRole xhUserRole);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Long userId);

}
