package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.commodityScore;

import java.util.List;

/**
 * (Commodity)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:13:24
 */
public interface CommodityService {

    /**
     * 通过ID查询单条数据
     *
     * @param commodityId 主键
     * @return 实例对象
     */
    Commodity queryById(Integer commodityId);
    
    
    Commodity queryObj(Commodity commodity);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Commodity> queryAllByLimit(int offset, int limit);
    
    
    List<Commodity> queryAll(Commodity commodity);


    Integer countNum(Commodity commodity);
    /**
     * 新增数据
     *
     * @param commodity 实例对象
     * @return 实例对象
     */
    Commodity insert(Commodity commodity);

    /**
     * 修改数据
     *
     * @param commodity 实例对象
     * @return 实例对象
     */
    Commodity update(Commodity commodity);

    Integer querySalesById(Integer commodityId);

    /**
     * 通过主键删除数据
     *
     * @param commodityId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer commodityId);

    commodityScore findAvgScore(commodityScore commodityScore);

}