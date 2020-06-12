package com.wx_shop.servicetest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (WxAccesstoken)实体类
 *
 * @author makejava
 * @since 2020-04-14 19:00:39
 */
public class WxAccesstoken implements Serializable {
    private static final long serialVersionUID = -10239661370916432L;
    
    private Integer id;
    
    private Integer wxid;
    
    private String accessToken;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWxid() {
        return wxid;
    }

    public void setWxid(Integer wxid) {
        this.wxid = wxid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}