package com.wx_shop.servicetest.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class ImgUtils {


    public static void addImg(File filename, String serviceImgName) {

        /**基本配置-从七牛管理后台拿到*/
        //设置好账号的ACCESS_KEY和SECRET_KEY
        String ACCESS_KEY = "mmGVYJJT6t0o72S8fUiveNF7KRGBhd59BYb3nAuF";
        String SECRET_KEY = "f0GhytEXX79ZWdsV4CVBdHJeYel-pQqJ-pAG5HXT";
        //要上传的空间名--
        String bucketname = "dtkq";

        String accessKey = "mmGVYJJT6t0o72S8fUiveNF7KRGBhd59BYb3nAuF";
        String secretKey = "f0GhytEXX79ZWdsV4CVBdHJeYel-pQqJ-pAG5HXT";
        String bucket = "dtkq";
        /**指定保存到七牛的文件名--同名上传会报错  {"error":"file exists"}*/
        /** {"hash":"FrQF5eX_kNsNKwgGNeJ4TbBA0Xzr","key":"aa1.jpg"} 正常返回 key为七牛空间地址 http:/xxxx.com/aa1.jpg */
        //上传文件的路径
//        String FilePath = filename.getAbsolutePath();
        System.out.println("---------" + filename.getAbsolutePath() + "----------");
        //上传到七牛后保存的文件名    访问为：http://oswj11a86.bkt.clouddn.com/daimo6.png
        String key = serviceImgName;
        System.out.println(key);
        //密钥配置
        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

        //获取上传凭证
        String upToken = auth.uploadToken(bucket);
//        //创建上传对象
//        UploadManager uploadManager =new UploadManager(new Configuration());
//      构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);
        //简单上传
        StringMap putPolicy = new StringMap();
        putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
        putPolicy.put("callbackBody", "key=$(key)&hash=$(etag)&bucket=$(bucket)&fsize=$(fsize)");
        long expireSeconds = 36000;
//        try {
//            //调用put方法上传
//
//            Response res = uploadManager.put(FilePath, key, auth.uploadToken(bucketname));
//            //打印返回的信息
//            System.out.println(res.bodyString());
//            System.out.println(res.statusCode);//200为上传成功
//        } catch (QiniuException e) {
//            Response r = e.response;
//            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
//            try {
//                //响应的文本信息
//                System.out.println(r.bodyString());
//            } catch (QiniuException e1) {
//                //ignore
//            }
//        }

        try {
            Response response = uploadManager.put(filename, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

        public static String publicFile(String fileName,String domainOfBucket) {
        String encodedFileName=null;
        try {
            encodedFileName = URLEncoder.encode(fileName,"utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String finalUrl = String.format("%s/%s", domainOfBucket,encodedFileName );
        System.out.println(finalUrl);
        return finalUrl;
    }



//
//
//    /**基本配置-从七牛管理后台拿到*/
//    //设置好账号的ACCESS_KEY和SECRET_KEY
//    String ACCESS_KEY = "mmGVYJJT6t0o72S8fUiveNF7KRGBhd59BYb3nAuF";
//    String SECRET_KEY = "f0GhytEXX79ZWdsV4CVBdHJeYel-pQqJ-pAG5HXT";
//    //要上传的空间名--
//    String bucketname = "mylikekq";
//
//    private static String accessKey = "mmGVYJJT6t0o72S8fUiveNF7KRGBhd59BYb3nAuF";
//    private static String secretKey = "f0GhytEXX79ZWdsV4CVBdHJeYel-pQqJ-pAG5HXT";
//    private static String bucket = "mylikekq";
//    /**
//     * 获取上传凭证
//     */
//    public static String getUploadCredential() {
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//        System.out.println(upToken);
//        return upToken;
//    }
//
//    /**指定保存到七牛的文件名--同名上传会报错  {"error":"file exists"}*/
//    /** {"hash":"FrQF5eX_kNsNKwgGNeJ4TbBA0Xzr","key":"aa1.jpg"} 正常返回 key为七牛空间地址 http:/xxxx.com/aa1.jpg */
//    //上传文件的路径
//    String FilePath ="D:\\132.MP4";
//    //上传到七牛后保存的文件名    访问为：http://oswj11a86.bkt.clouddn.com/daimo6.png
//    String key = "video/11111111.mp4";
//
//    //密钥配置
//    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
//    //创建上传对象
//    UploadManager uploadManager =new UploadManager(new Configuration());
//
//    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
//    public String getUpToken(){
//        StringMap putPolicy = new StringMap();
//        putPolicy.put("callbackUrl", "http://api.example.com/qiniu/upload/callback");
//        putPolicy.put("callbackBody", "key=$(key)&hash=$(etag)&bucket=$(bucket)&fsize=$(fsize)");
//        long expireSeconds = 3600;
//        String upToken = auth.uploadToken(bucketname, null, expireSeconds, putPolicy);
//        System.out.println(upToken);
//        return auth.uploadToken(bucketname);
//    }
//    public void upload() throws IOException {
//        try {
//            //调用put方法上传
//
//            Response res = uploadManager.put(FilePath, key, getUpToken());
//            //打印返回的信息
//            System.out.println(res.bodyString());
//            System.out.println(res.statusCode);//200为上传成功
//        } catch (QiniuException e) {
//            Response r = e.response;
//            // 请求失败时打印的异常的信息
//            System.out.println(r.toString());
//            try {
//                //响应的文本信息
//                System.out.println(r.bodyString());
//            } catch (QiniuException e1) {
//                //ignore
//            }
//        }
//    }
//
//    public static Auth getAuth() {
//        return Auth.create(accessKey, secretKey);
//    }
//
//    /**
//     * 公有空间返回文件URL
//     * @param fileName
//     * @param domainOfBucket
//     * @return
//     */
}