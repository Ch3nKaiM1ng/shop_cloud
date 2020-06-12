package com.wx_shop.servicetest.utils;

import com.wx_shop.servicetest.entity.TemplateData;
import com.wx_shop.servicetest.entity.WechatTemplate;
import net.sf.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class wxMsgUtils {
    //登特
//    private String template_id ="geruHVXxHRPrkweHzsluHDTOOaAIs8fZU9LUag_a--Q";//模板Id
//    private String templateHead ="您好，马上就要到您就诊了，请您做好准备！";//消息模板头部
//    private String treatHead ="您好，已经到您就诊了，请您马上联系前台人员安排诊察!";//消息模板头部
//    private String treatMent="口腔侦查";
//    private String orderStatus="排队中";
//    private String arriveStatus="已经轮到您就诊了！";
//    private String toRemark ="请您及时与前台联系，以免过号";//消息模板底部
    //美莱
    private String template_id ="rd4-KN08VRHCyV81J-NTq8z8yIJ9VQz7bl4UVEC2ONo";//模板Id
    private String templateHead ="您好，马上就要到您就诊了，请您做好准备！";//消息模板头部
    private String treatHead ="您好，已经到您就诊了，请您马上联系前台人员安排诊察!";//消息模板头部

    private String orderStatus="排队中";
    private String arriveStatus="已经轮到您就诊了！";
    private String toRemark ="请您及时与前台联系，以免过号";//消息模板底部


    public String sendTemplateMessages(String openId,String uOrderNum,String frontOfU,String access_token,Date createTime) {
        try {
            //发送模板消息（POST请求）
            URL msurl = new URL("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + access_token);
            HttpURLConnection msconn = (HttpURLConnection) msurl.openConnection();
            // 在连接之前设置属性
            // Content-Type实体头用于向接收方指示实体的介质类型，指定HEAD方法送到接收方的实体介质类型，或GET方法发送的请求介质类型
            msconn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            // 设置打开与此URLConnection引用的资源的通信链接时使用的指定超时值（以毫秒为单位）
            msconn.setConnectTimeout(30000);
            // 将读取超时设置为指定的超时时间，以毫秒为单位。
            // conn.setReadTimeout(60000);
            // 发送POST请求必须设置如下两行
            msconn.setDoOutput(true);
            msconn.setDoInput(true);
            msconn.setRequestMethod("POST");
            // Post 请求不能使用缓存
            msconn.setUseCaches(false);
            // 建立连接
            msconn.connect();
            OutputStream outputStream = msconn.getOutputStream();
            //前台如果传来消息模板则直接使用，如果未传则自己创建消息模板
            //注意编码格式，防止中文乱码
            //前台组装好模板消息，则直接使用  eg：body
            //outputStream.write(body.getBytes("UTF-8"));

            //前台没有传模板消息，则后台自己组装 eg：wechatTemplateStr
            WechatTemplate wechatTemplate = new WechatTemplate();
            wechatTemplate.setTemplate_id(template_id);
            wechatTemplate.setTouser(openId);
            Map<String, TemplateData> mapdata = new HashMap<>();
            // 封装模板数据
            getTemplateMap(uOrderNum,frontOfU,mapdata,createTime);//封装模板信息MapData
            wechatTemplate.setData(mapdata);
            // 封装模板数据
            //通过JSONArray将模板类转换为字符串
            JSONArray wechatTemplatearray =  JSONArray.fromObject(wechatTemplate);
            String wechatTemplateStr = wechatTemplatearray.toString().replace("[", " ");
            wechatTemplateStr = wechatTemplateStr.toString().replace("]", " ");

            outputStream.write(wechatTemplateStr.getBytes("UTF-8"));
            outputStream.close();

            // 获取响应
            BufferedReader msreader = new BufferedReader(new InputStreamReader(msconn.getInputStream()));
            String msline;
            String msresult = "";
            while ((msline = msreader.readLine()) != null) {
                msresult += msline;
            }
            msreader.close();

            msconn.disconnect();
            return msresult;

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
        return "send false";
    }
    //登特
//    public Map<String, TemplateData> getTemplateMap(String uOrderNum, String frontOfU, Map<String, TemplateData> mapdata){
//        TemplateData first = new TemplateData();
//        first.setValue(templateHead);
//        if(frontOfU.equals("0")){
//            first.setValue(treatHead);
//        }
//        first.setColor("#173177");
//        mapdata.put("first", first);
//
//        TemplateData keyword1 = new TemplateData();//检查项目
//        keyword1.setValue(treatMent);
//        keyword1.setColor("#173177");
//        mapdata.put("keyword1", keyword1);
//
//        TemplateData keyword2 = new TemplateData();
//        keyword2.setValue(uOrderNum);
//        keyword2.setColor("#173177");
//        mapdata.put("keyword2", keyword2);
//
//        TemplateData keyword3 = new TemplateData();
//        keyword3.setValue(frontOfU);
//        keyword3.setColor("#173177");
//        mapdata.put("keyword3", keyword3);
//
//
//        TemplateData keyword4 = new TemplateData();
//        keyword4.setValue(orderStatus);
//        if(frontOfU.equals("0")){
//            keyword4.setValue(arriveStatus);
//        }
//        keyword4.setColor("#173177");
//        mapdata.put("keyword4", keyword4);
//
//        TemplateData remark = new TemplateData();
//        remark.setValue(toRemark);
//        remark.setColor("#173177");
//        mapdata.put("remark", remark);
//        return mapdata;
//    }
    //美莱
    public Map<String, TemplateData> getTemplateMap(String uOrderNum, String frontOfU, Map<String, TemplateData> mapdata,Date createTime){
        TemplateData first = new TemplateData();

        //获取现在时间
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String time= sdf.format(createTime);
        System.out.println("date="+createTime.getTime());
        Date now=new Date();
        System.out.println("now="+now.getTime());
        String between=""+((now.getTime()-createTime.getTime())/60000)+"  分钟";
        System.out.println("between="+between);
        first.setValue(templateHead);
        if(frontOfU.equals("0")){
            first.setValue(treatHead);
        }
        first.setColor("#173177");
        mapdata.put("first", first);

        TemplateData keyword1 = new TemplateData();//检查项目
        keyword1.setValue(uOrderNum);
        keyword1.setColor("#173177");
        mapdata.put("keyword1", keyword1);

        TemplateData keyword2 = new TemplateData();
        keyword2.setValue(time);
        keyword2.setColor("#173177");
        mapdata.put("keyword2", keyword2);

        TemplateData keyword3 = new TemplateData();
        keyword3.setValue(between);
        keyword3.setColor("#173177");
        mapdata.put("keyword3", keyword3);


        TemplateData keyword4 = new TemplateData();
        keyword4.setValue(frontOfU);
        if(frontOfU.equals("0")){
            keyword4.setValue(arriveStatus);
        }
        keyword4.setColor("#173177");
        mapdata.put("keyword4", keyword4);

        TemplateData remark = new TemplateData();
        remark.setValue(toRemark);
        remark.setColor("#173177");
        mapdata.put("remark", remark);
        return mapdata;
    }
}
