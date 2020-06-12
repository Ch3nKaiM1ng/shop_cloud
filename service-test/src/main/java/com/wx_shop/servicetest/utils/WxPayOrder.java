package com.wx_shop.servicetest.utils;


import java.text.SimpleDateFormat;
import java.util.SortedMap;
import java.util.TreeMap;

public class WxPayOrder {
    public static String makePayOrder(String xml) {
        String result = "";
        result = HttpUrlConnection.httpXML(
                "https://api.mch.weixin.qq.com/pay/unifiedorder", xml);

        return result;
    }
    //生成xml格式的订单
    public static String haveTestPayOrderXml(String price,String appid,String openid,String mch_id,String nonce_str,String key,String orderId){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String xml="";
        SortedMap<String,String> map=new TreeMap<String, String>();
        map.put("appid", appid);
        map.put("mch_id",mch_id);
        map.put("device_info", "11111");
        map.put("nonce_str", nonce_str);
        map.put("sign_type", "MD5");
        map.put("detail", "250ml");//商品详情
        map.put("out_trade_no", orderId);//订单号
        map.put("total_fee",price);//把页面传过来的价格price放到订单map里，其他参数同理可传进来
        map.put("openid", openid);//这里的openid是个写死的例子，大家可以根据自己获取到的openid放在这里
        map.put("spbill_create_ip", "47.107.47.13");
        map.put("notify_url", "https://m.szmlkq.com:8099/wx_shop/mainOrder/WxGetPayResult");//这里是微信支付结果通知的接口，用来接收订单支付情况
        map.put("trade_type", "JSAPI");
//        map.put("product_id", "12222223333111332");
        map.put("body", "shangpingoumai");//商品详情
        String sign=signUtils.createSign(map,key);
        sign=sign.toUpperCase();
        map.put("sign", sign);

        xml= xmlUtils.getRequestXml(map);
        return xml;
    }
    public static String secondSignCreate(String appId,String timeStamp,String nonceStr,String pck,String key){
        String sign="";
//        SortedMap<String,String> map=new TreeMap<String, String>();
//        map.put("appId",appId);
//        map.put("timeStamp",timeStamp);
//        map.put("nonceStr",nonceStr);
//        map.put("package",pck);
//        map.put("signType","MD5");
        System.out.println("appId="+appId);
        System.out.println("timeStamp="+timeStamp);
        System.out.println("nonceStr="+nonceStr);
        System.out.println("pck="+pck);
        System.out.println("key="+key);
        sign=signUtils.getSecondSign(appId,timeStamp,nonceStr,pck,key);
        return sign;
    }
}
