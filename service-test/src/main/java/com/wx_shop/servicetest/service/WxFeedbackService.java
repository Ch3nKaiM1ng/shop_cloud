package com.wx_shop.servicetest.service;

import com.wx_shop.servicetest.entity.WxFeedback;
import java.util.List;

/**
 * (WxFeedback)表服务接口
 *
 * @author makejava
 * @since 2020-07-22 14:17:12
 */
public interface WxFeedbackService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    WxFeedback queryById(Integer id);
    
    
    WxFeedback queryObj(WxFeedback wxFeedback);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<WxFeedback> queryAllByLimit(int offset, int limit);
    
    
    List<WxFeedback> queryAll(WxFeedback wxFeedback);
    /**
     * 新增数据
     *
     * @param wxFeedback 实例对象
     * @return 实例对象
     */
    WxFeedback insert(WxFeedback wxFeedback);

    /**
     * 修改数据
     *
     * @param wxFeedback 实例对象
     * @return 实例对象
     */
    WxFeedback update(WxFeedback wxFeedback);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}