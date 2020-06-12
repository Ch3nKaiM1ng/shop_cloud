package com.wx_shop.servicetest.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Doctor)实体类
 *
 * @author makejava
 * @since 2020-06-04 10:39:23
 */
public class Doctor implements Serializable {
    private static final long serialVersionUID = -57748494176679415L;
    
    private Integer id;
    //医生名称
    private String doctorName;
    //医生编号
    private String doctorCode;
    //医生图片
    private String doctorImg;
    //医生备注
    private String doctorRemark;
    //医生费用
    private Double doctorCost;
    //创建时间
    private Date ctime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorCode() {
        return doctorCode;
    }

    public void setDoctorCode(String doctorCode) {
        this.doctorCode = doctorCode;
    }

    public String getDoctorImg() {
        return doctorImg;
    }

    public void setDoctorImg(String doctorImg) {
        this.doctorImg = doctorImg;
    }

    public String getDoctorRemark() {
        return doctorRemark;
    }

    public void setDoctorRemark(String doctorRemark) {
        this.doctorRemark = doctorRemark;
    }

    public Double getDoctorCost() {
        return doctorCost;
    }

    public void setDoctorCost(Double doctorCost) {
        this.doctorCost = doctorCost;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

}