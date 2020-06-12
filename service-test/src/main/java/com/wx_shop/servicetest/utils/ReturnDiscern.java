package com.wx_shop.servicetest.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
public class ReturnDiscern {


    public Map<String,Object> SUCCESS(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","200");
        map.put("msg","提交成功！");
        return map;
    }
    public Map<String,Object> ERROR(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","500");
        map.put("msg","提交失败！");
        return map;
    }
    public Map<String,Object> ERRORMSG(String msg){
        Map<String,Object> map = new HashMap<>();
        map.put("code","500");
        map.put("msg",msg);
        return map;
    }
    public Map<String,Object> SUCCESSOBJ(Object obj){
        Map<String,Object> map = new HashMap<>();
        map.put("code","200");
        map.put("msg","提交成功！");
        map.put("data",obj);
        return map;
    }
    public Map<String,Object> TimeError(int time){
        Map<String,Object> map = new HashMap<>();
        map.put("code","500");
        map.put("msg","每"+time+"分钟只能申请认证一次！");
        return map;
    }
    public Map<String,Object> TimeOut(){
        Map<String,Object> map = new HashMap<>();
        map.put("code","500");
        map.put("msg","验证码已失效，请重新获取！");
        return map;
    }
    public String video(){
        return "video";
    }
    public String img(){
        return "img";
    }
    public Map<String,Object> CodeVerify(String code){
        Map<String,Object> map = new HashMap<>();
        if (code==null){
            map.put("data","-1");
            map.put("content","程序异常！");
            return map;
        }
        if (code.equals("00000")){
            map.put("data","0");
            map.put("content","提交成功！");
        }else if (code.equals("10000")){
            map.put("data","-1");
            map.put("content","参数异常！");
        }else if (code.equals("10001")){
            map.put("data","-1");
            map.put("content","手机号格式不正确！");
        }else if (code.equals("10002")){
            map.put("data","-1");
            map.put("content","模板不存在！");
        }else if (code.equals("10003")){
            map.put("data","-1");
            map.put("content","模板变量不正确！");
        }else if (code.equals("10004")){
            map.put("data","-1");
            map.put("content","变量中含有敏感词！");
        }else if (code.equals("10005")){
            map.put("data","-1");
            map.put("content","变量名称不匹配！");
        }else if (code.equals("10006")){
            map.put("data","-1");
            map.put("content","短信长度过长！");
        }else if (code.equals("10007")){
            map.put("data","-1");
            map.put("content","手机号查询不到归属地！");
        }else if (code.equals("10008")){
            map.put("data","-1");
            map.put("content","产品错误！");
        }else if (code.equals("10010")){
            map.put("data","-1");
            map.put("content","重复调用！");
        }else if (code.equals("10009")){
            map.put("data","-1");
            map.put("content","价格错误！");
        }else if (code.equals("99999")){
            map.put("data","-1");
            map.put("content","系统错误！");
        }
        return map;
    }
    public String getRemortIP(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
    @PostConstruct  //关键四：通过@PostConstruct注解实现注入
    public void init() {
        //tokenUtils.rService =  this.rService;
    }
    //保存用户跟踪数据
    public  void getIpAndMobileMsg(HttpServletRequest request, HttpServletResponse response, JSONObject json)  {
        //TrackUser data=new TrackUser();//返回实体类
        String referrer = request.getHeader("Referer");
        System.out.println(referrer);
        String remoteAddr = request.getRemoteAddr();
//        JSONObject json=new JSONObject();
        if(remoteAddr.equals("0:0:0:0:0:0:0:1")){
            System.out.println("您的ip地址为：127.0.0.1");
            json.put("ipaddr","127.0.0.1");
            //data.setIpaddr("127.0.0.1");
        }else{
            System.out.println("您的ip地址为：" + remoteAddr);
            json.put("ipaddr",remoteAddr);
            /*data.setIpaddr(remoteAddr);*/
        }
        String requestHeader = request.getHeader("User-Agent");
        int index_one = requestHeader.indexOf("(");
        String requestBody = requestHeader.substring(index_one+1);
        String userInfo = requestBody.substring(0, requestBody.indexOf(")"));
        String[] userInfoList = userInfo.split(";");
        int length = userInfoList.length;
        String os = userInfoList[0];
        String mobileInfo = userInfoList[length - 1];
        if(os.contains("Windows")){
            System.out.println("您的操作系统为：windows系统");
            //data.setSystem("电脑"+os);
            json.put("system","电脑"+os);
        }else{
            System.out.println("您的操作系统为：" + os);
            //data.setSystem("手机"+os);
            json.put("system",os);
        }
        int index = mobileInfo.indexOf("/");

        if(index > 0){
            mobileInfo = mobileInfo.substring(0, mobileInfo.indexOf("/") - 5);
            System.out.println("您的手机型号为：" + mobileInfo);
            json.put("mobile",mobileInfo);
            //data.setMobile(mobileInfo);
        }else{
            System.out.println("您的手机型号为：电脑登陆");
            json.put("mobile","电脑登陆");
            //data.setMobile("电脑登陆");
        }



    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

}
