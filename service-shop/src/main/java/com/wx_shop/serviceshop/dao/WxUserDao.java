package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.WxUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (WxUser)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 10:39:09
 */
public interface WxUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    WxUser queryById(Integer userId);
    
    WxUser queryObj(WxUser wxUser);

    Integer countNum(WxUser wxUser);

    List<WxUser> queryAllByLimit(WxUser wxUser);

    List<WxUser> queryAll(WxUser wxUser);
   
    /**
     * 新增数据
     *
     * @param wxUser 实例对象
     * @return 影响行数
     */
    int insert(WxUser wxUser);

    /**
     * 修改数据
     *
     * @param wxUser 实例对象
     * @return 影响行数
     */
    int update(WxUser wxUser);

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 影响行数
     */
    int deleteById(Integer userId);

}