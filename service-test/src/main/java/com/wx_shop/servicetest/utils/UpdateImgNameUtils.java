package com.wx_shop.servicetest.utils;


import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateImgNameUtils {
    public static String UpdateImgName(MultipartFile file){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String fileName = file.getOriginalFilename();
        System.out.println(file.getOriginalFilename());

        String serviceImgName = "";

        File filename = null;
        try {
            InputStream is = file.getInputStream();
            filename = new File(file.getOriginalFilename());

            OutputStream os = new FileOutputStream(filename);

            int bytesRead = 0;
            byte[] buffer = new byte[104857600];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String fileRealName = file.getOriginalFilename();//获得原始文件名;
        int pointIndex =  fileRealName.lastIndexOf(".");//点号的位置
        String fileSuffix = fileRealName.substring(pointIndex);//截取文件后缀
        //修改上传后文件名称
        serviceImgName = fileRealName;

        System.out.println(filename);
        //调用7牛API上传
        ImgUtils.addImg(filename,sdf.format(new Date())+serviceImgName);

        //删除临时文件
        filename.delete();

        //返回图片路径
        return ImgUtils.publicFile(serviceImgName,"http://dt.szmlkq.com/"+sdf.format(new Date()));
    }
}
