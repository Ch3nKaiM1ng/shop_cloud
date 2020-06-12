package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.SysMenu;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (SysMenu)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 10:39:05
 */
public interface SysMenuDao {

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    SysMenu queryById(Integer menuId);
    
    SysMenu queryObj(SysMenu sysMenu);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<SysMenu> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<SysMenu> queryAll(SysMenu sysMenu);
   
    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int insert(SysMenu sysMenu);

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 影响行数
     */
    int update(SysMenu sysMenu);

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 影响行数
     */
    int deleteById(Integer menuId);

}