package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.PhoneRecord;
import com.wx_shop.servicetest.entity.WxFeedback;
import com.wx_shop.servicetest.service.WxFeedbackService;
import com.wx_shop.servicetest.utils.AccessTokenUtils;
import com.wx_shop.servicetest.utils.ExcelUtil;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import com.wx_shop.servicetest.utils.wxMsgUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * (WxFeedback)表控制层
 *
 * @author makejava
 * @since 2020-07-22 14:17:12
 */
@RestController
@RequestMapping("wxFeedback")
public class WxFeedbackController {
    /**
     * 服务对象
     */
    @Resource
    private WxFeedbackService service;
    
    private ReturnDiscern re =new ReturnDiscern();

    private wxMsgUtils wxMsgUtils=new wxMsgUtils();

    private AccessTokenUtils accessTokenUtils=new AccessTokenUtils();

    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        WxFeedback dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxFeedback entity) {
     WxFeedback dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody WxFeedback entity) {
    List<WxFeedback> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxFeedback entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxFeedback entity) {
        service.insert(entity);
        if(entity.getAppid()==1){
            //用户投诉美莱
            String accessToken=accessTokenUtils.getAccessToken(entity.getAppid());
            wxMsgUtils.sendReplyMsgMyLike(entity,accessToken,entity.getAppid());
        }else if(entity.getAppid()==2){
            //用户投诉登特
            String accessToken=accessTokenUtils.getAccessToken(entity.getAppid());
            wxMsgUtils.sendReplyMsgMyLike(entity,accessToken,entity.getAppid());
        }
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxFeedback entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
    @RequestMapping("exportTodayList")
    public void  exportTodayList(HttpServletRequest request, HttpServletResponse response){
        int appid=Integer.parseInt(request.getParameter("appid"));
        String appWay="";
        if(appid==1){
            appWay="美莱";
        }else{
            appWay="登特";
        }
        WxFeedback wxFeedback=new WxFeedback();
        wxFeedback.setAppid(appid);
        List<WxFeedback> dataList =service.queryAll(wxFeedback);
        //excel标题
        String[] title = {"id","渠道","姓名","手机号码","就诊日期","上午/下午","就诊项目","反馈类型","问题描述","建议补充","数据提交时间"};
        Date date=new Date();

        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd");
        SimpleDateFormat dateTime=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String Day=sdf.format(date);
        //excel文件名
        String fileName = Day+"号导出数据.xls";
        //sheet名
        String sheetName = "网站表单数据";


        String[][] content = new String[dataList.size()+1][title.length];
        for (int i = 0; i < dataList.size(); i++) {
            WxFeedback data = dataList.get(i);
            String meatTime="上午";
            if(data.getMeatTime()==2){
                meatTime="下午";
            }

            content[i][0] = data.getId()+"";
            content[i][1] = appWay;
            content[i][2] = data.getName();
            content[i][3] = data.getPhone();
            content[i][4] = sdf.format(data.getMeatDay());
            content[i][5] = meatTime;
            content[i][6] = data.getMeatProject();
            content[i][7] = data.getFeedbackType();
            content[i][8] = data.getProblemDescription();
            content[i][9] = data.getContent();
            content[i][10]= dateTime.format(data.getCtime());
        }
        //创建HSSFWorkbook
        HSSFWorkbook wb = ExcelUtil.getHSSFWorkbook(sheetName, title, content, null);
        //响应到客户端
        try {
            this.setResponseHeader(response, fileName);
            OutputStream os = response.getOutputStream();
            wb.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}