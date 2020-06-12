package com.wx_shop.serviceshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (SubOrder)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:39:03
 */
public class SubOrder implements Serializable {
    private static final long serialVersionUID = 890476964572023947L;
    
    private Integer subOrderId;
    
    private Integer mainOrderId;
    
    private Integer commodityId;

    private String commodityName;

    private String commodityImg;

    private Double commodityPrice;
    
    private Integer buyNum;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;

    public String getCommodityImg() {
        return commodityImg;
    }

    public void setCommodityImg(String commodityImg) {
        this.commodityImg = commodityImg;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public Integer getSubOrderId() {
        return subOrderId;
    }

    public void setSubOrderId(Integer subOrderId) {
        this.subOrderId = subOrderId;
    }

    public Integer getMainOrderId() {
        return mainOrderId;
    }

    public void setMainOrderId(Integer mainOrderId) {
        this.mainOrderId = mainOrderId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(Double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}