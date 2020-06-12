package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.commodityScore;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Commodity)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 10:13:24
 */
public interface CommodityDao {

    /**
     * 通过ID查询单条数据
     *
     * @param commodityId 主键
     * @return 实例对象
     */
    Commodity queryById(Integer commodityId);

    Integer querySalesById(Integer commodityId);

    Commodity queryObj(Commodity commodity);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Commodity> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<Commodity> queryAll(Commodity commodity);

    List<Commodity> queryCommodityByWx(Commodity commodity);

    /**
     * 新增数据
     *
     * @param commodity 实例对象
     * @return 影响行数
     */
    int insert(Commodity commodity);

    Integer countNum(Commodity commodity);

    /**
     * 修改数据
     *
     * @param commodity 实例对象
     * @return 影响行数
     */
    int update(Commodity commodity);

    /**
     * 通过主键删除数据
     *
     * @param commodityId 主键
     * @return 影响行数
     */
    int deleteById(Integer commodityId);

    commodityScore findAvgScore(commodityScore commodityScore);

}