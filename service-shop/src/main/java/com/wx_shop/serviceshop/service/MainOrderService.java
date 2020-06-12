package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.MainOrder;
import java.util.List;

/**
 * (MainOrder)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:01
 */
public interface MainOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param mainOrderId 主键
     * @return 实例对象
     */
    MainOrder queryById(Integer mainOrderId);
    
    
    MainOrder queryObj(MainOrder mainOrder);

    
    
    List<MainOrder> queryAll(MainOrder mainOrder);

    List<MainOrder> queryAllByLimit(MainOrder mainOrder);

    Integer countNum(MainOrder mainOrder);
    /**
     * 新增数据
     *
     * @param mainOrder 实例对象
     * @return 实例对象
     */
    MainOrder insert(MainOrder mainOrder);

    /**
     * 修改数据
     *
     * @param mainOrder 实例对象
     * @return 实例对象
     */
    MainOrder update(MainOrder mainOrder);

    /**
     * 通过主键删除数据
     *
     * @param mainOrderId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer mainOrderId);

}