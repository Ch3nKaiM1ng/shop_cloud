package com.wx_shop.serviceshop.dao;

import com.wx_shop.serviceshop.entity.UserAddress;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (UserAddress)表数据库访问层
 *
 * @author makejava
 * @since 2019-12-16 20:21:34
 */
public interface UserAddressDao {

    /**
     * 通过ID查询单条数据
     *
     * @param addressId 主键
     * @return 实例对象
     */
    UserAddress queryById(Integer addressId);
    
    UserAddress queryObj(UserAddress userAddress);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserAddress> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    List<UserAddress> queryAll(UserAddress userAddress);
   
    /**
     * 新增数据
     *
     * @param userAddress 实例对象
     * @return 影响行数
     */
    int insert(UserAddress userAddress);

    /**
     * 修改数据
     *
     * @param userAddress 实例对象
     * @return 影响行数
     */
    int update(UserAddress userAddress);
    int setDefault(UserAddress userAddress);
    int setNotDefault(UserAddress userAddress);
    /**
     * 通过主键删除数据
     *
     * @param addressId 主键
     * @return 影响行数
     */
    int deleteById(Integer addressId);

}