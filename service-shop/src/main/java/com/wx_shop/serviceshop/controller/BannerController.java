package com.wx_shop.serviceshop.controller;

import com.wx_shop.serviceshop.entity.Banner;
import com.wx_shop.serviceshop.service.BannerService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (Banner)表控制层
 *
 * @author makejava
 * @since 2019-12-31 09:53:54
 */
@RestController
@RequestMapping("banner")
public class BannerController {
    /**
     * 服务对象
     */
    @Resource
    private BannerService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        Banner dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody Banner entity) {
     Banner dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody Banner entity) {
    List<Banner> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody Banner entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody Banner entity) {
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody Banner entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}