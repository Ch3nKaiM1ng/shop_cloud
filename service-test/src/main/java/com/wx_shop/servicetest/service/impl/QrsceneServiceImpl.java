package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.Qrscene;
import com.wx_shop.servicetest.dao.QrsceneDao;
import com.wx_shop.servicetest.service.QrsceneService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Qrscene)表服务实现类
 *
 * @author makejava
 * @since 2020-06-04 15:42:27
 */
@Service("qrsceneService")
public class QrsceneServiceImpl implements QrsceneService {
    @Resource
    private QrsceneDao qrsceneDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Qrscene queryById(Integer id) {
        return this.qrsceneDao.queryById(id);
    }
    
    @Override
    public Qrscene queryObj(Qrscene qrscene) {
        return this.qrsceneDao.queryObj(qrscene);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Qrscene> queryAllByLimit(int offset, int limit) {
        return this.qrsceneDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<Qrscene> queryAll(Qrscene qrscene) {
        return this.qrsceneDao.queryAll(qrscene);
    }
    /**
     * 新增数据
     *
     * @param qrscene 实例对象
     * @return 实例对象
     */
    @Override
    public Qrscene insert(Qrscene qrscene) {
        this.qrsceneDao.insert(qrscene);
        return qrscene;
    }

    /**
     * 修改数据
     *
     * @param qrscene 实例对象
     * @return 实例对象
     */
    @Override
    public Qrscene update(Qrscene qrscene) {
        this.qrsceneDao.update(qrscene);
        return this.queryById(qrscene.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.qrsceneDao.deleteById(id) > 0;
    }
}