package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.MainOrder;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (MainOrder)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 10:39:01
 */
public interface MainOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param mainOrderId 主键
     * @return 实例对象
     */
    MainOrder queryById(Integer mainOrderId);
    
    MainOrder queryObj(MainOrder mainOrder);

    List<MainOrder> queryAllByLimit(MainOrder mainOrder);

    List<MainOrder> queryAll(MainOrder mainOrder);
   
    /**
     * 新增数据
     *
     * @param mainOrder 实例对象
     * @return 影响行数
     */
    int insert(MainOrder mainOrder);
    int countNum(MainOrder mainOrder);
    /**
     * 修改数据
     *
     * @param mainOrder 实例对象
     * @return 影响行数
     */
    int update(MainOrder mainOrder);

    /**
     * 通过主键删除数据
     *
     * @param mainOrderId 主键
     * @return 影响行数
     */
    int deleteById(Integer mainOrderId);

}