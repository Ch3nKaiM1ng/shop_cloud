package com.wx_shop.servicetest.utils;

import com.wx_shop.servicetest.config.WxMappingJackson2HttpMessageConverter;
import com.wx_shop.servicetest.entity.WxAccesstoken;
import com.wx_shop.servicetest.entity.WxAppdata;
import com.wx_shop.servicetest.entity.WxUser;
import com.wx_shop.servicetest.service.WxAccesstokenService;
import com.wx_shop.servicetest.service.WxAppdataService;
import com.wx_shop.servicetest.service.WxUserService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class AccessTokenUtils {
    @Autowired
    private WxAppdataService wxAppdataService;

    @Autowired
    private WxAccesstokenService wxAccesstokenService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private RestTemplate restTemplate;

    private ReturnDiscern re =new ReturnDiscern();

    private static Logger log= LoggerFactory.getLogger(AccessTokenUtils.class);

    private static AccessTokenUtils accessTokenUtils;

    @PostConstruct
    public void init() {
        accessTokenUtils = this;
    }

    public  String getAccessToken(int id){
        String result="";
        WxAppdata appdata=new WxAppdata();
        appdata.setId(id);
        appdata=accessTokenUtils.wxAppdataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        if(appdata==null){
            result="error";
            return result;
        }
        String appid=appdata.getAppid();//appid
        String appsecret=appdata.getAppsecret();//appsecret
        int wxid=appdata.getId();
        //查找数据库中该商家的accesstoken是否过期
        WxAccesstoken checkParam=new WxAccesstoken();//创建一个实体类
        checkParam.setWxid(wxid);
        WxAccesstoken accesstokenParam=accessTokenUtils.wxAccesstokenService.queryLastOneByShopId(checkParam);
        if(accesstokenParam!=null){
            Date ctime=accesstokenParam.getCtime();
            Boolean isExpire=checkExpireTime(ctime);//查看是否过期
            if(isExpire){
                JSONObject rs=getToken(appid,appsecret);
                checkParam.setAccessToken(rs.getString("access_token"));
                accessTokenUtils.wxAccesstokenService.insert(checkParam);
                result=rs.getString("access_token");
                return result;
            }else{
                result=accesstokenParam.getAccessToken();
                return result;
            }
        }else{
            JSONObject rs=getToken(appid,appsecret);
            checkParam.setAccessToken(rs.getString("access_token"));
            accessTokenUtils.wxAccesstokenService.insert(checkParam);
            result=rs.getString("access_token");
            return result;
        }
    }
    public JSONObject getCodeAccessToken(int id,String code){
        System.out.println("111111111111");
        String result="";
        WxAppdata appdata=new WxAppdata();
        appdata.setId(id);
        appdata=accessTokenUtils.wxAppdataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        String appid=appdata.getAppid();//appid
        String appsecret=appdata.getAppsecret();//appsecret
        log.info("Jedis get accesstoken  Fail ");
        JSONObject rs=getCodeToken(appid,appsecret,code);
        JSONObject data=rs.getJSONObject("data");
        return data;
    }

    private boolean checkExpireTime(Date ctime){
        Date now=new Date();
        if(now.getTime()-ctime.getTime()>7200000){
            return true;
        }
        return false;
    }

    private JSONObject getToken(String appid,String appsecret){
        /**************封装调用获取session_key******************/
        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
        JSONObject jsonRs=accessTokenUtils.restTemplate.getForObject(url, JSONObject.class);
        return jsonRs;
    }

    private JSONObject getCodeToken(String appid,String appsecret,String code){
        /**************封装调用获取session_key******************/
        restTemplate.getMessageConverters().add(new WxMappingJackson2HttpMessageConverter());
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
        JSONObject jsonRs=accessTokenUtils.restTemplate.getForObject(url, JSONObject.class);
        JSONObject data=new JSONObject();
        data.put("data",jsonRs);
        return data;
    }

    public boolean checkUserData(String accesstoken,String openid){
        WxUser param=new WxUser();
        param.setOpenid(openid);
        WxUser userData=this.wxUserService.queryObj(param);
        if(userData==null){
            JSONObject userJson=getUserInfo(accesstoken,openid);
            if(userJson!=null){
                WxUser saveUser=new WxUser();
                saveUser=jsonToMap(userJson);
                this.wxUserService.insert(saveUser);
            }
            return true;
        }
            return false;

    }


    private JSONObject getUserInfo(String accesstoken,String openid){
        /**************封装调用获取session_key******************/
        String url="https://api.weixin.qq.com/cgi-bin/user/info?access_token="+accesstoken+"&openid="+openid+"&lang=zh_CN";
        JSONObject jsonRs=accessTokenUtils.restTemplate.getForObject(url, JSONObject.class);
        return jsonRs;
    }

    private WxUser jsonToMap(JSONObject jsonObject){
        WxUser userData=new WxUser();
        inMap(jsonObject,userData,"headimgurl");
        inMap(jsonObject,userData,"nickname");
        inMap(jsonObject,userData,"sex");
        inMap(jsonObject,userData,"country");
        inMap(jsonObject,userData,"province");
        inMap(jsonObject,userData,"city");
        inMap(jsonObject,userData,"openid");
        return userData;
    }
    private WxUser inMap(JSONObject jsonObject,WxUser wxUser,String paramName){
        if(paramName.equals("headimgurl")){
            wxUser.setAvatarUrl(jsonObject.getString(paramName));
        }else if(paramName.equals("nickname")){
            wxUser.setNickName(jsonObject.getString(paramName));
        }else if(paramName.equals("sex")){
            wxUser.setGender(jsonObject.getString(paramName));
        }else if(paramName.equals("country")){
            wxUser.setCountry(jsonObject.getString(paramName));
        }else if(paramName.equals("province")){
            wxUser.setProvince(jsonObject.getString(paramName));
        }else if(paramName.equals("city")){
            wxUser.setCity(jsonObject.getString(paramName));
        }else if(paramName.equals("openid")){
            wxUser.setOpenid(jsonObject.getString(paramName));
        }
        return wxUser;
    }

}
