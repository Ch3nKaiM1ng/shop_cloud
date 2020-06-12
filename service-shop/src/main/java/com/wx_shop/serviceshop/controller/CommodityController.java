package com.wx_shop.serviceshop.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.CommodityType;
import com.wx_shop.serviceshop.entity.SubOrder;
import com.wx_shop.serviceshop.entity.commodityScore;
import com.wx_shop.serviceshop.service.CommodityService;
import com.wx_shop.serviceshop.service.CommodityTypeService;
import com.wx_shop.serviceshop.service.MainOrderService;
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
 * (Commodity)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:13:25
 */
@RestController
@RequestMapping("commodity")
public class CommodityController {
    /**
     * 服务对象
     */
    @Resource
    private CommodityService service;

    @Resource
    private CommodityTypeService commodityTypeService;

    @Resource
    private SubOrderService subOrderService;

    @Resource
    private MainOrderService mainOrderService;
    
    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        Commodity dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody Commodity entity) {
     Commodity dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }


    @RequestMapping("findByWx")
    public Map<String, Object> findByWx(@RequestBody Commodity entity) {
        if(entity.getCommodityId()==null||entity.getCommodityId()==0){
            return re.ERRORMSG("缺少商品ID，获取数据失败");
        }
        int commodityId=entity.getCommodityId();
        /*获取评论分数列表*/
        commodityScore commodityScore=new commodityScore ();
        commodityScore.setCommodityId(commodityId);
        commodityScore scoreData=service.findAvgScore(commodityScore);
        int sales=service.querySalesById(commodityId);
        JSONObject scoreJson =new JSONObject();
        if(scoreData.getAvgCommodityScore()==null){
            scoreJson.put("avgTestScore",5);
            scoreJson.put("avgWeightScore",5);
            scoreJson.put("avgCommodityScore",5);
            scoreJson.put("sales",sales);
        }else{
            scoreJson.put("avgTestScore",Double.parseDouble(scoreData.getAvgTestScore()));
            scoreJson.put("avgWeightScore",Double.parseDouble(scoreData.getAvgWeightScore()));
            scoreJson.put("avgCommodityScore",Double.parseDouble(scoreData.getAvgCommodityScore()));
            scoreJson.put("sales",sales);
        }
        scoreJson.put("commodityId",commodityId);

        /*获取评论分数列表*/
        Commodity dataObj=service.queryObj(entity);//商品详情

        JSONObject json=new JSONObject();
        json.put("commodityData",dataObj);
        json.put("scoreData",scoreJson);
        return re.SUCCESSOBJ(json);
    }

    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody Commodity entity) {
    int currpage=entity.getOffset();//offset 查询起始位置
    int limit=entity.getLimit();//limit 查询条数
    if(currpage==1){
        entity.setOffset(currpage-1);
    }else if(currpage>1){
        entity.setOffset((currpage-1)*limit);
    }
    Integer countNum=service.countNum(entity);//查到所有数据数
    JSONObject jsonObject=new JSONObject();//组成一个对象
    List<Commodity> list =service.queryAll(entity);
    jsonObject.put("limit",limit);//返回当前页显示条数
    jsonObject.put("currpage",currpage);//返回当前页
    jsonObject.put("countNum",countNum);//返回当前页
    jsonObject.put("dataList",list);//返回当前数组
    return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("findAllByWx")
    public Map<String, Object> findAllByWx(@RequestBody CommodityType entity) {
        JSONObject jsonObject=new JSONObject();
        List<CommodityType> commodityTypeList=commodityTypeService.queryAllByWx(entity);//获取商家商品分类列表
        JSONArray dataList=new JSONArray();
        dataList=commodityTypeListJsonArray(commodityTypeList,dataList,1);//商品列表进行封装
        /*******获取广告商品信息*******/
        CommodityType adminEntity=new CommodityType();
        adminEntity.setShopId(0);
        List<CommodityType> adminCommodityList=commodityTypeService.queryAllByWx(adminEntity);//获取商家商品分类列表
        dataList=commodityTypeListJsonArray(adminCommodityList,dataList,0);//商品列表进行封装
        /*******获取广告商品信息*******/
        jsonObject.put("data",dataList);
        jsonObject.put("code",0);
        return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody Commodity entity) {
        service.update(entity);
        if(entity.getCommodityId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody Commodity entity) {
        service.insert(entity);
        if(entity.getCommodityId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody Commodity entity) {
        
        if(entity.getCommodityId() != null){
        service.deleteById(entity.getCommodityId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }

    private static JSONArray commodityTypeListJsonArray(List<CommodityType> commodityTypeList,JSONArray dataList,int isShop){
        for(int i=0;i<commodityTypeList.size();i++){
            JSONObject typeData=new JSONObject();
            JSONArray commodityList=new JSONArray();
            typeData.put("id",commodityTypeList.get(i).getTypeId());
            typeData.put("index",i+1);
            typeData.put("name",commodityTypeList.get(i).getTypeName());
            if(isShop==1){
                typeData.put("type",0);
            }
            if(isShop==0){
                typeData.put("index",100);
                typeData.put("type",1);
            }
            List<Commodity> list=commodityTypeList.get(i).getCommodityList();
            for(int x=0;x<list.size();x++){
                JSONObject commodityData=new JSONObject();
                commodityData.put("id",list.get(x).getCommodityId());
                commodityData.put("name",list.get(x).getCommodityName());
                commodityData.put("delPrice",list.get(x).getCommodityPrice());
                commodityData.put("price",list.get(x).getCommodityTruePrice());
                commodityData.put("img",list.get(x).getCommodityImg());
                commodityData.put("label",list.get(x).getCommodityLabel());
                commodityData.put("sales",list.get(x).getSales());
                commodityData.put("spec","");
                commodityData.put("total",0);
                commodityData.put("num", 0);
                commodityData.put("menu",null);
                commodityList.add(commodityData);
            }
            typeData.put("menu",commodityList);
            dataList.add(typeData);
        }
        return dataList;
    }
}