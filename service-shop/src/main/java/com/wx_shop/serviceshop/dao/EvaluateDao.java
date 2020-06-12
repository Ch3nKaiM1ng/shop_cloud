package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.Evaluate;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Evaluate)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 10:39:00
 */
public interface EvaluateDao {

    /**
     * 通过ID查询单条数据
     *
     * @param evaluateId 主键
     * @return 实例对象
     */
    Evaluate queryById(Integer evaluateId);
    
    Evaluate queryObj(Evaluate evaluate);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Evaluate> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<Evaluate> queryAll(Evaluate evaluate);

    List<Evaluate> findAllByCommodityId(Evaluate evaluate);

    /**
     * 新增数据
     *
     * @param evaluate 实例对象
     * @return 影响行数
     */
    int insert(Evaluate evaluate);

    /**
     * 修改数据
     *
     * @param evaluate 实例对象
     * @return 影响行数
     */
    int update(Evaluate evaluate);

    /**
     * 通过主键删除数据
     *
     * @param evaluateId 主键
     * @return 影响行数
     */
    int deleteById(Integer evaluateId);

    Integer countNum(Evaluate evaluate);
}