package com.wx_shop.serviceshop.service.impl;

import com.wx_shop.serviceshop.entity.UserAddress;
import com.wx_shop.serviceshop.dao.UserAddressDao;
import com.wx_shop.serviceshop.service.UserAddressService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (UserAddress)表服务实现类
 *
 * @author makejava
 * @since 2019-12-16 20:21:35
 */
@Service("userAddressService")
public class UserAddressServiceImpl implements UserAddressService {
    @Resource
    private UserAddressDao userAddressDao;

    /**
     * 通过ID查询单条数据
     *
     * @param addressId 主键
     * @return 实例对象
     */
    @Override
    public UserAddress queryById(Integer addressId) {
        return this.userAddressDao.queryById(addressId);
    }
    
    @Override
    public UserAddress queryObj(UserAddress userAddress) {
        return this.userAddressDao.queryObj(userAddress);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<UserAddress> queryAllByLimit(int offset, int limit) {
        return this.userAddressDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<UserAddress> queryAll(UserAddress userAddress) {
        return this.userAddressDao.queryAll(userAddress);
    }
    /**
     * 新增数据
     *
     * @param userAddress 实例对象
     * @return 实例对象
     */
    @Override
    public UserAddress insert(UserAddress userAddress) {
        this.userAddressDao.insert(userAddress);
        return userAddress;
    }

    /**
     * 修改数据
     *
     * @param userAddress 实例对象
     * @return 实例对象
     */
    @Override
    public UserAddress update(UserAddress userAddress) {
        this.userAddressDao.update(userAddress);
        return this.queryById(userAddress.getAddressId());
    }


    @Override
    public UserAddress setDefault(UserAddress userAddress) {
        this.userAddressDao.setDefault(userAddress);
        this.userAddressDao.setNotDefault(userAddress);
        return this.queryById(userAddress.getAddressId());
    }

    /**
     * 通过主键删除数据
     *
     * @param addressId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer addressId) {
        return this.userAddressDao.deleteById(addressId) > 0;
    }
}