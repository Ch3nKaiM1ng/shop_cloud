package com.wx_shop.serviceshop.entity;

import java.io.Serializable;

/**
 * (Shop)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:39:02
 */
public class Shop implements Serializable {
    private static final long serialVersionUID = 919026429841190925L;
    
    private Integer shopId;
    //商家名称
    private String shopName;
    //商家图片
    private String shopImg;
    //商家地址
    private String shopAddress;
    //商家手机号码
    private String shopMobile;
    //商家资格
    private String shopSeniority;
    //商家营业时间
    private String shopTime;
    //商家开始营业时间
    private String shopStime;
    //商家结束时间
    private String shopEtime;
    //商家公告
    private String shopNotice;
    //0不开通微信支付1开通
    private Integer shopWxpay;
    //起送价
    private Double shopStartpay;

    private Integer status;

    //当前页
    private int offset;
    //显示数量
    private int limit;

    public String getShopStime() {
        return shopStime;
    }

    public void setShopStime(String shopStime) {
        this.shopStime = shopStime;
    }

    public String getShopEtime() {
        return shopEtime;
    }

    public void setShopEtime(String shopEtime) {
        this.shopEtime = shopEtime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopMobile() {
        return shopMobile;
    }

    public void setShopMobile(String shopMobile) {
        this.shopMobile = shopMobile;
    }

    public String getShopSeniority() {
        return shopSeniority;
    }

    public void setShopSeniority(String shopSeniority) {
        this.shopSeniority = shopSeniority;
    }

    public String getShopTime() {
        return shopTime;
    }

    public void setShopTime(String shopTime) {
        this.shopTime = shopTime;
    }

    public String getShopNotice() {
        return shopNotice;
    }

    public void setShopNotice(String shopNotice) {
        this.shopNotice = shopNotice;
    }

    public Integer getShopWxpay() {
        return shopWxpay;
    }

    public void setShopWxpay(Integer shopWxpay) {
        this.shopWxpay = shopWxpay;
    }

    public Double getShopStartpay() {
        return shopStartpay;
    }

    public void setShopStartpay(Double shopStartpay) {
        this.shopStartpay = shopStartpay;
    }

}