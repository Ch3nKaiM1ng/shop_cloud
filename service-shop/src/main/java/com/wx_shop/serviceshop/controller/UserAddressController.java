package com.wx_shop.serviceshop.controller;

import com.wx_shop.serviceshop.entity.UserAddress;
import com.wx_shop.serviceshop.service.UserAddressService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (UserAddress)表控制层
 *
 * @author makejava
 * @since 2019-12-16 20:21:35
 */
@RestController
@RequestMapping("userAddress")
public class UserAddressController {
    /**
     * 服务对象
     */
    @Resource
    private UserAddressService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        UserAddress dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody UserAddress entity) {
     UserAddress dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody UserAddress entity) {
    List<UserAddress> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody UserAddress entity) {
        service.update(entity);
        if(entity.getAddressId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    @RequestMapping("setDefault")
    public Map<String, Object> setDefault(@RequestBody UserAddress entity) {
        service.setDefault(entity);
        return re.SUCCESS();
    }
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody UserAddress entity) {
        service.insert(entity);
        if(entity.getAddressId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody UserAddress entity) {
        
        if(entity.getAddressId() != null){
        service.deleteById(entity.getAddressId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}