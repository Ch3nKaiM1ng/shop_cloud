package com.wx_shop.serviceshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (WxUser)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:39:08
 */
public class WxUser implements Serializable {
    private static final long serialVersionUID = -80709310234671421L;
    
    private Integer userId;
    private Integer shopId;
    private Integer introducerId;
    //用户昵称
    private String nickName;
    //用户头像
    private String avatarUrl;
    //用户所在城市
    private String city;
    //用户所在省份
    private String province;
    //用户所在国家
    private String country;
    //用户手机号码
    private String phone;
    //用户openid
    private String openid;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private String gender;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;

    //当前页
    private int offset;
    //显示数量
    private int limit;

    public Integer getIntroducerId() {
        return introducerId;
    }

    public void setIntroducerId(Integer introducerId) {
        this.introducerId = introducerId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}