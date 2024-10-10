package com.xh.service;

import com.xh.entity.XhOperLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * 操作日志(XhOperLog)表服务接口
 *
 * @author makejava
 * @since 2024-10-01 13:36:54
 */
public interface XhOperLogService {

    /**
     * 通过ID查询单条数据
     *
     * @param operId 主键
     * @return 实例对象
     */
    XhOperLog queryById(Integer operId);

    /**
     * 分页查询
     *
     * @param xhOperLog 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<XhOperLog> queryByPage(XhOperLog xhOperLog, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param xhOperLog 实例对象
     * @return 实例对象
     */
    void insert(XhOperLog xhOperLog);

    /**
     * 修改数据
     *
     * @param xhOperLog 实例对象
     * @return 实例对象
     */
    XhOperLog update(XhOperLog xhOperLog);

    /**
     * 通过主键删除数据
     *
     * @param operId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer operId);

}
