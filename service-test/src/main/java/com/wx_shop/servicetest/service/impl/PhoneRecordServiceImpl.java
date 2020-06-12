package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.PhoneRecord;
import com.wx_shop.servicetest.dao.PhoneRecordDao;
import com.wx_shop.servicetest.service.PhoneRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (PhoneRecord)表服务实现类
 *
 * @author makejava
 * @since 2020-05-16 09:16:28
 */
@Service("phoneRecordService")
public class PhoneRecordServiceImpl implements PhoneRecordService {
    @Resource
    private PhoneRecordDao phoneRecordDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PhoneRecord queryById(Integer id) {
        return this.phoneRecordDao.queryById(id);
    }
    
    @Override
    public PhoneRecord queryObj(PhoneRecord phoneRecord) {
        return this.phoneRecordDao.queryObj(phoneRecord);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<PhoneRecord> queryAllByLimit(int offset, int limit) {
        return this.phoneRecordDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<PhoneRecord> queryAll(PhoneRecord phoneRecord) {
        return this.phoneRecordDao.queryAll(phoneRecord);
    }
    @Override
    public List<PhoneRecord> queryTodayList(PhoneRecord phoneRecord) {
        return this.phoneRecordDao.queryTodayList(phoneRecord);
    }
    /**
     * 新增数据
     *
     * @param phoneRecord 实例对象
     * @return 实例对象
     */
    @Override
    public PhoneRecord insert(PhoneRecord phoneRecord) {
        this.phoneRecordDao.insert(phoneRecord);
        return phoneRecord;
    }

    /**
     * 修改数据
     *
     * @param phoneRecord 实例对象
     * @return 实例对象
     */
    @Override
    public PhoneRecord update(PhoneRecord phoneRecord) {
        this.phoneRecordDao.update(phoneRecord);
        return this.queryById(phoneRecord.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.phoneRecordDao.deleteById(id) > 0;
    }
}