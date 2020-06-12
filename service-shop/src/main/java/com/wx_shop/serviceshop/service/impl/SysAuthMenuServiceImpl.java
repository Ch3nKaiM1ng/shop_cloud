package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.SysAuthMenu;
import com.wx_shop.serviceshop.dao.SysAuthMenuDao;
import com.wx_shop.serviceshop.service.SysAuthMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (SysAuthMenu)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 10:39:04
 */
@Service("sysAuthMenuService")
public class SysAuthMenuServiceImpl implements SysAuthMenuService {
    @Resource
    private SysAuthMenuDao sysAuthMenuDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysAuthMenu queryById(Integer id) {
        return this.sysAuthMenuDao.queryById(id);
    }
    
    @Override
    public SysAuthMenu queryObj(SysAuthMenu sysAuthMenu) {
        return this.sysAuthMenuDao.queryObj(sysAuthMenu);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<SysAuthMenu> queryAllByLimit(int offset, int limit) {
        return this.sysAuthMenuDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<SysAuthMenu> queryAll(SysAuthMenu sysAuthMenu) {
        return this.sysAuthMenuDao.queryAll(sysAuthMenu);
    }
    /**
     * 新增数据
     *
     * @param sysAuthMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysAuthMenu insert(SysAuthMenu sysAuthMenu) {
        this.sysAuthMenuDao.insert(sysAuthMenu);
        return sysAuthMenu;
    }

    /**
     * 修改数据
     *
     * @param sysAuthMenu 实例对象
     * @return 实例对象
     */
    @Override
    public SysAuthMenu update(SysAuthMenu sysAuthMenu) {
        this.sysAuthMenuDao.update(sysAuthMenu);
        return this.queryById(sysAuthMenu.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.sysAuthMenuDao.deleteById(id) > 0;
    }
}