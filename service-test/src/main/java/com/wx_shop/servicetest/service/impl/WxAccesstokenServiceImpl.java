package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.WxAccesstoken;
import com.wx_shop.servicetest.dao.WxAccesstokenDao;
import com.wx_shop.servicetest.service.WxAccesstokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (WxAccesstoken)表服务实现类
 *
 * @author makejava
 * @since 2020-04-14 19:00:39
 */
@Service("wxAccesstokenService")
public class WxAccesstokenServiceImpl implements WxAccesstokenService {
    @Resource
    private WxAccesstokenDao wxAccesstokenDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WxAccesstoken queryById(Integer id) {
        return this.wxAccesstokenDao.queryById(id);
    }
    
    @Override
    public WxAccesstoken queryObj(WxAccesstoken wxAccesstoken) {
        return this.wxAccesstokenDao.queryObj(wxAccesstoken);
    }

    @Override
    public WxAccesstoken queryLastOneByShopId(WxAccesstoken wxAccesstoken) {
        return this.wxAccesstokenDao.queryLastOneByShopId(wxAccesstoken);
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<WxAccesstoken> queryAllByLimit(int offset, int limit) {
        return this.wxAccesstokenDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<WxAccesstoken> queryAll(WxAccesstoken wxAccesstoken) {
        return this.wxAccesstokenDao.queryAll(wxAccesstoken);
    }
    /**
     * 新增数据
     *
     * @param wxAccesstoken 实例对象
     * @return 实例对象
     */
    @Override
    public WxAccesstoken insert(WxAccesstoken wxAccesstoken) {
        this.wxAccesstokenDao.insert(wxAccesstoken);
        return wxAccesstoken;
    }

    /**
     * 修改数据
     *
     * @param wxAccesstoken 实例对象
     * @return 实例对象
     */
    @Override
    public WxAccesstoken update(WxAccesstoken wxAccesstoken) {
        this.wxAccesstokenDao.update(wxAccesstoken);
        return this.queryById(wxAccesstoken.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.wxAccesstokenDao.deleteById(id) > 0;
    }
}