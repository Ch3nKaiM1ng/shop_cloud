package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.WxUser;
import com.wx_shop.servicetest.dao.WxUserDao;
import com.wx_shop.servicetest.service.WxUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (WxUser)表服务实现类
 *
 * @author makejava
 * @since 2020-04-14 16:43:58
 */
@Service("wxUserService")
public class WxUserServiceImpl implements WxUserService {
    @Resource
    private WxUserDao wxUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userId 主键
     * @return 实例对象
     */
    @Override
    public WxUser queryById(Integer userId) {
        return this.wxUserDao.queryById(userId);
    }
    
    @Override
    public WxUser queryObj(WxUser wxUser) {
        return this.wxUserDao.queryObj(wxUser);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<WxUser> queryAllByLimit(int offset, int limit) {
        return this.wxUserDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<WxUser> queryAll(WxUser wxUser) {
        return this.wxUserDao.queryAll(wxUser);
    }
    /**
     * 新增数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    @Override
    public WxUser insert(WxUser wxUser) {
        this.wxUserDao.insert(wxUser);
        return wxUser;
    }

    /**
     * 修改数据
     *
     * @param wxUser 实例对象
     * @return 实例对象
     */
    @Override
    public WxUser update(WxUser wxUser) {
        this.wxUserDao.update(wxUser);
        return this.queryById(wxUser.getUserId());
    }

    /**
     * 通过主键删除数据
     *
     * @param userId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer userId) {
        return this.wxUserDao.deleteById(userId) > 0;
    }
}