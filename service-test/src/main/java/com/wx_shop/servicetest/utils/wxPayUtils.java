package com.wx_shop.servicetest.utils;

public class wxPayUtils {

    /**
     * 作用：统一下单<br>
     * 场景：商户在小程序中先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易后调起支付。
     * 接口链接：URL地址：https://api.mch.weixin.qq.com/pay/unifiedorder
     * 是否需要证书：否
     * 接口文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
     *
     */
//    public Map<String, String> unifiedOrder(String notify_url, String openid, String body, String out_trade_no, String total_fee, String spbill_create_ip, String goods_tag, String detail) throws Exception {
//
//        /** 构造请求参数数据 **/
//        Map<String, String> data = new HashMap<>();
//
//        // 字段名  变量名 必填  类型  示例值 描述
//        // 标价币种 fee_type    否   String(16)  CNY 符合ISO 4217标准的三位字母代码，默认人民币：CNY，详细列表请参见货币类型
//        data.put("fee_type", "CNY");
//        // 通知地址 notify_url  是   String(256) http://www.weixin.qq.com/wxpay/pay.php  异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。
//        data.put("notify_url", notify_url);
//        // 交易类型 trade_type  是   String(16)  JSAPI   小程序取值如下：JSAPI，详细说明见参数规定
//        data.put("trade_type", "JSAPI");
//        // 用户标识 openid  否   String(128) oUpF8uMuAJO_M2pxb1Q9zNjWeS6o    trade_type=JSAPI，此参数必传，用户在商户appid下的唯一标识。openid如何获取，可参考【获取openid】。
//        data.put("openid", openid);
//        // 商品描述 body    是   String(128) 腾讯充值中心-QQ会员充值 商品简单描述，该字段请按照规范传递，具体请见参数规定
//        data.put("body", body);
//        // 商户订单号    out_trade_no    是   String(32)  20150806125346  商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*且在同一个商户号下唯一。详见商户订单号
//        data.put("out_trade_no", out_trade_no);
//        // 标价金额 total_fee   是   Int 88  订单总金额，单位为分，详见支付金额
//        // 默认单位为分，系统是元，所以需要*100
//        data.put("total_fee", String.valueOf(new BigDecimal(total_fee).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).intValue()));
//        // 终端IP spbill_create_ip    是   String(16)  123.12.12.123   APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP。
//        data.put("spbill_create_ip", spbill_create_ip);
//        /** 以下参数为非必填参数 **/
//        // 订单优惠标记   goods_tag   否   String(32)  WXG 订单优惠标记，使用代金券或立减优惠功能时需要的参数，说明详见代金券或立减优惠
//        if (StringUtils.isNotBlank(goods_tag)) {
//            data.put("goods_tag", goods_tag);
//        }
//        // 商品详情 detail  否   String(6000)        商品详细描述，对于使用单品优惠的商户，改字段必须按照规范上传，详见“单品优惠参数说明”
//        if (StringUtils.isNotBlank(detail)) {
//            data.put("detail", detail);
//            // 接口版本号 新增字段，接口版本号，区分原接口，默认填写1.0。入参新增version后，则支付通知接口也将返回单品优惠信息字段promotion_detail，请确保支付通知的签名验证能通过。
//            data.put("version", "1.0");
//        }
//        // 设备号  device_info 否   String(32)  013467007045764 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB"
//        data.put("device_info", "WEB");
//
//        // 交易起始时间   time_start  否   String(14)  20091225091010  订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
//        // data.put("time_start", DateTimeUtil.getTimeShortString(timeStart));
//        // 交易结束时间   time_expire 否   String(14)  20091227091010  订单失效时间，格式为yyyyMMddHHmmss，如2009年12月27日9点10分10秒表示为20091227091010。
//        // 订单失效时间是针对订单号而言的，由于在请求支付的时候有一个必传参数prepay_id只有两小时的有效期，所以在重入时间超过2小时的时候需要重新请求下单接口获取新的prepay_id。其他详见时间规则,建议：最短失效时间间隔大于1分钟
//        // data.put("time_expire", DateTimeUtil.getTimeShortString(timeExpire));
//        /*// 商品ID   product_id  否   String(32)  12235413214070356458058 trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义。
//        data.put("product_id", null);
//        // 指定支付方式   limit_pay   否   String(32)  no_credit   上传此参数no_credit--可限制用户不能使用信用卡支付
//        data.put("limit_pay", null);
//        // 附加数据 attach  否   String(127) 深圳分店    附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
//        data.put("attach", null);*/
//
//        /** 以下五个参数，在 this.fillRequestData 方法中会自动赋值 **/
//        /*// 小程序ID  appid   是   String(32)  wxd678efh567hg6787  微信分配的小程序ID
//        data.put("appid", WXPayConstants.APP_ID);
//        // 商户号  mch_id  是   String(32)  1230000109  微信支付分配的商户号
//        data.put("mch_id", WXPayConstants.MCH_ID);
//        // 随机字符串    nonce_str   是   String(32)  5K8264ILTKCH16CQ2502SI8ZNMTM67VS    随机字符串，长度要求在32位以内。推荐随机数生成算法
//        data.put("nonce_str", nonce_str);
//        // 签名类型 sign_type   否   String(32)  MD5 签名类型，默认为MD5，支持HMAC-SHA256和MD5。
//        data.put("sign_type", WXPayConstants.MD5);
//        // 签名   sign    是   String(32)  C380BEC2BFD727A4B6845133519F3AD6    通过签名算法计算得出的签名值，详见签名生成算法
//        data.put("sign", sign);*/
//
//        // 微信统一下单接口请求地址
//        Map<String, String> resultMap = this.unifiedOrder(data);
//
//
//        return resultMap;
//    }
//
//    /**
//     * 作用：生成微信支付所需参数，微信支付二次签名<br>
//     * 场景：根据微信统一下单接口返回的 prepay_id 生成微信支付所需的参数
//     * 接口文档地址：https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_7&index=6
//     *
//     * @param prepay_id 预支付id
//     * @param nonce_str 随机字符串
//     * @return 支付方法调用所需参数map
//     * @throws Exception e
//     */
//    public Map<String, String> chooseWXPayMap(String prepay_id, String nonce_str) throws Exception {
//
//        // 支付方法调用所需参数map
//        Map<String, String> chooseWXPayMap = new HashMap<>();
//        chooseWXPayMap.put("appId", config.getAppID());
//        chooseWXPayMap.put("timeStamp", String.valueOf(WXPayUtil.getCurrentTimestamp()));
//        chooseWXPayMap.put("nonceStr", nonce_str);
//        chooseWXPayMap.put("package", "prepay_id=" + prepay_id);
//        chooseWXPayMap.put("signType", WXPayConstants.MD5);
//
//        WXPayUtil.getLogger().info("wxPay.chooseWXPayMap:" + chooseWXPayMap.toString());
//
//        // 生成支付签名
//        String paySign = WXPayUtil.generateSignature(chooseWXPayMap, config.getKey());
//        chooseWXPayMap.put("paySign", paySign);
//
//        WXPayUtil.getLogger().info("wxPay.paySign:" + paySign);
//
//        return chooseWXPayMap;
//    }


}
