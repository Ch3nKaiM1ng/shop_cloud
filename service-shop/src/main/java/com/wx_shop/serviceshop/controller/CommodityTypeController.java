package com.wx_shop.serviceshop.controller;

import com.wx_shop.serviceshop.entity.CommodityType;
import com.wx_shop.serviceshop.service.CommodityTypeService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (CommodityType)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:00
 */
@RestController
@RequestMapping("commodityType")
public class CommodityTypeController {
    /**
     * 服务对象
     */
    @Resource
    private CommodityTypeService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        CommodityType dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody CommodityType entity) {
     CommodityType dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody CommodityType entity) {
    List<CommodityType> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("queryAllNotState")
    public Map<String, Object> queryAllNotState(@RequestBody CommodityType entity) {
    List<CommodityType> dataList =service.queryAllNotState(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody CommodityType entity) {
        service.update(entity);
        if(entity.getTypeId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody CommodityType entity) {
        service.insert(entity);
        if(entity.getTypeId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody CommodityType entity) {
        
        if(entity.getTypeId() != null){
        service.deleteById(entity.getTypeId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}