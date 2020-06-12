package com.wx_shop.servicetest.utils;

import org.springframework.util.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

public class signUtils {

    public static String getSign(Map<String,Object> map,String key){
        String stringA="appid="+map.get("appid")+"&body="+map.get("body")+"&device_info="+map.get("device_info")+
                "&mch_id="+map.get("mch_id")+
                "&nonce_str="+map.get("nonce_str");
        String stringSignTemp=stringA+"&key="+key; //注：key为商户平台设置的密钥key
        String md5after=DigestUtils.md5DigestAsHex(stringSignTemp.getBytes());
        String sign= md5after.toUpperCase(); //注：MD5签名方式
        //        sign=getSHA256(sign).toUpperCase();//注：HMAC-SHA256签名方式
        return sign;
    }

    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createSign( SortedMap<String,String> parameters,String key){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String signT=sb.toString();
        String md5after=DigestUtils.md5DigestAsHex(signT.getBytes());
        return md5after;
    }

    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String createPayBackSign( Map<String,String> parameters,String key){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + key);//最后加密时添加商户密钥，由于key值放在最后，所以不用添加到SortMap里面去，单独处理，编码方式采用UTF-8
        String signT=sb.toString();
        String md5after=DigestUtils.md5DigestAsHex(signT.getBytes());
        return md5after;
    }

    //定义签名，微信根据参数字段的ASCII码值进行排序 加密签名,故使用SortMap进行参数排序
    public static String getSecondSign( String appId,String timeStamp,String nonceStr,String pck,String key){
        String stringA="appId="+appId+"&nonceStr="+nonceStr+"&package="+pck+
                "&signType=MD5"+
                "&timeStamp="+timeStamp;
//        String stringA="appId="+appId+"&nonceStr="+nonceStr+"&package="+pck+"&signType=MD5&timeStamp="+timeStamp;
        String stringSignTemp=stringA+"&key="+key; //注：key为商户平台设置的密钥key
        String md5after=DigestUtils.md5DigestAsHex(stringSignTemp.getBytes());
        String sign= md5after.toUpperCase(); //注：MD5签名方式
        //        sign=getSHA256(sign).toUpperCase();//注：HMAC-SHA256签名方式
        return sign;
    }

    public static String getSHA256(String str){
        MessageDigest messageDigest;
     String encodestr = "";
     try {
      messageDigest = MessageDigest.getInstance("SHA-256");
      messageDigest.update(str.getBytes("UTF-8"));
      encodestr = byte2Hex(messageDigest.digest());
     } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
     } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
     }
     return encodestr;
    }
    /**
     * 将byte转为16进制
     * @param bytes
     * @return
     */
            private static String byte2Hex(byte[] bytes){
     StringBuffer stringBuffer = new StringBuffer();
     String temp = null;
     for (int i=0;i<bytes.length;i++){
      temp = Integer.toHexString(bytes[i] & 0xFF);
      if (temp.length()==1){
      //1得到一位的进行补0操作
      stringBuffer.append("0");
      }
      stringBuffer.append(temp);
     }
     return stringBuffer.toString();
    }
    public static void main(String[] args){
        String appId="wxb85ccf53e185bee5";
        String nonceStr="vcxh5t2jzAUC2rpT";
        String pck="prepay_id=wx30212646045983f3376ab7751462405700";
        String paykey="payKeySetrandomByDTS201912281105";
        String timeStamp="1577712365076";
        String A="appId=wxb85ccf53e185bee5&nonceStr=AZkHGGznMKsZy9ZR&package=prepay_id=wx30210957097105009e66f1e21840442700&signType=MD5&timeStamp=1577711356118&key=payKeySetrandomByDTS201912281105";
        String B=getSecondSign("wxb85ccf53e185bee5","1577712365076","vcxh5t2jzAUC2rpT","prepay_id=wx30212646045983f3376ab7751462405700","payKeySetrandomByDTS201912281105");
        String C=WxPayOrder.secondSignCreate(appId,timeStamp,nonceStr,pck,paykey);

        String md5after=DigestUtils.md5DigestAsHex(A.getBytes());
        String sign= md5after.toUpperCase(); //注：MD5签名方式
        System.out.println(sign);
        System.out.println(B);
        System.out.println(C);
    }
}
