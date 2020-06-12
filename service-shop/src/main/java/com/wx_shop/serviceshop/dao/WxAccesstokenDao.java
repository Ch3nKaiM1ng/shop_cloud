package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.WxAccesstoken;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WxAccesstoken)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-27 10:24:52
 */
public interface WxAccesstokenDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxAccesstoken queryById(Integer id);
    
    WxAccesstoken queryObj(WxAccesstoken wxAccesstoken);

    WxAccesstoken queryLastOneByShopId(WxAccesstoken wxAccesstoken);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxAccesstoken> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<WxAccesstoken> queryAll(WxAccesstoken wxAccesstoken);
   
    /**
     * 新增数据
     *
     * @param wxAccesstoken 实例对象
     * @return 影响行数
     */
    int insert(WxAccesstoken wxAccesstoken);

    /**
     * 修改数据
     *
     * @param wxAccesstoken 实例对象
     * @return 影响行数
     */
    int update(WxAccesstoken wxAccesstoken);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}