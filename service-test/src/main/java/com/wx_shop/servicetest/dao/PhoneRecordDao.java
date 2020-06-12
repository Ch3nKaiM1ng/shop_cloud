package com.wx_shop.servicetest.dao;

import com.wx_shop.servicetest.entity.PhoneRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (PhoneRecord)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-16 09:16:28
 */
@Mapper
public interface PhoneRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PhoneRecord queryById(Integer id);
    
    PhoneRecord queryObj(PhoneRecord phoneRecord);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<PhoneRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<PhoneRecord> queryAll(PhoneRecord phoneRecord);
    List<PhoneRecord> queryTodayList(PhoneRecord phoneRecord);

    /**
     * 新增数据
     *
     * @param phoneRecord 实例对象
     * @return 影响行数
     */
    int insert(PhoneRecord phoneRecord);

    /**
     * 修改数据
     *
     * @param phoneRecord 实例对象
     * @return 影响行数
     */
    int update(PhoneRecord phoneRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}