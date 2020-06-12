package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.SubOrder;
import com.wx_shop.serviceshop.dao.SubOrderDao;
import com.wx_shop.serviceshop.service.SubOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SubOrder)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:03
 */
@Service("subOrderService")
public class SubOrderServiceImpl implements SubOrderService {
    @Resource
    private SubOrderDao subOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param subOrderId 主键
     * @return 实例对象
     */
    @Override
    public SubOrder queryById(Integer subOrderId) {
        return this.subOrderDao.queryById(subOrderId);
    }
    
    @Override
    public SubOrder queryObj(SubOrder subOrder) {
        return this.subOrderDao.queryObj(subOrder);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SubOrder> queryAllByLimit(int offset, int limit) {
        return this.subOrderDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<SubOrder> queryAll(SubOrder subOrder) {
        return this.subOrderDao.queryAll(subOrder);
    }
    /**
     * 新增数据
     *
     * @param subOrder 实例对象
     * @return 实例对象
     */
    @Override
    public SubOrder insert(SubOrder subOrder) {
        this.subOrderDao.insert(subOrder);
        return subOrder;
    }

    /**
     * 修改数据
     *
     * @param subOrder 实例对象
     * @return 实例对象
     */
    @Override
    public SubOrder update(SubOrder subOrder) {
        this.subOrderDao.update(subOrder);
        return this.queryById(subOrder.getSubOrderId());
    }

    /**
     * 通过主键删除数据
     *
     * @param subOrderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer subOrderId) {
        return this.subOrderDao.deleteById(subOrderId) > 0;
    }
}