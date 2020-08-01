package com.wx_shop.servicetest.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.io.Serializable;

/**
 * (WxFeedback)实体类
 *
 * @author makejava
 * @since 2020-07-22 14:17:12
 */
public class WxFeedback implements Serializable {
    private static final long serialVersionUID = -58269752976288215L;
    
    private Integer id;
    //公众号iD
    private Integer appid;
    //用户微信openid
    private String openid;
    //就诊日期
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(
            pattern = "yyyy-MM-dd",
            timezone = "GMT+8"
    )
    private Date meatDay;
    //1上午 2下午
    private Integer meatTime;
    //就诊项目
    private String meatProject;
    //姓名
    private String name;
    //反馈类型
    private String feedbackType;
    //问题描述
    private String problemDescription;
    //详细补充
    private String content;
    //图片
    private String image;
    //手机号码
    private String phone;
    //创建时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;
    //1正常
    private String status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeatProject() {
        return meatProject;
    }

    public void setMeatProject(String meatProject) {
        this.meatProject = meatProject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Date getMeatDay() {
        return meatDay;
    }

    public void setMeatDay(Date meatDay) {
        this.meatDay = meatDay;
    }

    public Integer getMeatTime() {
        return meatTime;
    }

    public void setMeatTime(Integer meatTime) {
        this.meatTime = meatTime;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}