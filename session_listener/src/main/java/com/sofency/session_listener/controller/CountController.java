package com.sofency.session_listener.controller;

import com.sofency.session_listener.config.SessionListenerCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author sofency
 * @date 2020/5/12 11:40
 * @package IntelliJ IDEA
 * @description
 */
@Controller
public class CountController {
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public CountController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    //统计在线人数的demo
    @RequestMapping("/login")
    @ResponseBody
    public String login(String userName, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(true);
        httpSession.setAttribute(userName, userName);
        AtomicInteger userCount = SessionListenerCount.atomicInteger;
        redisTemplate.opsForValue().set("people",userCount);
        return userName + "上线成功！当前在线人数： " + userCount;
    }

    @RequestMapping("/logout")
    @ResponseBody
    public String logout(String userName, HttpServletRequest request) {
        HttpSession httpSession = request.getSession(true);
        httpSession.removeAttribute(userName);
        httpSession.invalidate();
        AtomicInteger userCount = SessionListenerCount.atomicInteger;
        redisTemplate.opsForValue().set("people",userCount);
        return userName + "下线成功！当前在线人数：" + userCount;
    }

    //通过redis获取在线人数
}
