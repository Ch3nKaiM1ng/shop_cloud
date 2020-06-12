package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.WxUser;
import com.wx_shop.servicetest.service.WxUserService;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (WxUser)表控制层
 *
 * @author makejava
 * @since 2020-04-14 16:43:58
 */
@RestController
@RequestMapping("wxUser")
public class WxUserController {
    /**
     * 服务对象
     */
    @Resource
    private WxUserService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        WxUser dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxUser entity) {
     WxUser dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody WxUser entity) {
    List<WxUser> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxUser entity) {
        service.update(entity);
        if(entity.getUserId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxUser entity) {
        service.insert(entity);
        if(entity.getUserId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxUser entity) {
        
        if(entity.getUserId() != null){
        service.deleteById(entity.getUserId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}