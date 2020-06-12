package com.wx_shop.serviceshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.wx_shop.serviceshop.entity.Commodity;
import com.wx_shop.serviceshop.entity.MainOrder;
import com.wx_shop.serviceshop.entity.SubOrder;
import com.wx_shop.serviceshop.entity.WxAppdata;
import com.wx_shop.serviceshop.service.MainOrderService;
import com.wx_shop.serviceshop.service.SubOrderService;
import com.wx_shop.serviceshop.service.WxAppdataService;
import com.wx_shop.serviceshop.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.JDOMException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * (MainOrder)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:02
 */
@RestController
@RequestMapping("mainOrder")
public class MainOrderController {
    /**
     * 服务对象
     */
    @Resource
    private MainOrderService service;

    @Resource
    private SubOrderService subOrderService;

    @Resource
    private WxAppdataService appDataService;

    private ReturnDiscern re =new ReturnDiscern();

    public static Log log = LogFactory.getLog(MainOrderController.class);

    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        MainOrder dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody MainOrder entity) {
     MainOrder dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody MainOrder entity) {
    List<MainOrder> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("findAllByLimit")
    public Map<String, Object> findAllByLimit(@RequestBody MainOrder entity) {
        int currpage=entity.getOffset();//offset 查询起始位置
        int limit=entity.getLimit();//limit 查询条数
        if(currpage==1){
            entity.setOffset(currpage-1);
        }else if(currpage>1){
            entity.setOffset((currpage-1)*limit);
        }
        Integer countNum=service.countNum(entity);//查到所有数据数
        JSONObject jsonObject=new JSONObject();//组成一个对象
        List<MainOrder> list =service.queryAllByLimit(entity);
        jsonObject.put("limit",limit);//返回当前页显示条数
        jsonObject.put("currpage",currpage);//返回当前页
        jsonObject.put("countNum",countNum);//返回当前页
        jsonObject.put("dataList",list);//返回当前数组
        return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody MainOrder entity) {
        service.update(entity);
        if(entity.getMainOrderId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }


    @RequestMapping("WxGetPayResult")
    public String notify(HttpServletRequest request) {
        log.info("================================================开始处理微信小程序发送的异步通知");

        //1 获取微信支付异步回调结果
        String xmlResult = PayCommonUtil.getPostStr(request);

        Map<String, String> resultMap = null;
        try {
            //将结果转成map
            resultMap = PayCommonUtil.xmlToMap(xmlResult);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        //订单号
        String orderCode = resultMap.get("out_trade_no");
        String wxPayOrder = resultMap.get("transaction_id");
        log.info("订单号：------------------"+orderCode+"结束----------");
        log.info("传值resultMap================================================"+resultMap);

        //通过订单号查询到shopId，通过shopId获取payKey
        MainOrder entity= new MainOrder();
        entity.setOrderCode(orderCode);
        MainOrder orderData=service.queryObj(entity);
        int mainOrderId=orderData.getMainOrderId();
        log.info("mainOrderId================================================"+mainOrderId);
        WxAppdata wxAppdata=new WxAppdata();
        wxAppdata.setShopId(orderData.getShopId());
        wxAppdata=appDataService.queryObj(wxAppdata);
        String paykey=wxAppdata.getPaykey();
        log.info("paykey================================================"+paykey);
        String result_code = resultMap.get("result_code");
        //回调返回的加密签名 保存下来 下面会进行对比
        String sign = resultMap.get("sign");



        Map<String, String> validParams = PayCommonUtil.paraFilter(resultMap);  //回调验签时需要去除sign和空值参数
        String validStr = PayCommonUtil.createLinkString(validParams);//把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String sign1 = PayCommonUtil.sign(validStr,paykey, "utf-8").toUpperCase();//拼装生成服务器端验证的签名

        log.info("二次签名sign1================================================"+sign1);


        String resultCode;
        String resultMsg;
        //对比微信回调的加密与重新加密是否一致  一致即为通过 不一致说明呗改动过 加密不通过
        log.info("==============================================开始对比加密++++++++++++++++++++++++++++++++++++++");
        if (sign.equals(sign1)) { //验签通过
            log.info("==============================================验签通过++++++++++++++++++++++++++++++++++++++");
            MainOrder updateOrder=new MainOrder();
            updateOrder.setMainOrderId(mainOrderId);
            updateOrder.setPayType("1");
            updateOrder.setStatus("1");
            updateOrder.setWxPayOrder(wxPayOrder);
            service.update(updateOrder);
            resultCode = "SUCCESS";
            resultMsg = "成功";


        } else {
            resultCode = "FAIL";
            resultMsg = "验签未通过";
        }

        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("return_code", resultCode);
        returnMap.put("return_msg", resultMsg);
        try {
            String s = PayCommonUtil.mapToXml(returnMap);
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody MainOrder entity) {
        service.insert(entity);
        if(entity.getMainOrderId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("addCommoidtyObj")
    public Map<String, Object> addCommoidtyObj(@RequestBody MainOrder entity) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String out_trade_no="99"+sdf.format(new Date()).trim();
        entity.setOrderCode(out_trade_no);
         MainOrder data= service.insert(entity);
         //如果订单数据不为空
         if(data!=null){
             int mainOrderId=data.getMainOrderId();
             for(int i=0;i<entity.getSubOrder().size();i++){
                 List<SubOrder> subOrderList=entity.getSubOrder();
                 SubOrder subOrder=subOrderList.get(i);
                 subOrder.setMainOrderId(mainOrderId);
                 subOrderService.insert(subOrder);
             }
         }
        if(entity.getMainOrderId() != null){
            return re.SUCCESSOBJ(entity);
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("startPayment")
    public Map<String, Object> startPayment(@RequestBody MainOrder entity) {
        /**************获取商家对应Appid与Appsecret*****
         * *************/
        int shopId=entity.getShopId();
        String orderCode=entity.getOrderCode();
        WxAppdata appdata=new WxAppdata();
        appdata.setShopId(shopId);
        appdata=appDataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        if(appdata==null){
            return re.ERRORMSG("获取不到商户信息");
        }
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");//double转String去0
        Double totalPrice=(entity.getTotalPrice()*100);//单位为分
        String finalPrice=decimalFormat.format(totalPrice);//价格

        String appid=appdata.getAppid();//appid
        String paykey=appdata.getPaykey();//支付秘钥
        String mch_id=appdata.getMchId();//商家号
        String openId=entity.getOpenId();//获取用户OPENID

        String nonce_str=re.getRandomString(32);


        //操作获取拉起支付的apisign
        String xml=WxPayOrder.haveTestPayOrderXml(finalPrice, appid,openId,mch_id, nonce_str, paykey,orderCode);
        System.out.println("xml="+xml);
        String res=HttpUrlConnection.httpXML("https://api.mch.weixin.qq.com/pay/unifiedorder",xml);
        Map<String, Object> resultData= xmlUtils.toMap(res);//第一次拉起支付返回结果
        String pck="prepay_id="+resultData.get("prepay_id").toString();
        /*******二次签名的获取***************/
        Date now=new Date();
        String timeStamp=(now.getTime())+"";
        String nonceStr=resultData.get("nonce_str").toString();
        String paySign=WxPayOrder.secondSignCreate(appid,timeStamp,nonceStr,pck,paykey);//二次签名
        /*******二次签名的获取***************/
        resultData.put("package",pck);
        resultData.put("paySign",paySign);
        resultData.put("timeStamp",timeStamp);
        return re.SUCCESSOBJ(resultData);
    }

    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody MainOrder entity) {
        
        if(entity.getMainOrderId() != null){
        service.deleteById(entity.getMainOrderId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}