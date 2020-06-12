package com.wx_shop.servicetest.service;

import com.wx_shop.servicetest.entity.WxOrder;
import java.util.List;

/**
 * (WxOrder)表服务接口
 *
 * @author makejava
 * @since 2020-04-14 16:43:14
 */
public interface WxOrderService {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    WxOrder queryById(Integer orderId);
    
    
    WxOrder queryObj(WxOrder wxOrder);

    WxOrder queryLastOne(WxOrder wxOrder);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxOrder> queryAllByLimit(int offset, int limit);

    Integer countFasterThanU(WxOrder wxOrder);

    List<WxOrder> queryAll(WxOrder wxOrder);

    List<WxOrder> queryTopList(WxOrder wxOrder);

    List<WxOrder> queryTopListByorderNum(WxOrder wxOrder);

    /**
     * 新增数据
     *
     * @param wxOrder 实例对象
     * @return 实例对象
     */
    WxOrder insert(WxOrder wxOrder);

    /**
     * 修改数据
     *
     * @param wxOrder 实例对象
     * @return 实例对象
     */
    WxOrder update(WxOrder wxOrder);

    WxOrder callOrder(WxOrder wxOrder);

    WxOrder doOrder(WxOrder wxOrder);

    WxOrder orderArrive(WxOrder wxOrder);
    WxOrder orderJump(WxOrder wxOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer orderId);

}