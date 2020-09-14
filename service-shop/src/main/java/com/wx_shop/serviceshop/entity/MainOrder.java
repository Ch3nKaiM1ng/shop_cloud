package com.wx_shop.serviceshop.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (MainOrder)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:39:01
 */
public class MainOrder implements Serializable {
    private static final long serialVersionUID = -79719381018493085L;
    
    private Integer mainOrderId;
    //店家ID
    private Integer shopId;
    //用户ID
    private Integer userId;
    //配送地址
    private String sendAddress;
    private String orderCode;
    //电话号码
    private String mobile;
    //电话号码
    private String receive;
    //备注
    private String remarks;
    //状态0.未支付1.已支付，正在配送2.已送达3.确认收货
    private String status;
    //
    //0未支付1已支付
    private String payType;
    //0商家收款1微信支付
    private String payWay;
    //微信支付订单号
    private String wxPayOrder;
    //评价与否 0未评价 1已评价
    private String evaluate;
    private Double totalPrice;

    //创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;

    private List<SubOrder> subOrder;

    //当前页
    private int offset;
    //显示数量
    private int limit;

    private WxUser userData;

    String openId;

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public WxUser getUserData() {
        return userData;
    }

    public void setUserData(WxUser userData) {
        this.userData = userData;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
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

    public List<SubOrder> getSubOrder() {
        return subOrder;
    }

    public void setSubOrder(List<SubOrder> subOrder) {
        this.subOrder = subOrder;
    }

    public Integer getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(Integer mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getWxPayOrder() {
        return wxPayOrder;
    }

    public void setWxPayOrder(String wxPayOrder) {
        this.wxPayOrder = wxPayOrder;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}