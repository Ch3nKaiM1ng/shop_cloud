package com.wx_shop.servicetest.utils;


import java.util.HashMap;
import java.util.Map;

public class MassageReturnUtils {
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
}
