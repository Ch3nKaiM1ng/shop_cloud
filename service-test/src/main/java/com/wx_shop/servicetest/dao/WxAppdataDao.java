package com.wx_shop.servicetest.dao;

import com.wx_shop.servicetest.entity.WxAppdata;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WxAppdata)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-14 16:41:30
 */
@Mapper
public interface WxAppdataDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxAppdata queryById(Integer id);
    
    WxAppdata queryObj(WxAppdata wxAppdata);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxAppdata> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<WxAppdata> queryAll(WxAppdata wxAppdata);
   
    /**
     * 新增数据
     *
     * @param wxAppdata 实例对象
     * @return 影响行数
     */
    int insert(WxAppdata wxAppdata);

    /**
     * 修改数据
     *
     * @param wxAppdata 实例对象
     * @return 影响行数
     */
    int update(WxAppdata wxAppdata);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}