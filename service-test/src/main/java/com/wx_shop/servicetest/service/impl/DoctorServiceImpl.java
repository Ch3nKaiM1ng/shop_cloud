package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.Doctor;
import com.wx_shop.servicetest.dao.DoctorDao;
import com.wx_shop.servicetest.service.DoctorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Doctor)表服务实现类
 *
 * @author makejava
 * @since 2020-06-04 10:39:23
 */
@Service("doctorService")
public class DoctorServiceImpl implements DoctorService {
    @Resource
    private DoctorDao doctorDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Doctor queryById(Integer id) {
        return this.doctorDao.queryById(id);
    }
    
    @Override
    public Doctor queryObj(Doctor doctor) {
        return this.doctorDao.queryObj(doctor);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<Doctor> queryAllByLimit(int offset, int limit) {
        return this.doctorDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<Doctor> queryAll(Doctor doctor) {
        return this.doctorDao.queryAll(doctor);
    }
    /**
     * 新增数据
     *
     * @param doctor 实例对象
     * @return 实例对象
     */
    @Override
    public Doctor insert(Doctor doctor) {
        this.doctorDao.insert(doctor);
        return doctor;
    }

    /**
     * 修改数据
     *
     * @param doctor 实例对象
     * @return 实例对象
     */
    @Override
    public Doctor update(Doctor doctor) {
        this.doctorDao.update(doctor);
        return this.queryById(doctor.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.doctorDao.deleteById(id) > 0;
    }
}