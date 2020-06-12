package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.WxAppdata;
import java.util.List;

/**
 * (WxAppdata)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:08
 */
public interface WxAppdataService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxAppdata queryById(Integer id);
    
    
    WxAppdata queryObj(WxAppdata wxAppdata);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxAppdata> queryAllByLimit(int offset, int limit);
    
    
    List<WxAppdata> queryAll(WxAppdata wxAppdata);
    /**
     * 新增数据
     *
     * @param wxAppdata 实例对象
     * @return 实例对象
     */
    WxAppdata insert(WxAppdata wxAppdata);

    /**
     * 修改数据
     *
     * @param wxAppdata 实例对象
     * @return 实例对象
     */
    WxAppdata update(WxAppdata wxAppdata);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}