package com.wx_shop.servicetest.service;

import com.wx_shop.servicetest.entity.WxUser;
import java.util.List;

/**
 * (WxUser)表服务接口
 *
 * @author makejava
 * @since 2020-04-14 16:43:57
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

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxUser> queryAllByLimit(int offset, int limit);
    
    
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

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer userId);

}