package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.Banner;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Banner)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-31 09:53:54
 */
public interface BannerDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Banner queryById(Integer id);
    
    Banner queryObj(Banner banner);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Banner> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<Banner> queryAll(Banner banner);
   
    /**
     * 新增数据
     *
     * @param banner 实例对象
     * @return 影响行数
     */
    int insert(Banner banner);

    /**
     * 修改数据
     *
     * @param banner 实例对象
     * @return 影响行数
     */
    int update(Banner banner);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}