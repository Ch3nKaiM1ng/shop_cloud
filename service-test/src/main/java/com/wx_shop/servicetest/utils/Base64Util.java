package com.wx_shop.servicetest.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;

import java.io.*;
import java.util.Date;

public class Base64Util {
    /**
     * 本地图片转换成base64字符串
     * @param imgFile	本地图片全路径 （注意：带文件名）
     *  (将图片文件转化为字节数组字符串，并对其进行Base64编码处理)
     * @return
     */
    public static String ImageToBase64ByLocal(String imgFile) {


        byte[] data = null;

        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFile);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 返回Base64编码过的字节数组字符串
        return Base64Utils.encodeToString(data);
    }




    /**
     * base64字符串转换成图片 (对字节数组字符串进行Base64解码并生成图片)
     * @param imgStr		base64字符串
     * @return
     */
    public static String Base64ToImage(String imgStr) {
        //图片储存路径
        String path="/www/web/smallprogram_admin/public_html/static/img/";
        Date now=new Date();
        //图片名称
        String fileName=Long.toString(now.getTime()/1000)+".npg";
        String imgFilePath=path+fileName;
        String readPath="https://wxadmin.szmlkq.com/static/img/"+fileName;
        if (StringUtils.isEmpty(imgStr)) // 图像数据为空
            return null;

        try {
            // Base64解码
            byte[] b = Base64Utils.decodeFromString(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            return readPath;
        } catch (Exception e) {
            return null;
        }

    }
}
