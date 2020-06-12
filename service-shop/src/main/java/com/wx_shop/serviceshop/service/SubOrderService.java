package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.SubOrder;
import java.util.List;

/**
 * (SubOrder)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:03
 */
public interface SubOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param subOrderId 主键
     * @return 实例对象
     */
    SubOrder queryById(Integer subOrderId);
    
    
    SubOrder queryObj(SubOrder subOrder);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SubOrder> queryAllByLimit(int offset, int limit);
    
    
    List<SubOrder> queryAll(SubOrder subOrder);
    /**
     * 新增数据
     *
     * @param subOrder 实例对象
     * @return 实例对象
     */
    SubOrder insert(SubOrder subOrder);

    /**
     * 修改数据
     *
     * @param subOrder 实例对象
     * @return 实例对象
     */
    SubOrder update(SubOrder subOrder);

    /**
     * 通过主键删除数据
     *
     * @param subOrderId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer subOrderId);

}