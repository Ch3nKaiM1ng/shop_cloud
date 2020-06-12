package com.wx_shop.serviceshop.controller;

import com.wx_shop.serviceshop.entity.SubOrder;
import com.wx_shop.serviceshop.service.SubOrderService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (SubOrder)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:03
 */
@RestController
@RequestMapping("subOrder")
public class SubOrderController {
    /**
     * 服务对象
     */
    @Resource
    private SubOrderService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        SubOrder dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody SubOrder entity) {
     SubOrder dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody SubOrder entity) {
    List<SubOrder> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody SubOrder entity) {
        service.update(entity);
        if(entity.getSubOrderId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody SubOrder entity) {
        service.insert(entity);
        if(entity.getSubOrderId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody SubOrder entity) {
        
        if(entity.getSubOrderId() != null){
        service.deleteById(entity.getSubOrderId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}