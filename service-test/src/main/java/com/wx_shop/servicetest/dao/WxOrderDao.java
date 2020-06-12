package com.wx_shop.servicetest.dao;

import com.wx_shop.servicetest.entity.WxOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WxOrder)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-14 16:43:13
 */
@Mapper
public interface WxOrderDao {

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    WxOrder queryById(Integer orderId);
    
    WxOrder queryObj(WxOrder wxOrder);
    WxOrder queryLastOne(WxOrder wxOrder);
    WxOrder queryLastOneByap(WxOrder wxOrder);
    WxOrder queryTopOneByap(WxOrder wxOrder);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxOrder> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<WxOrder> queryAll(WxOrder wxOrder);

    List<WxOrder> queryTopList(WxOrder wxOrder);
    List<WxOrder> queryTopListByorderNum(WxOrder wxOrder);

    /**
     * 新增数据
     *
     * @param wxOrder 实例对象
     * @return 影响行数
     */
    int insert(WxOrder wxOrder);

    Integer countFasterThanU(WxOrder wxOrder);

    Integer checkNowOrderNum(WxOrder wxOrder);

    /**
     * 修改数据
     *
     * @param wxOrder 实例对象
     * @return 影响行数
     */
    int update(WxOrder wxOrder);

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 影响行数
     */
    int deleteById(Integer orderId);

}