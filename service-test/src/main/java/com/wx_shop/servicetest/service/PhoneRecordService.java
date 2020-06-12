package com.wx_shop.servicetest.service;

import com.wx_shop.servicetest.entity.PhoneRecord;
import java.util.List;

/**
 * (PhoneRecord)表服务接口
 *
 * @author makejava
 * @since 2020-05-16 09:16:28
 */
public interface PhoneRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PhoneRecord queryById(Integer id);
    
    
    PhoneRecord queryObj(PhoneRecord phoneRecord);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PhoneRecord> queryAllByLimit(int offset, int limit);
    
    
    List<PhoneRecord> queryAll(PhoneRecord phoneRecord);
    List<PhoneRecord> queryTodayList(PhoneRecord phoneRecord);
    /**
     * 新增数据
     *
     * @param phoneRecord 实例对象
     * @return 实例对象
     */
    PhoneRecord insert(PhoneRecord phoneRecord);

    /**
     * 修改数据
     *
     * @param phoneRecord 实例对象
     * @return 实例对象
     */
    PhoneRecord update(PhoneRecord phoneRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}