package com.xh.service.impl;

import com.xh.entity.XhOperLog;
import com.xh.dao.XhOperLogDao;
import com.xh.service.XhOperLogService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

/**
 * 操作日志(XhOperLog)表服务实现类
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
@Service("xhOperLogService")
public class XhOperLogServiceImpl implements XhOperLogService {
    @Resource
    private XhOperLogDao xhOperLogDao;

    /**
     * 通过ID查询单条数据
     *
     * @param operId 主键
     * @return 实例对象
     */
    @Override
    public XhOperLog queryById(Integer operId) {
        return this.xhOperLogDao.queryById(operId);
    }

    /**
     * 分页查询
     *
     * @param xhOperLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<XhOperLog> queryByPage(XhOperLog xhOperLog, PageRequest pageRequest) {
        long total = this.xhOperLogDao.count(xhOperLog);
        return new PageImpl<>(this.xhOperLogDao.queryAllByLimit(xhOperLog, pageRequest), pageRequest, total);
    }


    /**
     * 新增数据
     *
     * @param xhOperLog 实例对象
     * @return 实例对象
     */
    @Override
    @Async("xh-logger")
    public void insert(XhOperLog xhOperLog) {
        System.out.println("ydllogger---"+ Thread.currentThread().getName());
        this.xhOperLogDao.insert(xhOperLog);
    }

    /**
     * 修改数据
     *
     * @param xhOperLog 实例对象
     * @return 实例对象
     */
    @Override
    public XhOperLog update(XhOperLog xhOperLog) {
        this.xhOperLogDao.update(xhOperLog);
        return this.queryById(xhOperLog.getOperId());
    }

    /**
     * 通过主键删除数据
     *
     * @param operId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer operId) {
        return this.xhOperLogDao.deleteById(operId) > 0;
    }
}
