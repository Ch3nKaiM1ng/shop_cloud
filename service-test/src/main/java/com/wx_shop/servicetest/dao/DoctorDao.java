package com.wx_shop.servicetest.dao;

import com.wx_shop.servicetest.entity.Doctor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Doctor)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-04 10:39:23
 */
@Mapper
public interface DoctorDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Doctor queryById(Integer id);
    
    Doctor queryObj(Doctor doctor);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Doctor> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<Doctor> queryAll(Doctor doctor);
   
    /**
     * 新增数据
     *
     * @param doctor 实例对象
     * @return 影响行数
     */
    int insert(Doctor doctor);

    /**
     * 修改数据
     *
     * @param doctor 实例对象
     * @return 影响行数
     */
    int update(Doctor doctor);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}