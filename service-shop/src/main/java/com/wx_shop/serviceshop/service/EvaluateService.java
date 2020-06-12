package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Evaluate;
import java.util.List;

/**
 * (Evaluate)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:00
 */
public interface EvaluateService {

    /**
     * 通过ID查询单条数据
     *
     * @param evaluateId 主键
     * @return 实例对象
     */
    Evaluate queryById(Integer evaluateId);
    
    
    Evaluate queryObj(Evaluate evaluate);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Evaluate> queryAllByLimit(int offset, int limit);
    
    
    List<Evaluate> queryAll(Evaluate evaluate);

    List<Evaluate> findAllByCommodityId(Evaluate evaluate);
    Integer countNum(Evaluate evaluate);
    /**
     * 新增数据
     *
     * @param evaluate 实例对象
     * @return 实例对象
     */
    Evaluate insert(Evaluate evaluate);

    /**
     * 修改数据
     *
     * @param evaluate 实例对象
     * @return 实例对象
     */
    Evaluate update(Evaluate evaluate);

    /**
     * 通过主键删除数据
     *
     * @param evaluateId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer evaluateId);

}