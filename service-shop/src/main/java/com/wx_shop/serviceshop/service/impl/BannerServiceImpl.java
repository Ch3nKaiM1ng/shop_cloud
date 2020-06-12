package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.Banner;
import com.wx_shop.serviceshop.dao.BannerDao;
import com.wx_shop.serviceshop.service.BannerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Banner)表服务实现类
 *
 * @author makejava
 * @since 2019-12-31 09:53:54
 */
@Service("bannerService")
public class BannerServiceImpl implements BannerService {
    @Resource
    private BannerDao bannerDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Banner queryById(Integer id) {
        return this.bannerDao.queryById(id);
    }
    
    @Override
    public Banner queryObj(Banner banner) {
        return this.bannerDao.queryObj(banner);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Banner> queryAllByLimit(int offset, int limit) {
        return this.bannerDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<Banner> queryAll(Banner banner) {
        return this.bannerDao.queryAll(banner);
    }
    /**
     * 新增数据
     *
     * @param banner 实例对象
     * @return 实例对象
     */
    @Override
    public Banner insert(Banner banner) {
        this.bannerDao.insert(banner);
        return banner;
    }

    /**
     * 修改数据
     *
     * @param banner 实例对象
     * @return 实例对象
     */
    @Override
    public Banner update(Banner banner) {
        this.bannerDao.update(banner);
        return this.queryById(banner.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.bannerDao.deleteById(id) > 0;
    }
}