package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Evaluate;
import com.wx_shop.serviceshop.dao.EvaluateDao;
import com.wx_shop.serviceshop.service.EvaluateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Evaluate)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:01
 */
@Service("evaluateService")
public class EvaluateServiceImpl implements EvaluateService {
    @Resource
    private EvaluateDao evaluateDao;

    /**
     * 通过ID查询单条数据
     *
     * @param evaluateId 主键
     * @return 实例对象
     */
    @Override
    public Evaluate queryById(Integer evaluateId) {
        return this.evaluateDao.queryById(evaluateId);
    }
    
    @Override
    public Evaluate queryObj(Evaluate evaluate) {
        return this.evaluateDao.queryObj(evaluate);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Evaluate> queryAllByLimit(int offset, int limit) {
        return this.evaluateDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<Evaluate> queryAll(Evaluate evaluate) {
        return this.evaluateDao.queryAll(evaluate);
    }

    @Override
    public List<Evaluate> findAllByCommodityId(Evaluate evaluate) {
        return this.evaluateDao.findAllByCommodityId(evaluate);
    }
    /**
     * 新增数据
     *
     * @param evaluate 实例对象
     * @return 实例对象
     */
    @Override
    public Evaluate insert(Evaluate evaluate) {
        this.evaluateDao.insert(evaluate);
        return evaluate;
    }

    /**
     * 修改数据
     *
     * @param evaluate 实例对象
     * @return 实例对象
     */
    @Override
    public Evaluate update(Evaluate evaluate) {
        this.evaluateDao.update(evaluate);
        return this.queryById(evaluate.getEvaluateId());
    }

    /**
     * 通过主键删除数据
     *
     * @param evaluateId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer evaluateId) {
        return this.evaluateDao.deleteById(evaluateId) > 0;
    }

    @Override
    public Integer countNum(Evaluate evaluate) {
        return this.evaluateDao.countNum(evaluate);
    }
}