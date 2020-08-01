package com.wx_shop.servicetest.service;

import com.wx_shop.servicetest.entity.Qrscene;
import java.util.List;

/**
 * (Qrscene)表服务接口
 *
 * @author makejava
 * @since 2020-06-04 15:42:27
 */
public interface QrsceneService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Qrscene queryById(Integer id);
    
    
    Qrscene queryObj(Qrscene qrscene);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Qrscene> queryAllByLimit(int offset, int limit);
    
    
    List<Qrscene> queryAll(Qrscene qrscene);

    List<Qrscene> queryAllTest(Qrscene qrscene);
    /**
     * 新增数据
     *
     * @param qrscene 实例对象
     * @return 实例对象
     */
    Qrscene insert(Qrscene qrscene);

    /**
     * 修改数据
     *
     * @param qrscene 实例对象
     * @return 实例对象
     */
    Qrscene update(Qrscene qrscene);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}