package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.CommodityType;
import java.util.List;

/**
 * (CommodityType)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:38:59
 */
public interface CommodityTypeService {

    /**
     * 通过ID查询单条数据
     *
     * @param typeId 主键
     * @return 实例对象
     */
    CommodityType queryById(Integer typeId);
    
    
    CommodityType queryObj(CommodityType commodityType);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<CommodityType> queryAllByLimit(int offset, int limit);
    
    
    List<CommodityType> queryAll(CommodityType commodityType);

    List<CommodityType> queryAllByWx(CommodityType commodityType);


    List<CommodityType> queryAllNotState(CommodityType commodityType);
    /**
     * 新增数据
     *
     * @param commodityType 实例对象
     * @return 实例对象
     */
    CommodityType insert(CommodityType commodityType);

    /**
     * 修改数据
     *
     * @param commodityType 实例对象
     * @return 实例对象
     */
    CommodityType update(CommodityType commodityType);

    /**
     * 通过主键删除数据
     *
     * @param typeId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer typeId);

}