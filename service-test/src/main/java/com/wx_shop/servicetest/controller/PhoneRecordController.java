package com.wx_shop.servicetest.controller;

import com.google.gson.JsonObject;
import com.wx_shop.servicetest.entity.PhoneRecord;
import com.wx_shop.servicetest.entity.WxOrder;
import com.wx_shop.servicetest.service.PhoneRecordService;
import com.wx_shop.servicetest.utils.ExcelUtil;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import net.sf.json.JSONObject;
import org.apache.http.HttpRequest;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * (PhoneRecord)表控制层
 *
 * @author makejava
 * @since 2020-05-16 09:16:28
 */
@RestController
@RequestMapping("phoneRecord")
public class PhoneRecordController {
    /**
     * 服务对象
     */
    @Resource
    private PhoneRecordService service;
    
    private ReturnDiscern re =new ReturnDiscern();

    private static Logger log= LoggerFactory.getLogger(PhoneRecordController.class);
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        PhoneRecord dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody PhoneRecord entity) {
     PhoneRecord dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody PhoneRecord entity) {
    if(entity.getSourceurl()!=null && !entity.getSourceurl().isEmpty()){
        String url="%"+entity.getSourceurl()+"%";
        entity.setSourceurl(url);
    }
    List<PhoneRecord> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody PhoneRecord entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody PhoneRecord entity) {
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    @RequestMapping("exportTodayList")
    public void  exportTodayList(HttpServletRequest request, HttpServletResponse response){
        String sourceurl="%"+request.getParameter("sourceurl")+"%";
        String stime=request.getParameter("stime");
        String etime=request.getParameter("etime");
        PhoneRecord phoneRecord=new PhoneRecord();
        phoneRecord.setStime(stime);
        phoneRecord.setEtime(etime);
        phoneRecord.setSourceurl(sourceurl);
        List<PhoneRecord> dataList =service.queryTodayList(phoneRecord);
        //excel标题
        String[] title = {"id","电话","其他信息","投放链接","创建时间"};
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
            PhoneRecord data = dataList.get(i);
            String AddTime=dateTime.format(data.getAddtime());
            System.out.println(data.toString());
            content[i][0] = data.getId()+"";
            content[i][1] = data.getPhone();
            content[i][2] = data.getRemark();
            content[i][3] = data.getSourceurl();
            content[i][4] = AddTime;
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
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody PhoneRecord entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
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