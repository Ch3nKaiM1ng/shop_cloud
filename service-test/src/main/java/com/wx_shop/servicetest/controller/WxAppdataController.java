package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.WxAppdata;
import com.wx_shop.servicetest.service.WxAppdataService;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (WxAppdata)表控制层
 *
 * @author makejava
 * @since 2020-04-14 16:41:32
 */
@RestController
@RequestMapping("wxAppdata")
public class WxAppdataController {
    /**
     * 服务对象
     */
    @Resource
    private WxAppdataService service;

    private ReturnDiscern re =new ReturnDiscern();


    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        WxAppdata dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }

    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxAppdata entity) {
     WxAppdata dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }


    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxAppdata entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxAppdata entity) {
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }


    @RequestMapping("findAll")
    @Transactional
    public Map<String, Object> findAll(@RequestBody WxAppdata entity) {
        List<WxAppdata> dataList =service.queryAll(entity);

        List<WxAppdata> objList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxAppdata entity) {

        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}