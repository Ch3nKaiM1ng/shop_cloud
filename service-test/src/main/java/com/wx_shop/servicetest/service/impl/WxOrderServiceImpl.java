package com.wx_shop.servicetest.service.impl;

import com.wx_shop.servicetest.entity.WxOrder;
import com.wx_shop.servicetest.dao.WxOrderDao;
import com.wx_shop.servicetest.service.WxOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

/**
 * (WxOrder)表服务实现类
 *
 * @author makejava
 * @since 2020-04-14 16:43:14
 */
@Service("wxOrderService")
public class WxOrderServiceImpl implements WxOrderService {
    @Resource
    private WxOrderDao wxOrderDao;

    /**
     * 通过ID查询单条数据
     *
     * @param orderId 主键
     * @return 实例对象
     */
    @Override
    public WxOrder queryById(Integer orderId) {
        return this.wxOrderDao.queryById(orderId);
    }
    
    @Override
    public WxOrder queryObj(WxOrder wxOrder) {
        return this.wxOrderDao.queryObj(wxOrder);
    }
    @Override
    public WxOrder queryLastOne(WxOrder wxOrder) {
        return this.wxOrderDao.queryLastOne(wxOrder);
    }

    @Override
    public Integer countFasterThanU(WxOrder wxOrder){
        return this.wxOrderDao.countFasterThanU(wxOrder);
    }
    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    @Override
    public List<WxOrder> queryAllByLimit(int offset, int limit) {
        return this.wxOrderDao.queryAllByLimit(offset, limit);
    }
    
    @Override
    public List<WxOrder> queryAll(WxOrder wxOrder) {
        return this.wxOrderDao.queryAll(wxOrder);
    }
    @Override
    public List<WxOrder> queryTopList(WxOrder wxOrder) {
        return this.wxOrderDao.queryTopList(wxOrder);
    }
    @Override
    public List<WxOrder> queryTopListByorderNum(WxOrder wxOrder) {
        return this.wxOrderDao.queryTopListByorderNum(wxOrder);
    }
    /**
     * 新增数据
     *
     *
     * @param wxOrder 实例对象
     * @return 实例对象
     */
    @Override
    public WxOrder insert(WxOrder wxOrder) {
        this.wxOrderDao.insert(wxOrder);
        return wxOrder;
    }

    /**
     * 修改数据
     *
     * @param wxOrder 实例对象
     * @return 实例对象
     */
    @Override
    public WxOrder update(WxOrder wxOrder) {
        this.wxOrderDao.update(wxOrder);
        return this.queryById(wxOrder.getOrderId());
    }
    //叫号
    @Override
    public WxOrder callOrder(WxOrder wxOrder) {
        List<WxOrder> wxOrderList=wxOrderDao.queryTopList(wxOrder);
        for (int i=0;i<wxOrderList.size();i++){
           WxOrder wxParam=wxOrderList.get(i);
            if(i==0){
                wxParam.setIsuse("1");
                wxOrderDao.update(wxParam);
                break;
            }
        }
        return this.queryById(wxOrder.getOrderId());
    }

    //后台操作用户到场
    @Override
    public WxOrder orderArrive(WxOrder wxOrder) {

        WxOrder lastOrder=this.wxOrderDao.queryLastOneByap(wxOrder);
        int orderNum=1;//获取排队号码
        int orderId=wxOrder.getOrderId();
        if(lastOrder!=null){
             orderNum=lastOrder.getOrderNum()+1;//获取排队号码
        }

        //到场分发排队号码
        WxOrder updateParam=new WxOrder();
        updateParam.setOrderNum(orderNum);
        updateParam.setOrderId(orderId);
        updateParam.setComeTime(new Date());
        updateParam.setState(1);
        this.wxOrderDao.update(updateParam);
        //将插入后的数据返回给前台
        lastOrder=this.wxOrderDao.queryLastOne(wxOrder);
        return lastOrder;
    }
    //后台操作用户到场
    @Override
    public WxOrder orderJump(WxOrder wxOrder) {

        WxOrder topOrder=this.wxOrderDao.queryTopOneByap(wxOrder);
        int orderNum=1;//获取排队号码
        int orderId=wxOrder.getOrderId();
        if(topOrder!=null){
            orderNum=topOrder.getOrderNum();
        }

        //到场分发排队号码
        WxOrder updateParam=new WxOrder();
        updateParam.setOrderNum(orderNum);
        updateParam.setOrderId(orderId);
        updateParam.setIsjump(1);
        updateParam.setArriveTime(new Date());
        this.wxOrderDao.update(updateParam);
        //将插入后的数据返回给前台
        topOrder=this.wxOrderDao.queryLastOne(wxOrder);
        return topOrder;
    }
    @Override
    public WxOrder doOrder(WxOrder wxOrder) {

        WxOrder lastOrder=this.wxOrderDao.queryLastOne(wxOrder);
        int orderNum=1;//获取排队号码
        String openid=wxOrder.getOpenid();//获取openid
        int orderType=wxOrder.getOrdertype();
        if(lastOrder!=null){
             orderNum=lastOrder.getOrderNum()+1;//获取排队号码
        }

        //开始排队
        WxOrder saveParam=new WxOrder();
        saveParam.setOrderNum(orderNum);
        saveParam.setOpenid(openid);
        saveParam.setIsuse("0");
        saveParam.setOrdertype(orderType);
        this.wxOrderDao.insert(saveParam);
        //将插入后的数据返回给前台
        lastOrder=this.wxOrderDao.queryLastOne(wxOrder);
        return lastOrder;
    }

    /**
     * 通过主键删除数据
     *
     * @param orderId 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer orderId) {
        return this.wxOrderDao.deleteById(orderId) > 0;
    }
}