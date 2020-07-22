package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.WxFeedback;
import com.wx_shop.servicetest.dao.WxFeedbackDao;
import com.wx_shop.servicetest.service.WxFeedbackService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (WxFeedback)表服务实现类
 *
 * @author makejava
 * @since 2020-07-22 14:17:12
 */
@Service("wxFeedbackService")
public class WxFeedbackServiceImpl implements WxFeedbackService {
    @Resource
    private WxFeedbackDao wxFeedbackDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public WxFeedback queryById(Integer id) {
        return this.wxFeedbackDao.queryById(id);
    }
    
    @Override
    public WxFeedback queryObj(WxFeedback wxFeedback) {
        return this.wxFeedbackDao.queryObj(wxFeedback);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<WxFeedback> queryAllByLimit(int offset, int limit) {
        return this.wxFeedbackDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<WxFeedback> queryAll(WxFeedback wxFeedback) {
        return this.wxFeedbackDao.queryAll(wxFeedback);
    }
    /**
     * 新增数据
     *
     * @param wxFeedback 实例对象
     * @return 实例对象
     */
    @Override
    public WxFeedback insert(WxFeedback wxFeedback) {
        this.wxFeedbackDao.insert(wxFeedback);
        return wxFeedback;
    }

    /**
     * 修改数据
     *
     * @param wxFeedback 实例对象
     * @return 实例对象
     */
    @Override
    public WxFeedback update(WxFeedback wxFeedback) {
        this.wxFeedbackDao.update(wxFeedback);
        return this.queryById(wxFeedback.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.wxFeedbackDao.deleteById(id) > 0;
    }
}