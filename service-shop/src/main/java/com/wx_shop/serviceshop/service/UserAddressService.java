package com.wx_shop.serviceshop.service;

import com.wx_shop.serviceshop.entity.UserAddress;
import java.util.List;

/**
 * (UserAddress)表服务接口
 *
 * @author makejava
 * @since 2019-12-16 20:21:35
 */
public interface UserAddressService {

    /**
     * 通过ID查询单条数据
     *
     * @param addressId 主键
     * @return 实例对象
     */
    UserAddress queryById(Integer addressId);
    
    
    UserAddress queryObj(UserAddress userAddress);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UserAddress> queryAllByLimit(int offset, int limit);
    
    
    List<UserAddress> queryAll(UserAddress userAddress);
    /**
     * 新增数据
     *
     * @param userAddress 实例对象
     * @return 实例对象
     */
    UserAddress insert(UserAddress userAddress);

    /**
     * 修改数据
     *
     * @param userAddress 实例对象
     * @return 实例对象
     */
    UserAddress update(UserAddress userAddress);

    UserAddress setDefault(UserAddress userAddress);

    /**
     * 通过主键删除数据
     *
     * @param addressId 主键
     * @return 是否成功
     */
    boolean deleteById(Integer addressId);

}