package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.SysMenu;
import com.wx_shop.serviceshop.dao.SysMenuDao;
import com.wx_shop.serviceshop.service.SysMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysMenu)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:06
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuDao sysMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param menuId 主键
     * @return 实例对象
     */
    @Override
    public SysMenu queryById(Integer menuId) {
        return this.sysMenuDao.queryById(menuId);
    }
    
    @Override
    public SysMenu queryObj(SysMenu sysMenu) {
        return this.sysMenuDao.queryObj(sysMenu);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysMenu> queryAllByLimit(int offset, int limit) {
        return this.sysMenuDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<SysMenu> queryAll(SysMenu sysMenu) {
        return this.sysMenuDao.queryAll(sysMenu);
    }
    /**
     * 新增数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu insert(SysMenu sysMenu) {
        this.sysMenuDao.insert(sysMenu);
        return sysMenu;
    }

    /**
     * 修改数据
     *
     * @param sysMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysMenu update(SysMenu sysMenu) {
        this.sysMenuDao.update(sysMenu);
        return this.queryById(sysMenu.getMenuId());
    }

    /**
     * 通过主键删除数据
     *
     * @param menuId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer menuId) {
        return this.sysMenuDao.deleteById(menuId) > 0;
    }
}