package com.wx_shop.servicetest.dao;

import com.wx_shop.servicetest.entity.WxFeedback;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WxFeedback)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-22 14:17:12
 */
@Mapper
public interface WxFeedbackDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxFeedback queryById(Integer id);
    
    WxFeedback queryObj(WxFeedback wxFeedback);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxFeedback> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<WxFeedback> queryAll(WxFeedback wxFeedback);
   
    /**
     * 新增数据
     *
     * @param wxFeedback 实例对象
     * @return 影响行数
     */
    int insert(WxFeedback wxFeedback);

    /**
     * 修改数据
     *
     * @param wxFeedback 实例对象
     * @return 影响行数
     */
    int update(WxFeedback wxFeedback);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}