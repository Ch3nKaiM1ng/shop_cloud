package com.wx_shop.serviceshop.entity;

public class commodityScore {
    String avgTestScore;
    String avgWeightScore;
    String avgCommodityScore;
    Integer commodityId;
    Integer sales;

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public String getAvgTestScore() {
        return avgTestScore;
    }

    public void setAvgTestScore(String avgTestScore) {
        this.avgTestScore = avgTestScore;
    }

    public String getAvgWeightScore() {
        return avgWeightScore;
    }

    public void setAvgWeightScore(String avgWeightScore) {
        this.avgWeightScore = avgWeightScore;
    }

    public String getAvgCommodityScore() {
        return avgCommodityScore;
    }

    public void setAvgCommodityScore(String avgCommodityScore) {
        this.avgCommodityScore = avgCommodityScore;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
}
