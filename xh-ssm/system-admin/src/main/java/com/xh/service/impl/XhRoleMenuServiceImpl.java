package com.xh.service.impl;

import com.xh.entity.XhRoleMenu;
import com.xh.dao.XhRoleMenuDao;
import com.xh.service.XhRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 角色和菜单关联表(XhRoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@Service("xhRoleMenuService")
public class XhRoleMenuServiceImpl implements XhRoleMenuService {
    @Resource
    private XhRoleMenuDao xhRoleMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public XhRoleMenu queryById(Long roleId) {
        return this.xhRoleMenuDao.queryById(roleId);
    }

    /**
     * 分页查询
     *
     * @param xhRoleMenu 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<XhRoleMenu> queryByPage(XhRoleMenu xhRoleMenu, PageRequest pageRequest) {
        long total = this.xhRoleMenuDao.count(xhRoleMenu);
        return new PageImpl<>(this.xhRoleMenuDao.queryAllByLimit(xhRoleMenu, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param xhRoleMenu 实例对象
     * @return 实例对象
     */
    @Override
    public XhRoleMenu insert(XhRoleMenu xhRoleMenu) {
        this.xhRoleMenuDao.insert(xhRoleMenu);
        return xhRoleMenu;
    }

    /**
     * 修改数据
     *
     * @param xhRoleMenu 实例对象
     * @return 实例对象
     */
    @Override
    public XhRoleMenu update(XhRoleMenu xhRoleMenu) {
        this.xhRoleMenuDao.update(xhRoleMenu);
        return this.queryById(xhRoleMenu.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.xhRoleMenuDao.deleteById(roleId) > 0;
    }
}
