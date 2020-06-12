package com.wx_shop.serviceshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (Commodity)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:16:05
 */
public class Commodity implements Serializable {
    private static final long serialVersionUID = -34164607918500996L;
    
    private Integer commodityId;
    //商家id
    private Integer shopId;
    //必选
    private Integer commodityTypeId;
    //名称
    private String commodityName;
    //商品介绍
    private String commodityIntroduce;
    //商品标签
    private String commodityLabel;
    //库存
    private String commodityStock;
    //销量
    private String commoditySales;
    //好评率
    private Integer commodityGood;
    //参考价
    private Double commodityPrice;
    //真实价格
    private Double commodityTruePrice;
    //图片
    private String commodityImg;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date cTime;

    private Integer state;

    private Integer sales;

    public String getCommodityLabel() {
        return commodityLabel;
    }

    public void setCommodityLabel(String commodityLabel) {
        this.commodityLabel = commodityLabel;
    }

    //当前页
    private int offset;
    //显示数量
    private int limit;

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
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

    public Date getcTime() {
        return cTime;
    }

    public void setcTime(Date cTime) {
        this.cTime = cTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCommodityTypeId() {
        return commodityTypeId;
    }

    public void setCommodityTypeId(Integer commodityTypeId) {
        this.commodityTypeId = commodityTypeId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityIntroduce() {
        return commodityIntroduce;
    }

    public void setCommodityIntroduce(String commodityIntroduce) {
        this.commodityIntroduce = commodityIntroduce;
    }

    public String getCommodityStock() {
        return commodityStock;
    }

    public void setCommodityStock(String commodityStock) {
        this.commodityStock = commodityStock;
    }

    public String getCommoditySales() {
        return commoditySales;
    }

    public void setCommoditySales(String commoditySales) {
        this.commoditySales = commoditySales;
    }

    public Integer getCommodityGood() {
        return commodityGood;
    }

    public void setCommodityGood(Integer commodityGood) {
        this.commodityGood = commodityGood;
    }

    public Double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public Double getCommodityTruePrice() {
        return commodityTruePrice;
    }

    public void setCommodityTruePrice(Double commodityTruePrice) {
        this.commodityTruePrice = commodityTruePrice;
    }

    public String getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(String commodityImg) {
        this.commodityImg = commodityImg;
    }

}