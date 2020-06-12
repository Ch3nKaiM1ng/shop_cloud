package com.wx_shop.servicetest.service;

import com.wx_shop.servicetest.entity.WxAccesstoken;
import java.util.List;

/**
 * (WxAccesstoken)表服务接口
 *
 * @author makejava
 * @since 2020-04-14 19:00:39
 */
public interface WxAccesstokenService {

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
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxAccesstoken> queryAllByLimit(int offset, int limit);
    
    
    List<WxAccesstoken> queryAll(WxAccesstoken wxAccesstoken);
    /**
     * 新增数据
     *
     * @param wxAccesstoken 实例对象
     * @return 实例对象
     */
    WxAccesstoken insert(WxAccesstoken wxAccesstoken);

    /**
     * 修改数据
     *
     * @param wxAccesstoken 实例对象
     * @return 实例对象
     */
    WxAccesstoken update(WxAccesstoken wxAccesstoken);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}