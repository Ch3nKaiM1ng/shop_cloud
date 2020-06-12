package com.wx_shop.serviceshop.controller;



import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.wx_shop.serviceshop.entity.WxAccesstoken;
import com.wx_shop.serviceshop.entity.WxAppdata;
import com.wx_shop.serviceshop.service.WxAccesstokenService;
import com.wx_shop.serviceshop.service.WxAppdataService;
import com.wx_shop.serviceshop.utils.*;
import net.sf.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wx_shop.serviceshop.entity.WxUser;
import com.wx_shop.serviceshop.service.WxUserService;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * (WxUser)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:09
 */
@RestController
@RequestMapping("wxUser")
public class WxUserController {
    /**
     * 服务对象
     */
    @Resource
    private WxUserService service;

    @Resource
    private WxAppdataService appDataService;

    @Resource
    private WxAccesstokenService wxAccesstokenService;

    @Autowired
    private RestTemplate restTemplate;

    private ReturnDiscern re =new ReturnDiscern();

    private HttpUtils httpUtils=new HttpUtils();
    @RequestMapping("getAccessToken")
    public Map<String, Object> getAccessToken(@RequestBody JSONObject json) {
        int shopId=json.getInt("shopId");
        WxAppdata appdata=new WxAppdata();
        appdata.setShopId(shopId);
        appdata=appDataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        if(appdata==null){
            return re.ERRORMSG("获取不到商户信息");
        }
        String appid=appdata.getAppid();//appid
        String appsecret=appdata.getAppsecret();//appsecret

        //查找数据库中该商家的accesstoken是否过期
        WxAccesstoken checkParam=new WxAccesstoken();//创建一个实体类
        checkParam.setShopId(shopId);
        WxAccesstoken accesstokenParam=wxAccesstokenService.queryLastOneByShopId(checkParam);
        if(accesstokenParam!=null){
            Date ctime=accesstokenParam.getCtime();
            Boolean isExpire=checkExpireTime(ctime);//查看是否过期
            if(isExpire){
                JSONObject rs=getToken(appid,appsecret);
                checkParam.setAccessToken(rs.getString("access_token"));
                wxAccesstokenService.insert(checkParam);
                return re.SUCCESSOBJ(checkParam);
            }else{
                return re.SUCCESSOBJ(accesstokenParam);
            }
        }else{
            JSONObject rs=getToken(appid,appsecret);
            checkParam.setAccessToken(rs.getString("access_token"));
            wxAccesstokenService.insert(checkParam);
            return re.SUCCESSOBJ(checkParam);
        }
        /**************封装调用获取session_key******************/

//        String appid="wxb85ccf53e185bee5";
//        String appsecret="866b1ae1834c81ef2591bbb4a36d6ad2";
//        String url="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appsecret;
//        JSONObject jsonRs=restTemplate.getForObject(url, JSONObject.class);
//        return jsonRs;
    }

    @RequestMapping("getUserQrcode")
    public Map<String, Object> getUserQrcode(@RequestBody JSONObject json, HttpServletRequest request)  {
        int shopId=json.getInt("shopId");
        String userId=json.getString("userId");
        WxAppdata appdata=new WxAppdata();
        appdata.setShopId(shopId);
        appdata=appDataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        if(appdata==null){
            return re.ERRORMSG("获取不到商户信息");
        }
        String appid=appdata.getAppid();//appid
        String appsecret=appdata.getAppsecret();//appsecret
        String accesstoken="";
        //查找数据库中该商家的accesstoken是否过期
        WxAccesstoken checkParam=new WxAccesstoken();//创建一个实体类
        checkParam.setShopId(shopId);
        WxAccesstoken accesstokenParam=wxAccesstokenService.queryLastOneByShopId(checkParam);
        if(accesstokenParam!=null){
            Date ctime=accesstokenParam.getCtime();
            Boolean isExpire=checkExpireTime(ctime);//查看是否过期
            if(isExpire){
                JSONObject rs=getToken(appid,appsecret);
                checkParam.setAccessToken(rs.getString("access_token"));
                wxAccesstokenService.insert(checkParam);
                accesstoken=rs.getString("access_token");
            }else{
                accesstoken=accesstokenParam.getAccessToken();
            }
        }else{
            JSONObject rs=getToken(appid,appsecret);
            checkParam.setAccessToken(rs.getString("access_token"));
            accesstoken=rs.getString("access_token");
            wxAccesstokenService.insert(checkParam);
        }
        //开始调用接口获取QRcode的Base64
        try{
            Map<String, Object> params = new HashMap<>();
            params.put("scene", userId);  //参数
            params.put("page", "pages/user/user"); //位置
            params.put("width", 280);
            params.put("is_hyaline", true);

            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accesstoken);  // 接口
            httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
            String body = JSON.toJSONString(params);           //必须是json模式的 post
            StringEntity entity;
            entity = new StringEntity(body);
            entity.setContentType("image/png");
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);
            InputStream inputStream = response.getEntity().getContent();
            // 将获取流转为base64格式
            String result = "";
            byte[] data = null;
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc = 0;
            while ((rc = inputStream.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
            data = swapStream.toByteArray();

            result = new String(Base64.getEncoder().encode(data));
//            return re.SUCCESSOBJ(result);


            String readPath=Base64Util.Base64ToImage(result);
            return re.SUCCESSOBJ(readPath);
//            MultipartFile file=BASE64DecodedMultipartFile.base64ToMultipart(result);
//            if (file==null){
//                return re.ERRORMSG("文件上传失败！");
//            }
//            else {
//                String url = UpdateImgNameUtils.UpdateImgName(file);
//                return re.SUCCESSOBJ(url);
//            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return re.ERROR();
    }

    @RequestMapping("getPhoneNumber")
    public Map<String, Object> getPhoneNumber(@RequestBody JSONObject json) {
        /**************获取商家对应Appid与Appsecret******************/
        int userId=json.getInt("userId");
        int shopId=json.getInt("shopId");
        WxAppdata appdata=new WxAppdata();
        appdata.setShopId(shopId);
        appdata=appDataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        if(appdata==null){
            return re.ERRORMSG("获取不到商户信息");
        }

        /**************封装调用获取session_key******************/
        String encryptedData=json.get("encryptedData").toString();//encryptedData
        String iv=json.get("iv").toString();
        String session_key=json.get("session_key").toString();

        /**************根据返回的session_key与其他字段解密获取用户手机号码******************/
        JSONObject jsonObject=new JSONObject();
        try {
            String result = AESCBCUtil.decryptwx(encryptedData, session_key, iv, "UTF-8");//解密获取用户手机号码
            if (null != result && result.length() > 0) {
                JSONObject mobileInfo = JSONObject.fromObject(result);//将用户手机号码转换成json
                /**************根据手机号码查询是否有用户数据******************/
                WxUser wxuser=new WxUser();
                wxuser.setUserId(userId);
                wxuser.setPhone(mobileInfo.getString("purePhoneNumber"));
                wxuser.setShopId(shopId);
                service.update(wxuser);
                WxUser userData=service.queryObj(wxuser);
                jsonObject.put("userInfo",userData);//将用户数据组成json返回给前端
            }else{
                return re.ERRORMSG("解密失败，获取不到用户手机数据");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }

    @RequestMapping("addUserInfo")
    public Map<String, Object> addUserInfo(@RequestBody JSONObject json) {
        /**************获取商家对应Appid与Appsecret******************/
        int shopId=json.getInt("shopId");
        WxAppdata appdata=new WxAppdata();
        appdata.setShopId(shopId);
        appdata=appDataService.queryObj(appdata);//调用数据库查找相应的商家微信数据
        if(appdata==null){
            return re.ERRORMSG("获取不到商户信息");
        }

        /**************封装调用获取session_key******************/
        String appid=appdata.getAppid();//appid
        String appsecret=appdata.getAppsecret();//appsecret
        Integer IntroducerId=null;
        if(json.get("introducerId")!=null){
            IntroducerId=json.getInt("introducerId");
        }
        String code=json.get("code").toString();//用户登陆验证的code
        String encryptedData=json.get("encryptedData").toString();//encryptedData
        String grant_type = "authorization_code";//授权（必填）
        String iv=json.get("iv").toString();
        String url="https://api.weixin.qq.com/sns/jscode2session";
        String params = "appid=" + appid + "&secret=" + appsecret + "&js_code=" + code + "&grant_type=" + grant_type;
        String res=HttpUtils.sendGet(url,params);//调用接口获取微信用户登陆
        Gson gson = new Gson();
        JSONObject jsonObject = gson.fromJson(res, JSONObject.class);//获取返回的session_key
        if (jsonObject.get("session_key") == null) {
            return re.ERRORMSG("解密失败获取不到sessionkey");
        }
        String session_key=jsonObject.get("session_key").toString();

        /**************根据返回的session_key与其他字段解密获取用户手机号码******************/
        try {
            String result = AESCBCUtil.decryptwx(encryptedData, session_key, iv, "UTF-8");//解密获取用户手机号码
            if (null != result && result.length() > 0) {
                JSONObject userInfo = JSONObject.fromObject(result);//将用户手机号码转换成json
                jsonObject.put("userInfo",userInfo);//将用户数据组成json返回给前端
                /**************根据手机号码查询是否有用户数据******************/
                WxUser wxuser=new WxUser();
                wxuser.setOpenid(userInfo.getString("openId"));
                wxuser.setShopId(shopId);
                WxUser userData=service.queryObj(wxuser);
                //用户数据为空（新用户
                if(userData==null){
                    setEntity(wxuser,userInfo);
                    wxuser.setIntroducerId(IntroducerId);
                    service.insert(wxuser);
                    wxuser.setCtime(new Date());
                    jsonObject.put("userInfo",wxuser);
//                    wxuser.setAvatarUrl(userInfo.getString("avatarUrl"));
//                    wxuser.setCity(userInfo.getString("city"));
//                    wxuser.setCountry(userInfo.getString("country"));
//                    wxuser.setGender(userInfo.get("gender").toString());
//                    wxuser.setNickName(userInfo.getString("nickName"));
//                    wxuser.setProvince(userInfo.getString("province"));
                }
                //用户数据不为空（老用户,返回用户数据至前台
                else if(userData!=null){
                    jsonObject.put("userInfo",userData);
                }
            }else{
                return re.ERRORMSG("解密失败，获取不到用户手机数据");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return re.SUCCESSOBJ(jsonObject);

    }

    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxUser entity) {
     WxUser dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody WxUser entity) {
    List<WxUser> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("findAllByLimit")
    public Map<String, Object> findAllByLimit(@RequestBody WxUser entity) {
        int currpage=entity.getOffset();//offset 查询起始位置
        int limit=entity.getLimit();//limit 查询条数
        if(currpage==1){
            entity.setOffset(currpage-1);
        }else if(currpage>1){
            entity.setOffset((currpage-1)*limit);
        }
        Integer countNum=service.countNum(entity);//查到所有数据数
        JSONObject jsonObject=new JSONObject();//组成一个对象
        List<WxUser> list =service.queryAllByLimit(entity);
        jsonObject.put("limit",limit);//返回当前页显示条数
        jsonObject.put("currpage",currpage);//返回当前页
        jsonObject.put("countNum",countNum);//返回当前页
        jsonObject.put("dataList",list);//返回当前数组
        return re.SUCCESSOBJ(jsonObject);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxUser entity) {
        service.update(entity);
        if(entity.getUserId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxUser entity) {
        service.insert(entity);
        if(entity.getUserId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxUser entity) {
        
        if(entity.getUserId() != null){
        service.deleteById(entity.getUserId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
    public WxUser setEntity(WxUser entity,JSONObject json){
        entity.setAvatarUrl(json.getString("avatarUrl"));
        entity.setCity(json.getString("city"));
        entity.setCountry(json.getString("country"));
        entity.setGender(json.get("gender").toString());
        entity.setNickName(json.getString("nickName"));
        entity.setProvince(json.getString("province"));
        return entity;
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
        JSONObject jsonRs=restTemplate.getForObject(url, JSONObject.class);
        return jsonRs;
    }
}