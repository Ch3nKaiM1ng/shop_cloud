package com.wx_shop.serviceshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Shop;
import com.wx_shop.serviceshop.service.ShopService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (Shop)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:02
 */
@RestController
@RequestMapping("shop")
public class ShopController {
    /**
     * 服务对象
     */
    @Resource
    private ShopService service;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        Shop dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody Shop entity) {
     Shop dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody Shop entity) {
    List<Shop> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("findAllPage")
    public Map<String, Object> findAllPage(@RequestBody Shop entity) {
        int currpage=entity.getOffset();//offset 查询起始位置
        int limit=entity.getLimit();//limit 查询条数
        if(currpage==1){
            entity.setOffset(currpage-1);
        }else if(currpage>1){
            entity.setOffset((currpage-1)*limit);
        }
        Integer countNum=service.countNum(entity);//查到所有数据数
        JSONObject jsonObject=new JSONObject();//组成一个对象
        List<Shop> list =service.queryAll(entity);
        jsonObject.put("limit",limit);//返回当前页显示条数
        jsonObject.put("currpage",currpage);//返回当前页
        jsonObject.put("countNum",countNum);//返回当前页
        jsonObject.put("dataList",list);//返回当前数组
        return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody Shop entity) {
        service.update(entity);
        if(entity.getShopId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody Shop entity) {
        service.insert(entity);
        if(entity.getShopId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody Shop entity) {
        
        if(entity.getShopId() != null){
        service.deleteById(entity.getShopId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}