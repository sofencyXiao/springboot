package com.sofency.uploadimage.controller;

import com.alibaba.fastjson.JSONObject;
import com.sofency.uploadimage.service.NginxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author sofency
 * @date 2020/5/10 9:51
 * @package IntelliJ IDEA
 * @description
 */
@RestController
@Slf4j
public class NginxController {

    private NginxService nginxService;
    @Autowired
    public NginxController(NginxService nginxService){
        this.nginxService=nginxService;
    }
    /**
     * 可上传图片、视频，只需在nginx配置中配置可识别的后缀
     */
    @PostMapping("/upload")
    public JSONObject pictureUpload(@RequestParam(value = "image",required = false) MultipartFile image) {
        //根据id查找到用户的信息 并且修改图片的url
        JSONObject upload = nginxService.upload(image);
        return upload;
    }
}