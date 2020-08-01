package com.wx_shop.servicetest.entity;

import java.io.Serializable;
import java.util.List;

/**
 * (Qrscene)实体类
 *
 * @author makejava
 * @since 2020-06-04 15:42:27
 */
public class Qrscene implements Serializable {
    private static final long serialVersionUID = 698527104452548824L;
    
    private Integer id;
    //识别标号
    private String ticket;
    //二维码值
    private String scene;
    //用途
    private String usefor;

    private List<WxUser> userDataList;

    public List<WxUser> getUserDataList() {
        return userDataList;
    }

    public void setUserDataList(List<WxUser> userDataList) {
        this.userDataList = userDataList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getUsefor() {
        return usefor;
    }

    public void setUsefor(String usefor) {
        this.usefor = usefor;
    }

}