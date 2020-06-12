package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.MainOrder;
import com.wx_shop.serviceshop.dao.MainOrderDao;
import com.wx_shop.serviceshop.service.MainOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (MainOrder)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:01
 */
@Service("mainOrderService")
public class MainOrderServiceImpl implements MainOrderService {
    @Resource
    private MainOrderDao mainOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param mainOrderId 主键
     * @return 实例对象
     */
    @Override
    public MainOrder queryById(Integer mainOrderId) {
        return this.mainOrderDao.queryById(mainOrderId);
    }
    
    @Override
    public MainOrder queryObj(MainOrder mainOrder) {
        return this.mainOrderDao.queryObj(mainOrder);
    }


    @Override
    public Integer countNum(MainOrder commodity) {
        return this.mainOrderDao.countNum(commodity);
    }

    @Override
    public List<MainOrder> queryAll(MainOrder mainOrder) {
        return this.mainOrderDao.queryAll(mainOrder);
    }

    @Override
    public List<MainOrder> queryAllByLimit(MainOrder mainOrder) {
        return this.mainOrderDao.queryAllByLimit(mainOrder);
    }
    /**
     * 新增数据
     *
     * @param mainOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MainOrder insert(MainOrder mainOrder) {
        this.mainOrderDao.insert(mainOrder);
        return mainOrder;
    }

    /**
     * 修改数据
     *
     * @param mainOrder 实例对象
     * @return 实例对象
     */
    @Override
    public MainOrder update(MainOrder mainOrder) {
        this.mainOrderDao.update(mainOrder);
        return this.queryById(mainOrder.getMainOrderId());
    }

    /**
     * 通过主键删除数据
     *
     * @param mainOrderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer mainOrderId) {
        return this.mainOrderDao.deleteById(mainOrderId) > 0;
    }
}