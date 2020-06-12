package com.wx_shop.serviceshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * (CommodityType)实体类
 *
 * @author makejava
 * @since 2019-12-16 10:38:58
 */
public class CommodityType implements Serializable {
    private static final long serialVersionUID = 550118933765691756L;
    
    private Integer typeId;
    //商家ID
    private Integer shopId;
    //分类名称
    private String typeName;
    //必选1必选0可选
    private String typeMust;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date ctime;

    private Integer state;

    private List<Commodity> commodityList;

    public List<Commodity> getCommodityList() {
        return commodityList;
    }

    public void setCommodityList(List<Commodity> commodityList) {
        this.commodityList = commodityList;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeMust() {
        return typeMust;
    }

    public void setTypeMust(String typeMust) {
        this.typeMust = typeMust;
    }

}