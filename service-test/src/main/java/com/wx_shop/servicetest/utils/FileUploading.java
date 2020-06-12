package com.wx_shop.servicetest.utils;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileUploading {
    private ReturnDiscern re =  new ReturnDiscern();
//    添加文件
    private ImgUtils imgUtils=new ImgUtils();
    @RequestMapping("/uploading")
    public Map<String, Object> ImgUploading(MultipartFile file){
        if (file==null){
            return re.ERRORMSG("文件上传失败！");
        }
        else {
            String url = UpdateImgNameUtils.UpdateImgName(file);
            return re.SUCCESSOBJ(url);
        }
    }

}
