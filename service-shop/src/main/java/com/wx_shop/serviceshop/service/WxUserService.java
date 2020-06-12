package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.WxUser;
import java.util.List;

/**
 * (WxUser)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:09
 */
public interface WxUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    WxUser queryById(Integer userId);
    
    
    WxUser queryObj(WxUser wxUser);

    List<WxUser> queryAllByLimit(WxUser wxUser);
    
    
    List<WxUser> queryAll(WxUser wxUser);
    /**
     * 新增数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    WxUser insert(WxUser wxUser);

    /**
     * 修改数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    WxUser update(WxUser wxUser);

    Integer countNum(WxUser wxUser);
    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

}