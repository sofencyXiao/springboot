package com.sofency.uploadimage.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofency.uploadimage.utils.FTPUtils;
import com.sofency.uploadimage.utils.IDUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author sofency
 * @date 2020/3/20 11:25
 * @package IntelliJ IDEA
 * @description
 */
@Service
@Slf4j
public class NginxService {
    private FTPUtils ftpUtil;
    @Autowired
    public NginxService(FTPUtils ftpUtil) {
        this.ftpUtil =ftpUtil;
    }
    //上传成功的例子
    private Object uploadPicture(MultipartFile uploadFile) {
        //1、给上传的图片生成新的文件名
        //1.1获取原始文件名
        String oldName = uploadFile.getOriginalFilename();
        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        String newName = IDUtils.genImageName();
        assert oldName != null;
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        //2、把图片上传到图片服务器
        //2.1获取上传的io流
        InputStream input = null;
        try {
            input = uploadFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.2调用FtpUtil工具类进行上传
        return ftpUtil.putImages(input,newName);
    }

    public JSONObject upload(MultipartFile image){
        JSONObject jsonObject=null;
        String imgUrl = "";
        if(image!=null){//是用户修改图片
            jsonObject=new JSONObject();
            long begin = System.currentTimeMillis();
            Object result = this.uploadPicture(image);
            try {
                imgUrl = new ObjectMapper().writeValueAsString(result);
                imgUrl = imgUrl.substring(1,imgUrl.length()-1);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            long end = System.currentTimeMillis();
            log.info("任务结束，共耗时：[" + (end-begin) + "]毫秒");
            jsonObject.put("success", 200);
            jsonObject.put("message", "上传成功");
            jsonObject.put("url", imgUrl);
        }
        if(jsonObject==null){
            jsonObject = new JSONObject();
            jsonObject.put("error", 404);
            jsonObject.put("message", "上传失败");
            jsonObject.put("url", "");
        }
        return jsonObject;
    }
}