package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.SysAuthMenu;
import java.util.List;

/**
 * (SysAuthMenu)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 10:39:04
 */
public interface SysAuthMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysAuthMenu queryById(Integer id);
    
    
    SysAuthMenu queryObj(SysAuthMenu sysAuthMenu);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysAuthMenu> queryAllByLimit(int offset, int limit);
    
    
    List<SysAuthMenu> queryAll(SysAuthMenu sysAuthMenu);
    /**
     * 新增数据
     *
     * @param sysAuthMenu 实例对象
     * @return 实例对象
     */
    SysAuthMenu insert(SysAuthMenu sysAuthMenu);

    /**
     * 修改数据
     *
     * @param sysAuthMenu 实例对象
     * @return 实例对象
     */
    SysAuthMenu update(SysAuthMenu sysAuthMenu);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}