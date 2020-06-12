package com.wx_shop.serviceshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (SysUser)实体类
 *
 * @author makejava
 * @since 2019-12-18 09:52:02
 */
public class SysUser implements Serializable {
    private static final long serialVersionUID = 569778556794289884L;
    
    private Integer id;

    private Integer notId;
    //名称
    private String name;
    //密码
    private String password;
    //1:系统管理员 2商家管理员
    private Integer type;
    //商家ID
    private Integer shopId;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;
    //头像图片
    private String headImg;
    //真实姓名
    private String realName;

    public Integer getNotId() {
        return notId;
    }

    public void setNotId(Integer notId) {
        this.notId = notId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

}