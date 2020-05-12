package com.sofency.kuang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofency.kuang.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
class KuangApplicationTests {

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name","alie");
        System.out.println(redisTemplate.opsForValue().get("name"));
//        redisTemplate.opsForHash();
//        redisTemplate.opsForList();
//        redisTemplate.opsForSet();
//        redisTemplate.opsForZSet();
//        redisTemplate.opsForGeo();
//        redisTemplate.opsForHyperLogLog();
    }
    @Test
    public void test() throws JsonProcessingException {
//        User user = new User("sofency", 18);
//        //对对象序列化处理
//        String jsonUser = new ObjectMapper().writeValueAsString(user);
//        redisTemplate.opsForValue().set("user",jsonUser);
//        System.out.println(redisTemplate.opsForValue().get("user"));
        User user1 = JSON.parseObject(String.valueOf(redisTemplate.opsForValue().get("user")), User.class);
        System.out.println(user1);
    }


}
