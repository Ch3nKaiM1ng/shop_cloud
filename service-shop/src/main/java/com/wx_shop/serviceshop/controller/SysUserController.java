package com.wx_shop.serviceshop.controller;

import com.alibaba.fastjson.JSONObject;
import com.wx_shop.serviceshop.entity.SysUser;
import com.wx_shop.serviceshop.service.SysUserService;
import com.wx_shop.serviceshop.utils.ReturnDiscern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * (SysUser)表控制层
 *
 * @author makejava
 * @since 2019-12-16 10:39:07
 */
@RestController
@RequestMapping("sysUser")
public class SysUserController {
    /**
     * 服务对象
     */
    @Resource
    private SysUserService service;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    HttpSession httpSession ;

    private ReturnDiscern re =new ReturnDiscern();
    
    
    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        SysUser dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }

    @RequestMapping("shopLogin")
    public Map<String, Object> shopLogin(@RequestBody SysUser entity) {
        SysUser dataObj=service.queryObj(entity);
        if(dataObj!=null){
            JSONObject json=new JSONObject();
            json.put("sessionName","adminUser");
            json.put("obj",dataObj);
            String id=dataObj.getId().toString();
            httpSession.setAttribute("adminUser",dataObj);
            httpSession.getAttribute("adminUser");
            return re.SUCCESSOBJ(json);
        }
        return re.ERROR();
    }
    @RequestMapping("adminLogin")
    public Map<String, Object> adminLogin(@RequestBody SysUser entity) {
        entity.setType(1);
        SysUser dataObj=service.queryObj(entity);
        if(dataObj!=null){
            JSONObject json=new JSONObject();
            json.put("sessionName","adminUser");
            json.put("obj",dataObj);
            String id=dataObj.getId().toString();
            httpSession.setAttribute("adminUser",dataObj);
            httpSession.getAttribute("adminUser");
            return re.SUCCESSOBJ(json);
        }
        return re.ERROR();
    }

    //  后台登陆
    @RequestMapping("/getUserSession")
    @CrossOrigin
    public Map<String, Object> getUserSession() {
        httpSession.getAttribute("adminUser");
        SysUser admin= (SysUser) httpSession.getAttribute("adminUser");
        if(admin!=null){
            return re.SUCCESSOBJ(admin);
        }else{
            return re.ERRORMSG("没有用户数据");
        }
    }

    @RequestMapping("/outLogin")
    @CrossOrigin
    public Map<String, Object> outLogin() {
        httpSession.removeAttribute("adminUser");
        return re.SUCCESS();
    }
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody SysUser entity) {
     SysUser dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }

    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody SysUser entity) {
    List<SysUser> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody SysUser entity) {

        SysUser checkUser=new SysUser();
        checkUser.setNotId(entity.getId());
        checkUser.setName(entity.getName());
        List objList=service.queryAll(checkUser);
        if(objList.size()>0){
            return re.ERRORMSG("账号重复，已有该账号名");
        }
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody SysUser entity) {
        SysUser checkUser=new SysUser();
        checkUser.setNotId(entity.getId());
        checkUser.setName(entity.getName());
        List objList=service.queryAll(checkUser);
        if(objList.size()>0){
            return re.ERRORMSG("账号重复，已有该账号名");
        }
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody SysUser entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }
}