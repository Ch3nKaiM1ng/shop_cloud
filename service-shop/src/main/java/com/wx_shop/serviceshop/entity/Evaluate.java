package com.wx_shop.serviceshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (Evaluate)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:39:00
 */
public class Evaluate implements Serializable {
    private static final long serialVersionUID = 546412063631523342L;
    
    private Integer evaluateId;
    //订单id
    private Integer mainOrderId;

    private Integer commodityId;
    //店家ID
    private Integer shopId;
    //用户id
    private Integer userId;
    //评论内容
    private String content;
    //店家回复
    private String shopReply;
    //图片
    private String img;
    //评分
    private Double score;

    private Double testScore;

    private Double weightScore;

    private List<WxUser> userData;
    //创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;

    //是否显示图片
    private Integer haveImg;

    //当前页
    private int offset;
    //显示数量
    private int limit;

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public Integer getHaveImg() {
        return haveImg;
    }

    public void setHaveImg(Integer haveImg) {
        this.haveImg = haveImg;
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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getTestScore() {
        return testScore;
    }

    public void setTestScore(Double testScore) {
        this.testScore = testScore;
    }

    public Double getWeightScore() {
        return weightScore;
    }

    public void setWeightScore(Double weightScore) {
        this.weightScore = weightScore;
    }

    public List<WxUser> getUserData() {
        return userData;
    }

    public void setUserData(List<WxUser> userData) {
        this.userData = userData;
    }

    public Integer getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(Integer evaluateId) {
        this.evaluateId = evaluateId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShopReply() {
        return shopReply;
    }

    public void setShopReply(String shopReply) {
        this.shopReply = shopReply;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }



    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}