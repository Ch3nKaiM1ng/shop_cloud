package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.Doctor;
import com.wx_shop.servicetest.entity.WxOrder;
import com.wx_shop.servicetest.entity.WxUser;
import com.wx_shop.servicetest.service.WxOrderService;
import com.wx_shop.servicetest.service.WxUserService;
import com.wx_shop.servicetest.utils.AccessTokenUtils;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import com.wx_shop.servicetest.utils.wxMsgUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.applet.Main;
import sun.rmi.runtime.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;

/**
 * (WxOrder)表控制层
 *
 * @author makejava
 * @since 2020-04-14 16:43:14
 */
@RestController
@RequestMapping("wxOrder")
public class WxOrderController {
    /**
     * 服务对象
     */
    @Resource
    private WxOrderService service;

    @Resource
    private WxUserService wxUserService;

    private ReturnDiscern re =new ReturnDiscern();

    wxMsgUtils wxmsg=new wxMsgUtils();

    private AccessTokenUtils accessTokenUtils=new AccessTokenUtils();

    private static Logger log= LoggerFactory.getLogger(WxOrderController.class);

    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        WxOrder dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxOrder entity) {
     WxOrder dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody JSONObject json) {
        WxOrder wxOrder=new WxOrder();
        wxOrder.setIsuse(json.getString("isuse"));
        wxOrder.setOrdertype(json.getInt("ordertype"));
        List<WxOrder> dataList =service.queryAll(wxOrder);
        for(int i=0;i<dataList.size();i++){
            WxOrder data=dataList.get(i);
            String openid=data.getOpenid();
            WxUser userParam=new WxUser();
            userParam.setOpenid(openid);
            data.setUserObj(wxUserService.queryObj(userParam));
        }
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("findAllBack")
    public Map<String, Object> findAllBack(@RequestBody WxOrder entity) {
        List<WxOrder> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }

    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxOrder entity) {
        service.update(entity);
        if(entity.getOrderId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("orderJump")
    public Map<String, Object> orderJump(@RequestBody WxOrder entity) {
        WxOrder lastOne=service.orderJump(entity);
        if(entity.getOrderId() != null){
            return re.SUCCESSOBJ(lastOne);
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("orderArrive")
    public Map<String, Object> orderArrive(@RequestBody WxOrder entity) {
        WxOrder lastOne=service.orderArrive(entity);
        if(entity.getOrderId() != null){
            return re.SUCCESSOBJ(lastOne);
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }

    @RequestMapping("finishOrder")
    public Map<String, Object> finishOrder(@RequestBody WxOrder entity) {
        int appid=entity.getAppId();
        WxOrder param=service.update(entity);
        if(param==null){
            return re.ERRORMSG("orderId或者appid错误缺失");
        }
//        int ordertype=param.getOrdertype();
        String stime=entity.getStime();
        String eTime=entity.getEtime();
        int sendMsg=entity.getSendMsg();
        WxOrder checkParam=new WxOrder();
//        checkParam.setOrdertype(ordertype);//设置排队的列表
        checkParam.setState(1);//已到场
        checkParam.setIsuse("0");//未使用
        checkParam.setStime(stime);//未使用
        checkParam.setEtime(eTime);//未使用
        checkParam.setDoctorId(entity.getDoctorId());
        List<WxOrder> wxOrderList=service.queryTopListByorderNum(checkParam);//获取叫号完的当前列表进行微信模板推送
        if(wxOrderList.size()==0){
            return re.ERRORMSG("当前没有排队进程");
        }else{
            String accessToken=accessTokenUtils.getAccessToken(appid);
            for(int i=0;i<wxOrderList.size();i++){
                WxOrder wxOrder=wxOrderList.get(i);
                Date ctime=wxOrder.getComeTime();//取号时间
                Doctor doctorData=wxOrder.getDoctorData();
                String doctorCode=doctorData.getDoctorCode();
                String frontType="";
//                if(ordertype==1){
//                    frontType="JZ";
//                }if(ordertype==2){
//                    frontType="RP";
//                }if(ordertype==3){
//                    frontType="XY";
//                }
                //获取第一条，判断是否有OPENID
                if(sendMsg==1) {
                    if (i == 0) {
                        log.info("First=" + wxOrder.getOpenid());
                        if (wxOrder.getOpenid() == null) {
                            continue;
                        } else {
                            log.info("sendFirst=" + wxOrder.getOpenid());
                            wxmsg.sendTemplateMessages(wxOrder.getOpenid(), frontType + doctorCode + wxOrder.getOrderNum().toString(), "0", accessToken, ctime);
                        }
                    }

                    //推送第二条
                    if (i == 1) {
                        log.info("Second=" + wxOrder.getOpenid());
                        if (wxOrder.getOpenid() == null) {
                            continue;
                        } else {
                            log.info("sendSecond=" + wxOrder.getOpenid());
                            wxmsg.sendTemplateMessages(wxOrder.getOpenid(), frontType + doctorCode + wxOrder.getOrderNum().toString(), "1", accessToken, ctime);
                        }
                    }
                }else{
                    log.info("sendMsg=="+sendMsg+" so not Send MSG");
                }
            }
        }
        return re.SUCCESSOBJ(wxOrderList);
    }
    @RequestMapping("callOrder")
    public Map<String, Object> callOrder(@RequestBody JSONObject json) {
        if(json.get("id")==null){
            return re.ERRORMSG("id为空");
        }
        if(json.get("ordertype")==null){
            return re.ERRORMSG("ordertype为空");
        }
        int id=Integer.parseInt(json.get("id").toString());
        int ordertype=json.getInt("ordertype");//操作的叫号种类

        String accessToken=accessTokenUtils.getAccessToken(id);
        //开始叫号操作
        WxOrder param=new WxOrder();
        param.setIsuse("0");//查找未使用的
        param.setOrdertype(ordertype);
        service.callOrder(param);//将第一条设置为已经使用
        List<WxOrder> wxOrderList=service.queryTopList(param);//获取叫号完的当前列表进行微信模板推送
        if(wxOrderList.size()==0){
            return re.ERRORMSG("当前没有排队进程");
        }
        for(int i=0;i<wxOrderList.size();i++){
            WxOrder wxOrder=wxOrderList.get(i);
            Date ctime=wxOrder.getComeTime();
            String openid=wxOrder.getOpenid();
            WxUser userParam=new WxUser();
            userParam.setOpenid(openid);
            wxOrder.setUserObj(wxUserService.queryObj(userParam));
            String frontType="";
            if(ordertype==1){
                frontType="JZ";
            }if(ordertype==2){
                frontType="RP";
            }if(ordertype==3){
                frontType="XY";
            }
            //推送第一条
            if(i==0){

                wxmsg.sendTemplateMessages(wxOrder.getOpenid(),frontType+wxOrder.getOrderNum().toString(),"0",accessToken,ctime);
            }
            //推送第二条
            if(i==1){
                wxmsg.sendTemplateMessages(wxOrder.getOpenid(),frontType+wxOrder.getOrderNum().toString(),"1",accessToken,ctime);
            }
        }
        return re.SUCCESSOBJ(wxOrderList);
    }

    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxOrder entity) {
        Date saveTime=entity.getAppointmentTime();
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
        String date=sdf.format(saveTime);
        String sTime=date+" 00:00:00";
        String eTime=date+" 23:59:59";
        log.info("date="+date);
        WxOrder checkP=new WxOrder();
        checkP.setIsuse("0");//未使用
        checkP.setPhone(entity.getPhone());//手机号码
        checkP.setStime(sTime);//开始时间
        checkP.setEtime(eTime);//结束时间
        checkP.setNotState(2);
        List<WxOrder> dataList=service.queryAll(checkP);
        if(dataList.size()>0){
            return re.ERRORMSG("这个手机号码今天已经有人预约了！");
        }
        service.insert(entity);
        if(entity.getOrderId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxOrder entity) {
        
        if(entity.getOrderId() != null){
        service.deleteById(entity.getOrderId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
    public static  void main(String[] args){
        SimpleDateFormat format=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        long timeS=1590726516000L;
        Date time=new Date(timeS);
        System.out.println("time="+format.format(time));
//        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
//        String accessToken="33_HAd6eYWm7m2EUKpx5i1vQ30ZLMNjCXmX_ydyNBxJdY9Vwz3h90oL5xThdFWat26bra3A9rEOo8do0yDWIUcVx-91malrdmqcLNYUSvQut3_K5TEOApaaPvUepZ2u_CeSqV0HLIqw51pSSnYQDNDbAFAYIY";
//        wxMsgUtils wxmsg=new wxMsgUtils();
//        wxmsg.sendTemplateMessages("oLeZbvzW-Nj9s3jMlnDt7Z9FQRpo","4","1",accessToken,new Date());
    }
}