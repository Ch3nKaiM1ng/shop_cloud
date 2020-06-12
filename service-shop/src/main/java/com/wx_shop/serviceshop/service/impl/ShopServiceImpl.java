package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Shop;
import com.wx_shop.serviceshop.dao.ShopDao;
import com.wx_shop.serviceshop.service.ShopService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Shop)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:02
 */
@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Resource
    private ShopDao shopDao;

    /**
     * 通过ID查询单条数据
     *
     * @param shopId 主键
     * @return 实例对象
     */
    @Override
    public Shop queryById(Integer shopId) {
        return this.shopDao.queryById(shopId);
    }
    
    @Override
    public Shop queryObj(Shop shop) {
        return this.shopDao.queryObj(shop);
    }

    @Override
    public Integer countNum(Shop shop) {
        return this.shopDao.countNum(shop);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Shop> queryAllByLimit(int offset, int limit) {
        return this.shopDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<Shop> queryAll(Shop shop) {
        return this.shopDao.queryAll(shop);
    }
    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    @Override
    public Shop insert(Shop shop) {
        this.shopDao.insert(shop);
        return shop;
    }

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 实例对象
     */
    @Override
    public Shop update(Shop shop) {
        this.shopDao.update(shop);
        return this.queryById(shop.getShopId());
    }

    /**
     * 通过主键删除数据
     *
     * @param shopId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer shopId) {
        return this.shopDao.deleteById(shopId) > 0;
    }
}