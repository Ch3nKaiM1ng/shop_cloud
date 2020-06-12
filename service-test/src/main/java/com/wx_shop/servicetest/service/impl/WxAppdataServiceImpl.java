package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.WxAppdata;
import com.wx_shop.servicetest.dao.WxAppdataDao;
import com.wx_shop.servicetest.service.WxAppdataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (WxAppdata)表服务实现类
 *
 * @author makejava
 * @since 2020-04-14 16:41:31
 */
@Service("wxAppdataService")
public class WxAppdataServiceImpl implements WxAppdataService {
    @Resource
    private WxAppdataDao wxAppdataDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WxAppdata queryById(Integer id) {
        return this.wxAppdataDao.queryById(id);
    }
    
    @Override
    public WxAppdata queryObj(WxAppdata wxAppdata) {
        return this.wxAppdataDao.queryObj(wxAppdata);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<WxAppdata> queryAllByLimit(int offset, int limit) {
        return this.wxAppdataDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<WxAppdata> queryAll(WxAppdata wxAppdata) {
        return this.wxAppdataDao.queryAll(wxAppdata);
    }
    /**
     * 新增数据
     *
     * @param wxAppdata 实例对象
     * @return 实例对象
     */
    @Override
    public WxAppdata insert(WxAppdata wxAppdata) {
        this.wxAppdataDao.insert(wxAppdata);
        return wxAppdata;
    }

    /**
     * 修改数据
     *
     * @param wxAppdata 实例对象
     * @return 实例对象
     */
    @Override
    public WxAppdata update(WxAppdata wxAppdata) {
        this.wxAppdataDao.update(wxAppdata);
        return this.queryById(wxAppdata.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.wxAppdataDao.deleteById(id) > 0;
    }
}