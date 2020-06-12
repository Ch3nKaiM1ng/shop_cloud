package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.dao.CommodityDao;
import com.wx_shop.serviceshop.entity.commodityScore;
import com.wx_shop.serviceshop.service.CommodityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Commodity)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:13:24
 */
@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {
    @Resource
    private CommodityDao commodityDao;

    /**
     * 通过ID查询单条数据
     *
     * @param commodityId 主键
     * @return 实例对象
     */
    @Override
    public Commodity queryById(Integer commodityId) {
        return this.commodityDao.queryById(commodityId);
    }
    
    @Override
    public Commodity queryObj(Commodity commodity) {
        return this.commodityDao.queryObj(commodity);
    }

    @Override
    public Integer countNum(Commodity commodity) {
        return this.commodityDao.countNum(commodity);
    }

    @Override
    public Integer querySalesById(Integer commodityId) {
        return this.commodityDao.querySalesById(commodityId);
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Commodity> queryAllByLimit(int offset, int limit) {
        return this.commodityDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<Commodity> queryAll(Commodity commodity) {
        return this.commodityDao.queryAll(commodity);
    }
    /**
     * 新增数据
     *
     * @param commodity 实例对象
     * @return 实例对象
     */
    @Override
    public Commodity insert(Commodity commodity) {
        this.commodityDao.insert(commodity);
        return commodity;
    }

    /**
     * 修改数据
     *
     * @param commodity 实例对象
     * @return 实例对象
     */
    @Override
    public Commodity update(Commodity commodity) {
        this.commodityDao.update(commodity);
        return this.queryById(commodity.getCommodityId());
    }

    /**
     * 通过主键删除数据
     *
     * @param commodityId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer commodityId) {
        return this.commodityDao.deleteById(commodityId) > 0;
    }

    @Override
    public commodityScore findAvgScore(commodityScore commodityScore) {
        return this.commodityDao.findAvgScore(commodityScore);
    }

}