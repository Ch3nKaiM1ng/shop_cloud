package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.*;
import com.wx_shop.servicetest.service.*;
import com.wx_shop.servicetest.utils.AccessTokenUtils;
import com.wx_shop.servicetest.utils.HttpUtils;
import com.wx_shop.servicetest.utils.MessageUtil;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * (WxAppdata)表控制层
 *
 * @author makejava
 * @since 2020-04-14 16:11:15
 */
@RestController
@RequestMapping("SignUtil")
public class SignController {
    /**
     * 服务对象
     */

    @Resource
    private WxAppdataService service;

    @Resource
    private WxOrderService wxOrderService;

    @Resource
    private QrsceneService qrsceneService;

    @Resource
    private WxUserService wxUserService;

    @Resource
    private WxAccesstokenService wxAccesstokenService;

    @Autowired
    private RestTemplate restTemplate;

    private static Logger log= LoggerFactory.getLogger(SignController.class);

    private static SignController signC;
    @Autowired
    private AccessTokenUtils accessTokenUtils;

    private ReturnDiscern re =new ReturnDiscern();

    private static final long serialVersionUID = 1L;
    private static String token = "weixin";

    
//    @RequestMapping("doGet")
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        //微信服务器get传递的参数
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String echostr = request.getParameter("echostr");
//
//        PrintWriter out = response.getWriter();
//        if (this.checkSignature(signature, timestamp, nonce)) {
//            out.print(echostr);
//            out.flush();
//            out.close();
//        }
//        out = null;
//    }

//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//        //微信服务器get传递的参数
//        String signature = request.getParameter("signature");
//        String timestamp = request.getParameter("timestamp");
//        String nonce = request.getParameter("nonce");
//        String echostr = request.getParameter("echostr");
//
//        PrintWriter out = response.getWriter();
//        if (this.checkSignature(signature, timestamp, nonce)) {
//            out.print(echostr);
//        }
//        //返回echostr给微信服务器
//        OutputStream os=response.getOutputStream();
//        os.write(URLEncoder.encode(echostr,"UTF-8").getBytes());
//        os.flush();
//        os.close();
//        out.close();
//    }
    @RequestMapping("doGet")
    public  String processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("SomeBody move My cake");
        String respMessage = null;
        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";

            // xml请求解析
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            log.info("*******requestMap====="+requestMap,requestMap);
            // 发送方帐号（open_id）0
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");

            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);

            // 文本消息
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                log.info("用户发送了文本信息");
                log.info("request:"+requestMap);
                String content=requestMap.get("Content");
                Boolean rs=StringUtils.isNumeric(content);
                if(rs==false){
                    return null;
                }else {
                    String phoneNum=content;
                    respContent=check(phoneNum,fromUserName);
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    log.info("respMessage="+respMessage);
                    return respMessage;
                }
            }
//            // 图片消息
//            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
//                respContent = "您发送的是图片消息！";
//            }
//            // 地理位置消息
//            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
//                respContent = "您发送的是地理位置消息！";
//            }
//            // 链接消息
//            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
//                respContent = "您发送的是链接消息！";
//            }
//            // 音频消息
//            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
//                respContent = "您发送的是音频消息！";
//            }
            // 事件推送
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    respContent = "谢谢您的关注！您好！";
                    String eventKey = requestMap.get("EventKey");
                    if(toUserName.equals("gh_ca003a0254a4")){//美莱的公众号
                        log.info("*******SCAN QR Way=====Mylike，ToUserName===="+toUserName);
                        checkMyLikeQrWay(eventKey,fromUserName);
                    }
                    if(toUserName.equals("gh_9da5291f1465")){//美莱的公众号
                        log.info("*******SCAN QR Way=====DTKQ，ToUserName===="+toUserName);
                        checkDTQrWay(eventKey,fromUserName);
                    }
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    return null; // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 事件告知客户是否收到消息
                else if (eventType.equals(MessageUtil.EVENT_TYPE_TEMPLATESENDJOBFINISH)) {
                    return null; // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应
                    String eventKey = requestMap.get("EventKey");

                    if (eventKey.equals("CORRECTORDER")) {
                        respContent=doOrder(fromUserName,1);
                        checkUserList(fromUserName);
                    }
                    else if (eventKey.equals("REPAIRORDER")) {
                        respContent=doOrder(fromUserName,2);
                        checkUserList(fromUserName);
                    }
                    else if (eventKey.equals("CLEANORDER")) {
                        respContent=doOrder(fromUserName,3);
                        checkUserList(fromUserName);
                    }
                    else if (eventKey.equals("CHECKORDER")) {
                        respContent=checkOrder(fromUserName);
//                        respContent = "测试预约！";
                    } else{
                        respContent = "点击事件跟踪错误！";
                    }
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.textMessageToXml(textMessage);
                    log.info("respMessage="+respMessage);
                    return respMessage;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respMessage;
    }


    @RequestMapping("getAccessToken")
    public Map<String, Object> getAccessToken(@RequestBody JSONObject json) {
        int id=json.getInt("id");
        String accessToken=accessTokenUtils.getAccessToken(id);
        return re.SUCCESSOBJ(accessToken);
    }

    public  String doOrder(String openid,Integer orderType){
        String respContent="";
        WxOrder checkParam=new WxOrder();//创建一个实体类
        checkParam.setOpenid(openid);
        checkParam.setIsuse("0");
        checkParam.setOrdertype(orderType);
        log.info("checkParam="+checkParam);
        signC = this;
        signC.wxOrderService=this.wxOrderService;
        WxOrder WxOrderParam=signC.wxOrderService.queryObj(checkParam);
        Integer front=0;//前面排队的人
        String uOrderNum="";//你的排队号码ordertype=1:JZ,2:RP,3:XY
        String nowOrderNum="";//当前叫号
        if(orderType==1){
            uOrderNum = "JZ";
            nowOrderNum ="JZ";
        }else if(orderType==2){
            uOrderNum = "RP";
            nowOrderNum = "RP";
        }else if(orderType==3){
            uOrderNum = "XY";
            nowOrderNum = "XY";
        }
        if(WxOrderParam==null){//没有号码，开始排队叫号
            WxOrder wxOrder=new WxOrder();
            wxOrder.setOpenid(openid);
            wxOrder.setOrdertype(orderType);
            WxOrder orderParam=signC.wxOrderService.doOrder(wxOrder);//没有排队信息，开始排队的数据
            WxOrder orderData=signC.wxOrderService.queryObj(orderParam);//获取前面还有多少用户在排队
            front=orderData.getFront();//前面排队的人

            uOrderNum=uOrderNum+orderData.getOrderNum();//你的排队号码
            nowOrderNum=nowOrderNum+orderData.getNowOrder();//当前叫号
            if(front!=0){
                respContent="排队成功,当前叫号"+nowOrderNum+"\n"+
                        "您的排队号码是"+uOrderNum+"号\n"+
                        "您前面目前还有"+front+"人\n"+
                        "请您耐心等待，待你就诊时，我们会即刻提醒您！";
            }else{
                respContent="尊敬的客户,轮到您就诊了，请你抓紧时间\n"+
                        "您的排队号码是"+uOrderNum+"号\n";
            }
        }else if(WxOrderParam!=null){
//            respContent="尊敬的客户,您已经在排队了，排队失败。\n"+
//                    "想查看你的排队信息请点击下面菜单中的排队查询\n";
            front=WxOrderParam.getFront();//前面排队的人

            uOrderNum=uOrderNum+WxOrderParam.getOrderNum();//你的排队号码
            nowOrderNum=nowOrderNum+WxOrderParam.getNowOrder();//当前叫号
            if(front!=0){
                respContent="尊敬的客户,当前叫号"+nowOrderNum+"\n"+
                        "您的排队号码是"+uOrderNum+"号\n"+
                        "您前面目前还有"+front+"人\n"+
                        "请您耐心等待，待你就诊时，我们会即刻提醒您！";
            }else{
                respContent="尊敬的客户,轮到您就诊了，请你抓紧时间\n"+
                        "您的排队号码是"+uOrderNum+"号\n";
            }
        }
        return respContent;
    }


    public void checkUserList(String openid){
        String accessToken=accessTokenUtils.getAccessToken(1);
        Boolean rs=accessTokenUtils.checkUserData(accessToken,openid);
        if(rs==true){
            log.info("save WxUserInfo Success");
        }else{
            log.info("save WxUserInfo Defeat Cause already exist");
        }
    }

    public  String checkOrder(String openid){
        String respContent="";
        WxOrder checkParam=new WxOrder();//创建一个实体类
        checkParam.setOpenid(openid);
        checkParam.setIsuse("0");
        log.info("checkParam="+checkParam);
        signC = this;
        signC.wxOrderService=this.wxOrderService;
        WxOrder WxOrderParam=signC.wxOrderService.queryObj(checkParam);
        Integer ordertype=WxOrderParam.getOrdertype();
        Integer front=0;//前面排队的人
        String uOrderNum="";//你的排队号码ordertype=1:JZ,2:RP,3:XY
        String nowOrderNum="";//当前叫号
        String DoctorNum="";
        if(ordertype==1){
            uOrderNum = "JZ";
            nowOrderNum ="JZ";
        }else if(ordertype==2){
            uOrderNum = "RP";
            nowOrderNum = "RP";
        }else if(ordertype==3){
            uOrderNum = "XY";
            nowOrderNum = "XY";
        }

        if(WxOrderParam==null){
            respContent="尊敬的客户\n"+
                    "您还没有任何排队中的项目\n"+
                    "请您到下面菜单点击预约拿取您的排队号码\n"+
                    "即刻预约，即刻就诊噢！";
        }else if(WxOrderParam!=null){
            front=WxOrderParam.getFront();//前面排队的人
            uOrderNum =uOrderNum+ WxOrderParam.getOrderNum();//你的排队号码
            nowOrderNum =nowOrderNum+ WxOrderParam.getNowOrder();//当前叫号
            if(front!=0){
                respContent="尊敬的客户,当前叫号"+nowOrderNum+"\n"+
                        "您的排队号码是"+uOrderNum+"号\n"+
                        "您前面目前还有"+front+"人\n"+
                        "请您耐心等待，待你就诊时，我们会即刻提醒您！";
            }else{
                respContent="尊敬的客户,轮到您就诊了，请你抓紧时间\n"+
                        "您的排队号码是"+uOrderNum+"号\n";
            }
        }
        return respContent;
    }

    public  String check(String phoneNum,String openid){
        String res="";
        //调用接口
        signC = this;
        signC.wxOrderService=this.wxOrderService;
        //调用接口
        String time[]=getSETime();
        String stime=time[0];
        String etime=time[1];
        log.info("time="+time);
        WxOrder param=new WxOrder();
        //查找手机号码是否有预约过
        param.setPhone(phoneNum);
        param.setIsuse("0");
        param.setStime(stime);
        param.setEtime(etime);
        param.setNotState(2);//筛选掉以取消的预约状态
        List<WxOrder> dataList=signC.wxOrderService.queryAll(param);
        if(dataList.size()>1){
            res="系统错误，用户今天有多个预约！";
            return res;
        }
        if(dataList.size()==0){
            res="经查询，您当前没有预约排队信息";
            return res;
        }
        if(dataList.size()!=0){
            log.info("用户今天有预约");

            WxOrder data=dataList.get(0);
            //根据查询的手机号码绑定用户
            data.setOpenid(openid);
            signC.wxOrderService.update(data);
            //根据查询的手机号码绑定用户

            int state=data.getState();//0正常 1用户到场 2用户取消
            if(state==0){
                res="您好,经查询您还没有前往前台确认到场\n"+
                        "请先前往前台与客服确认到场并且取号";
                return res;
            }
            int doctorId=data.getDoctorId();
//            int ordertype=data.getOrdertype();//1矫正2修复3洗牙4其他
            int orderNum=data.getOrderNum();
            String phone=data.getPhone();
            if(state==1){
                //查询用户该分类下前面还有多少人在现场排队
                WxOrder listParam=new WxOrder();
                listParam.setPhone(phone);
                listParam.setOrderNum(orderNum);
                listParam.setDoctorId(doctorId);
//                listParam.setOrdertype(ordertype);
                listParam.setState(1);
                WxOrder orderData=signC.wxOrderService.queryObj(listParam);
                int orderJump=orderData.getIsjump();
                if(orderJump==1){
                    res="尊敬的客户,已为您安排最优先通道\n"+
                            "您前面还有1人\n"+
                            "请您耐心等待，待你就诊时，我们会即刻提醒您！";
                    return res;
                }
                //
                Integer front=0;//前面排队的人
                String uOrderNum="";//你的排队号码ordertype=1:JZ,2:RP,3:XY
                String nowOrderNum="";//当前叫号
                Doctor doctorData=orderData.getDoctorData();
                String doctorCode=doctorData.getDoctorCode();
//                if(ordertype==1){
//                    uOrderNum = "JZ";
//                    nowOrderNum ="JZ";
//                }else if(ordertype==2){
//                    uOrderNum = "RP";
//                    nowOrderNum = "RP";
//                }else if(ordertype==3){
//                    uOrderNum = "XY";
//                    nowOrderNum = "XY";
//                }
                front=orderData.getFront();//前面排队的人
                uOrderNum =uOrderNum+doctorCode+ orderData.getOrderNum();//你的排队号码
                nowOrderNum =nowOrderNum+doctorCode+ orderData.getNowOrder();//当前叫号
                if(front!=0){
                    res="尊敬的客户,当前叫号"+nowOrderNum+"\n"+
                            "您的排队号码是"+uOrderNum+"号\n"+
                            "您前面目前还有"+front+"人\n"+
                            "请您耐心等待，待你就诊时，我们会即刻提醒您！";
                }else{
                    res="尊敬的客户,轮到您就诊了，请你抓紧时间\n"+
                            "您的排队号码是"+uOrderNum+"号\n";
                }
            }
        }
        return res;
    }

    public  String checkMyLikeQrWay(String EventKey,String openid){
        String res="谢谢您的关注~";
        try {
            //调用接口
            signC = this;
            signC.wxOrderService=this.wxOrderService;
            signC.qrsceneService=this.qrsceneService;
            signC.wxUserService=this.wxUserService;
            signC.accessTokenUtils=this.accessTokenUtils;
            //如果扫码关注
            String accessToken=signC.accessTokenUtils.getAccessToken(1);//获取accessToken
            if(!EventKey.equals("")){
                //调用接口
                int str=EventKey.indexOf("_");
                String scanKey=EventKey.substring(str+1);
                Qrscene param=new Qrscene();
                param.setScene(scanKey);
                Qrscene qrData=signC.qrsceneService.queryObj(param);
                if(qrData!=null){
                    WxUser checkParam=new WxUser();
                    checkParam.setOpenid(openid);
                    checkParam.setShopId(1);
                    WxUser data=signC.wxUserService.queryObj(checkParam);
                    if(data!=null){
                        return res;
                    }
                    //如果是扫码进来的，保存用户数据
                    WxUser wxUser= getUserInfoFromWX(accessToken,openid);
                    wxUser.setIntroducerId(qrData.getId());//设置介绍来源为二维码ID号
                    wxUser.setShopId(1);
                    signC.wxUserService.insert(wxUser);
                }
            }else {
             //正常关注
                WxUser checkParam=new WxUser();
                checkParam.setOpenid(openid);
                checkParam.setShopId(1);
                WxUser data=signC.wxUserService.queryObj(checkParam);
                if(data!=null){
                    return res;
                }
                WxUser wxUser= getUserInfoFromWX(accessToken,openid);
                wxUser.setShopId(1);
                wxUser.setIntroducerId(0);
                signC.wxUserService.insert(wxUser);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }
    public  String checkDTQrWay(String EventKey,String openid){
        String res="谢谢您的关注~";
        try {
            //调用接口
            signC = this;
            signC.wxOrderService=this.wxOrderService;
            signC.qrsceneService=this.qrsceneService;
            signC.wxUserService=this.wxUserService;
            signC.accessTokenUtils=this.accessTokenUtils;
            //如果扫码关注
            String accessToken=signC.accessTokenUtils.getAccessToken(2);//获取accessToken
            log.info("aEventKey="+EventKey);
            if(!EventKey.equals("")){
                //调用接口
                log.info("bEventKey="+EventKey);
                int str=EventKey.indexOf("_");
                log.info("str="+str);
                String scanKey=EventKey.substring(str+1);
                log.info("scanKey="+scanKey);
                Qrscene param=new Qrscene();
                param.setScene(scanKey);
                Qrscene qrData=signC.qrsceneService.queryObj(param);
                if(qrData!=null){
                    WxUser checkParam=new WxUser();
                    checkParam.setOpenid(openid);
                    checkParam.setShopId(2);
                    WxUser data=signC.wxUserService.queryObj(checkParam);
                    if(data!=null){
                        return res;
                    }
                    //如果是扫码进来的，保存用户数据
                    WxUser wxUser= getUserInfoFromWX(accessToken,openid);
                    wxUser.setIntroducerId(qrData.getId());//设置介绍来源为二维码ID号
                    wxUser.setShopId(2);
                    signC.wxUserService.insert(wxUser);
                }
            }else {
             //正常关注
                WxUser checkParam=new WxUser();
                checkParam.setOpenid(openid);
                checkParam.setShopId(2);
                WxUser data=signC.wxUserService.queryObj(checkParam);
                if(data!=null){
                    return res;
                }
                WxUser wxUser= getUserInfoFromWX(accessToken,openid);
                wxUser.setShopId(2);
                wxUser.setIntroducerId(0);
                signC.wxUserService.insert(wxUser);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return res;
    }

    private static WxUser getUserInfoFromWX(String accessToken,String openid)throws Exception {
        String params="access_token=";
        params += accessToken;
        params +="&openid=";
        params +=openid;
        params +="&lang=";
        params +="zh_CN";
        WxUser userData=new WxUser();
        String subscribers = HttpUtils.sendGet("https://api.weixin.qq.com/cgi-bin/user/info", params);
        System.out.println(subscribers);
        //这里返回参数只取了昵称、头像、和性别
        String nickname=JSONObject.fromObject(subscribers).getString("nickname");
        String headimgurl=JSONObject.fromObject(subscribers).getString("headimgurl");
        String sex=JSONObject.fromObject(subscribers).getString("sex");
        String city=JSONObject.fromObject(subscribers).getString("city");
        String province=JSONObject.fromObject(subscribers).getString("province");
        String country=JSONObject.fromObject(subscribers).getString("country");
            userData.setOpenid(openid);
            userData.setCity(city);
            userData.setNickName(nickname);
            userData.setAvatarUrl(headimgurl);
            userData.setGender(sex);
            userData.setProvince(province);
            userData.setCountry(country);
         return userData;
        }

    public String[] getSETime(){
        Date date=new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String stime = sdf.format(date)+" 00:00:00";
        String etime=sdf.format(date)+" 23:59:59";
        log.info("stime="+stime);
        log.info("etime="+etime);
        String[] test={stime,etime};
        return test;
    }

    /*public static  void main(String[] args){
        String accesstoken="33_GrVYvNJ1J6iCbon63Hi9vP0g5H2UHrLXOpaD6DfW-SrT7K2UsY3ilUXNOcq_AHkw4UfeaHlhJZVOv9XAKZSy5cCNMJvGGdzNehEMx2ePSu5yYl-yoR4_RDVki1bjRMyd2QxcF58_eKapvr2vIDYiAHARMH";
        String url="https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accesstoken;
        //button array
        JSONArray btnArray=new JSONArray();
        //button1
        JSONObject btn1Json=new JSONObject();
        btn1Json.put("type","view");
        btn1Json.put("name","登特口腔");
        btn1Json.put("url","https://mp.weixin.qq.com/s?__biz=MzU5OTkzNzM0Nw==&mid=100000012&idx=1&sn=78fc9d687895899c3b2b77c144122b14&chksm=7eac192049db9036dd34916d453a061bfa205dbba232bfba3ae27794512e442196bd16abbea8&scene=18#wechat_redirect");
        //button2
        JSONObject btn2Json=new JSONObject();
        JSONArray  btn2SubArray=new JSONArray();
        JSONObject  btn2SubBtn=new JSONObject();
        btn2Json.put("name","关于登特");
            //创建子菜单
            btn2SubBtn.put("name","品牌介绍");
            btn2SubBtn.put("type","view");
            btn2SubBtn.put("url","https://mp.weixin.qq.com/s?__biz=MzU5OTkzNzM0Nw==&tempkey=MTA1N19hTEVseG1vTFVUWStYbC9EMVpaY01OaFFHemVzVkNORmRpUE9HQXlsTXNFUV9FTjdvQjJTTWY3OG5ITmNhUmlHei1vWm9zWVBuUFZOWWxmQjB1RC1uN3Q1S1lDVldpRkowUm1tQ1h5ejlVaVBmLU5sYkRwaUVYVWdQYUtqdDA5VXJPYm1uU0pjUlJoRFB4MnpmRTRJaUZxOVM5VmR0V3dyV0paRnlnfn4%3D&chksm=7eac192049db9036dd34916d453a061bfa205dbba232bfba3ae27794512e442196bd16abbea8#rd");
            btn2SubArray.add(btn2SubBtn);
            btn2SubBtn.clear();//清空
            btn2SubBtn.put("name","矫正排队");
            btn2SubBtn.put("type","click");
            btn2SubBtn.put("key","CORRECTORDER");
            btn2SubArray.add(btn2SubBtn);
            btn2SubBtn.put("name","修复排队");
            btn2SubBtn.put("type","click");
            btn2SubBtn.put("key","REPAIRORDER");
            btn2SubArray.add(btn2SubBtn);
            btn2SubBtn.put("name","洗牙排队");
            btn2SubBtn.put("type","click");
            btn2SubBtn.put("key","CLEANORDER");
            btn2SubArray.add(btn2SubBtn);
            btn2SubBtn.clear();//清空
//            btn2SubBtn.put("name","查看排队进展");
//            btn2SubBtn.put("type","click");
//            btn2SubBtn.put("key","CHECKORDER");
//            btn2SubArray.add(btn2SubBtn);
        btn2Json.put("sub_button",btn2SubArray);
        //button3
        JSONObject btn3Json=new JSONObject();
        btn3Json.put("type","view");
        btn3Json.put("name","最新消息");
        btn3Json.put("url","http://mp.weixin.qq.com/s?__biz=MzU5OTkzNzM0Nw==&mid=2247483730&idx=1&sn=776ee045d7e318758ae44537eca76a98&chksm=feac197ec9db9068425e550d12ce5f7ba02a22e4874e5a9655bdee24e8890612f0e315f6de32&scene=18#wechat_redirect");
        btnArray.add(btn1Json);
        btnArray.add(btn2Json);
        btnArray.add(btn3Json);

        JSONObject json=new JSONObject();
        json.put("button",btnArray);
        String result = HttpUtils.doPostJson(url,json.toString());

        System.out.println("自定义菜单创建接口" + result);
    }*/

    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        // 对token、timestamp、和nonce按字典排序.
        String[] paramArr = new String[] {token, timestamp, nonce};
        Arrays.sort(paramArr);

        // 将排序后的结果拼接成一个字符串.
        String content  = paramArr[0].concat(paramArr[1]).concat(paramArr[2]);

        String ciphertext = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // 对拼接后的字符串进行sha1加密.
            byte[] digest = md.digest(content.toString().getBytes());
            ciphertext = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 将sha1加密后的字符串与signature进行对比.
        return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
    }

    /**
     * 将字节数组转换为十六进制字符串.
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转换为十六进制字符串.
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1' , '2', '3', '4' , '5', '6', '7' , '8', '9', 'A' , 'B', 'C', 'D' , 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }

    public static void main(String[] args){
        String EventKey="qrscene_QRCodeADin0618";
        int str=EventKey.indexOf("_");
        String ScanKey=EventKey.substring(str+1);
        log.info("ScanKey="+ScanKey);
//        Date date=new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        String stime = sdf.format(date)+" 00:00:00";
//        String etime=stime+" 23:59:59";
//        log.info("stime="+stime);
//        log.info("etime="+etime);
        Random luckyRabbitFoot=new Random();//随机数的产生者
        int bet=5;//你下的注数
        for(int luck=0;luck<bet;luck++){
            System.out.print("LuckRabbitFoot tell U Num : ");
            int[] frontNum=new int[5];
            int[] backNum=new int[2];
            //财富密码前5位了，
            for(int i=0;i<frontNum.length;i++){
                frontNum[i]=luckyRabbitFoot.nextInt(35)+1;
                if(i==frontNum.length-1){
                    System.out.print(frontNum[i]);
                }else{
                    System.out.print(frontNum[i]+",");
                }
            }
            //财富密码后2位
            for(int b=0;b<backNum.length;b++){
                backNum[b]=luckyRabbitFoot.nextInt(12)+1;
                if(b==backNum.length-1){
                    System.out.print(backNum[b]);
                }else if(b==0){
                    System.out.print(" |"+backNum[b]+",");
                }
            }
            System.out.println("   |    =(:з」∠)_");
        }

    }
}