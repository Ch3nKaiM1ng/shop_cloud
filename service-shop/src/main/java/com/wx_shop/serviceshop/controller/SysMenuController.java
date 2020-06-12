package com.wx_shop.serviceshop.controller;

import com.wx_shop.serviceshop.entity.SysMenu;
import com.wx_shop.serviceshop.service.SysMenuService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (SysMenu)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:06
 */
@RestController
@RequestMapping("sysMenu")
public class SysMenuController {
    /**
     * 服务对象
     */
    @Resource
    private SysMenuService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        SysMenu dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody SysMenu entity) {
     SysMenu dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody SysMenu entity) {
    List<SysMenu> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody SysMenu entity) {
        service.update(entity);
        if(entity.getMenuId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody SysMenu entity) {
        service.insert(entity);
        if(entity.getMenuId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody SysMenu entity) {
        
        if(entity.getMenuId() != null){
        service.deleteById(entity.getMenuId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}