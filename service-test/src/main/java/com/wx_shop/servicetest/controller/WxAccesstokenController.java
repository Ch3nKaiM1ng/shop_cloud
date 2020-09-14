package com.wx_shop.servicetest.controller;

import com.wx_shop.servicetest.entity.WxAccesstoken;
import com.wx_shop.servicetest.service.WxAccesstokenService;
import com.wx_shop.servicetest.utils.AccessTokenUtils;
import com.wx_shop.servicetest.utils.ReturnDiscern;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (WxAccesstoken)表控制层
 *
 * @author makejava
 * @since 2020-04-14 19:00:39
 */
@RestController
@RequestMapping("wxAccesstoken")
public class WxAccesstokenController {
    /**
     * 服务对象
     */
    @Resource
    private WxAccesstokenService service;
    
    private ReturnDiscern re =new ReturnDiscern();

    @Autowired
    private AccessTokenUtils accessTokenUtils;

    @Autowired
    private RestTemplate restTemplate;

    private static Logger log= LoggerFactory.getLogger(WxAccesstokenController.class);

    @RequestMapping("selectOne")
    public Map<String, Object> selectOne(Integer id) {
        WxAccesstoken dataObj=service.queryById(id);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findObj")
    public Map<String, Object> findObj(@RequestBody WxAccesstoken entity) {
     WxAccesstoken dataObj=service.queryObj(entity);
        return re.SUCCESSOBJ(dataObj);
    }
    
    @RequestMapping("findAll")
    public Map<String, Object> findAll(@RequestBody WxAccesstoken entity) {
    List<WxAccesstoken> dataList =service.queryAll(entity);
        return re.SUCCESSOBJ(dataList);
    }
    
    @RequestMapping("updateObj")
    public Map<String, Object> updateObj(@RequestBody WxAccesstoken entity) {
        service.update(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("addObj")
    public Map<String, Object> addObj(@RequestBody WxAccesstoken entity) {
        service.insert(entity);
        if(entity.getId() != null){
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("操作出现异常500");
        }
    }
    
    @RequestMapping("delObj")
    public Map<String, Object> delObj(@RequestBody WxAccesstoken entity) {
        
        if(entity.getId() != null){
        service.deleteById(entity.getId());
        return re.SUCCESS();
        }else{
            return re.ERRORMSG("缺少参数ID");
        }
    }


    @RequestMapping("getAccessToken")
    public Map<String, Object> getAccessToken(@RequestBody JSONObject jsonObject) {
        int id=jsonObject.getInt("id");
        String code=jsonObject.getString("code");
        JSONObject data=accessTokenUtils.getCodeAccessToken(id,code);
        return re.SUCCESSOBJ(data);
    }

    @RequestMapping("getRedis")
    public Map<String, Object> getRedis(@RequestBody JSONObject jsonObject) {
        Jedis jedis=new Jedis("47.107.47.13");
        String data=jedis.get("code_accesstoken");
        return re.SUCCESSOBJ(data);
    }

    @GetMapping("delRedis")
    public Map<String, Object> getForObj(@RequestBody JSONObject jsonObject) {
        Jedis jedis=new Jedis("47.107.47.13");
        jedis.del("code_accesstoken");
        String data=jedis.get("code_accesstoken");
        return re.SUCCESSOBJ(data);
    }
}