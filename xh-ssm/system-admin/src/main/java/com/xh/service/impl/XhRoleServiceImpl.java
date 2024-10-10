package com.xh.service.impl;

import com.xh.entity.XhRole;
import com.xh.dao.XhRoleDao;
import com.xh.service.XhRoleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 角色信息表(XhRole)表服务实现类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@Service("xhRoleService")
public class XhRoleServiceImpl implements XhRoleService {
    @Resource
    private XhRoleDao xhRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    @Override
    public XhRole queryById(Long roleId) {
        return this.xhRoleDao.queryById(roleId);
    }

    /**
     * 分页查询
     *
     * @param xhRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<XhRole> queryByPage(XhRole xhRole, PageRequest pageRequest) {
        long total = this.xhRoleDao.count(xhRole);
        return new PageImpl<>(this.xhRoleDao.queryAllByLimit(xhRole, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param xhRole 实例对象
     * @return 实例对象
     */
    @Override
    public XhRole insert(XhRole xhRole) {
        this.xhRoleDao.insert(xhRole);
        return xhRole;
    }

    /**
     * 修改数据
     *
     * @param xhRole 实例对象
     * @return 实例对象
     */
    @Override
    public XhRole update(XhRole xhRole) {
        this.xhRoleDao.update(xhRole);
        return this.queryById(xhRole.getRoleId());
    }

    /**
     * 通过主键删除数据
     *
     * @param roleId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long roleId) {
        return this.xhRoleDao.deleteById(roleId) > 0;
    }
}
