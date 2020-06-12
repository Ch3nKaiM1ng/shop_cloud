package com.wx_shop.serviceshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.Evaluate;
import com.wx_shop.serviceshop.entity.MainOrder;
import com.wx_shop.serviceshop.service.EvaluateService;
import com.wx_shop.serviceshop.service.MainOrderService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (Evaluate)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:01
 */
@RestController
@RequestMapping("evaluate")
public class EvaluateController {
    /**
     * 服务对象
     */
    @Resource
    private EvaluateService service;

    @Resource
    private MainOrderService mainOrderService;

    private ReturnDiscern re =new ReturnDiscern();

    public static Log log = LogFactory.getLog(EvaluateController.class);

    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        Evaluate dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody Evaluate entity) {
     Evaluate dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    //根据商品Id获取评论列表
    @RequestMapping("findAllByCommodityId")
    public Map<String, Object> findAllByCommodityId(@RequestBody Evaluate entity) {
        int currpage=entity.getOffset();//offset 查询起始位置
        int limit=entity.getLimit();//limit 查询条数
        if(currpage==1){
            entity.setOffset(currpage-1);
        }else if(currpage>1){
            entity.setOffset((currpage-1)*limit);
        }
        int haveImg=entity.getHaveImg();
        //查找所有数据
        entity.setHaveImg(0);
        Integer allCountNum=service.countNum(entity);//查到所有数据数
        //查找有图片的数据数
        entity.setHaveImg(1);
        Integer haveImgCountNum=service.countNum(entity);//查找有图片的数据数
        JSONObject jsonObject=new JSONObject();//组成一个对象
        List<Evaluate> list =service.findAllByCommodityId(entity);
        jsonObject.put("limit",limit);//返回当前页显示条数
        jsonObject.put("currpage",currpage);//返回当前页
        jsonObject.put("allCountNum",allCountNum);//返回当前页
        jsonObject.put("haveImgCountNum",haveImgCountNum);//返回当前页
        jsonObject.put("dataList",list);//返回当前数组
        return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody Evaluate entity) {
    List<Evaluate> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody Evaluate entity) {
        service.update(entity);
        if(entity.getEvaluateId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody Evaluate entity) {
        //根据口味评分和分量评分算出总评分
        if(entity.getTestScore()!=null&&entity.getWeightScore()!=null){
            double test=entity.getTestScore();
            double weight=entity.getWeightScore();
            double sum=(test+weight)/2;
            entity.setScore(sum);//将总评分插入
        }
        service.insert(entity);
        //修改订单评价状态】
        MainOrder mainOrder=new MainOrder();
        mainOrder.setMainOrderId(entity.getMainOrderId());
        mainOrder.setEvaluate("1");
        mainOrderService.update(mainOrder);
        if(entity.getEvaluateId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody Evaluate entity) {
        
        if(entity.getEvaluateId() != null){
        service.deleteById(entity.getEvaluateId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}