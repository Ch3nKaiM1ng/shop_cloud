package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.WxFeedback;
import com.wx_shop.servicetest.service.WxFeedbackService;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (WxFeedback)表控制层
 *
 * @author makejava
 * @since 2020-07-22 14:17:12
 */
@RestController
@RequestMapping("wxFeedback")
public class WxFeedbackController {
    /**
     * 服务对象
     */
    @Resource
    private WxFeedbackService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        WxFeedback dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxFeedback entity) {
     WxFeedback dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody WxFeedback entity) {
    List<WxFeedback> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxFeedback entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxFeedback entity) {
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxFeedback entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}