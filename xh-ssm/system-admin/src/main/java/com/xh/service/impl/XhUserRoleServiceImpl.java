package com.xh.service.impl;

import com.xh.entity.XhUserRole;
import com.xh.dao.XhUserRoleDao;
import com.xh.service.XhUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 用户和角色关联表(XhUserRole)表服务实现类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@Service("xhUserRoleService")
public class XhUserRoleServiceImpl implements XhUserRoleService {
    @Resource
    private XhUserRoleDao xhUserRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public XhUserRole queryById(Long userId) {
        return this.xhUserRoleDao.queryById(userId);
    }

    /**
     * 分页查询
     *
     * @param xhUserRole 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<XhUserRole> queryByPage(XhUserRole xhUserRole, PageRequest pageRequest) {
        long total = this.xhUserRoleDao.count(xhUserRole);
        return new PageImpl<>(this.xhUserRoleDao.queryAllByLimit(xhUserRole, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param xhUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public XhUserRole insert(XhUserRole xhUserRole) {
        this.xhUserRoleDao.insert(xhUserRole);
        return xhUserRole;
    }

    /**
     * 修改数据
     *
     * @param xhUserRole 实例对象
     * @return 实例对象
     */
    @Override
    public XhUserRole update(XhUserRole xhUserRole) {
        this.xhUserRoleDao.update(xhUserRole);
        return this.queryById(xhUserRole.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long userId) {
        return this.xhUserRoleDao.deleteById(userId) > 0;
    }
}
