package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Shop;
import java.util.List;

/**
 * (Shop)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:02
 */
public interface ShopService {

    /**
     * 通过ID查询单条数据
     *
     * @param shopId 主键
     * @return 实例对象
     */
    Shop queryById(Integer shopId);
    
    
    Shop queryObj(Shop shop);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Shop> queryAllByLimit(int offset, int limit);
    
    
    List<Shop> queryAll(Shop shop);
    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    Shop insert(Shop shop);

    Integer countNum(Shop shop);

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    Shop update(Shop shop);

    /**
     * 通过主键删除数据
     *
     * @param shopId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer shopId);

}