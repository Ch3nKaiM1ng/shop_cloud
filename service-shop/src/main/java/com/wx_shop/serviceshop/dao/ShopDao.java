package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Shop;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Shop)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 10:39:02
 */
public interface ShopDao {

    /**
     * 通过ID查询单条数据
     *
     * @param shopId 主键
     * @return 实例对象
     */
    Shop queryById(Integer shopId);
    
    Shop queryObj(Shop shop);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Shop> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<Shop> queryAll(Shop shop);

    Integer countNum(Shop shop);

    /**
     * 新增数据
     *
     * @param shop 实例对象
     * @return 影响行数
     */
    int insert(Shop shop);

    /**
     * 修改数据
     *
     * @param shop 实例对象
     * @return 影响行数
     */
    int update(Shop shop);

    /**
     * 通过主键删除数据
     *
     * @param shopId 主键
     * @return 影响行数
     */
    int deleteById(Integer shopId);

}