package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.Qrscene;
import com.wx_shop.servicetest.service.QrsceneService;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (Qrscene)表控制层
 *
 * @author makejava
 * @since 2020-06-04 15:42:27
 */
@RestController
@RequestMapping("qrscene")
public class QrsceneController {
    /**
     * 服务对象
     */
    @Resource
    private QrsceneService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        Qrscene dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody Qrscene entity) {
     Qrscene dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody Qrscene entity) {
    List<Qrscene> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("queryAllTest")
    public Map<String, Object> queryAllTest(@RequestBody Qrscene entity) {
        List<Qrscene> dataList =service.queryAllTest(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody Qrscene entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody Qrscene entity) {
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody Qrscene entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}