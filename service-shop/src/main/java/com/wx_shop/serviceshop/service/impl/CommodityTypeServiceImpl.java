package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.CommodityType;
import com.wx_shop.serviceshop.dao.CommodityTypeDao;
import com.wx_shop.serviceshop.service.CommodityTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CommodityType)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:00
 */
@Service("commodityTypeService")
public class CommodityTypeServiceImpl implements CommodityTypeService {
    @Resource
    private CommodityTypeDao commodityTypeDao;

    /**
     * 通过ID查询单条数据
     *
     * @param typeId 主键
     * @return 实例对象
     */
    @Override
    public CommodityType queryById(Integer typeId) {
        return this.commodityTypeDao.queryById(typeId);
    }
    
    @Override
    public CommodityType queryObj(CommodityType commodityType) {
        return this.commodityTypeDao.queryObj(commodityType);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<CommodityType> queryAllByLimit(int offset, int limit) {
        return this.commodityTypeDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<CommodityType> queryAll(CommodityType commodityType) {
        return this.commodityTypeDao.queryAll(commodityType);
    }
    @Override
    public List<CommodityType> queryAllByWx(CommodityType commodityType) {
        return this.commodityTypeDao.queryAllByWx(commodityType);
    }
    @Override
    public List<CommodityType> queryAllNotState(CommodityType commodityType) {
        return this.commodityTypeDao.queryAll(commodityType);
    }
    /**
     * 新增数据
     *
     * @param commodityType 实例对象
     * @return 实例对象
     */
    @Override
    public CommodityType insert(CommodityType commodityType) {
        this.commodityTypeDao.insert(commodityType);
        return commodityType;
    }

    /**
     * 修改数据
     *
     * @param commodityType 实例对象
     * @return 实例对象
     */
    @Override
    public CommodityType update(CommodityType commodityType) {
        this.commodityTypeDao.update(commodityType);
        return this.queryById(commodityType.getTypeId());
    }

    /**
     * 通过主键删除数据
     *
     * @param typeId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer typeId) {
        return this.commodityTypeDao.deleteById(typeId) > 0;
    }
}