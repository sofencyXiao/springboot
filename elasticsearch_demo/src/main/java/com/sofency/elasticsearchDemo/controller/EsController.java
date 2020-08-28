package com.sofency.elasticsearchDemo.controller;

import com.sofency.elasticsearchDemo.pojo.User;
import com.sofency.elasticsearchDemo.reponse.ResultVO;
import com.sofency.elasticsearchDemo.service.EsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * @author xiaohufeng
 * @date:
 */
@RestController
public class EsController {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private EsUserService esUserService;

    private String[] names = {"诸葛亮","曹操","李白","韩信","赵云","小乔","狄仁杰","李四","诸小明","王五"};
    private String[] infos = {"我来自中国的一个小乡村，地处湖南省","我来自中国的一个大城市，名叫上海，人们称作魔都"
        ,"我来自东北，家住大囤里，一口大碴子话"};

    @GetMapping("/saveUser")
    public ResultVO saveUser(){
        //批量插入到数据库　
        //添加索引mapping    索引会自动创建但mapping自只用默认的这会导致分词器不生效 所以这里我们手动导入mapping
        elasticsearchRestTemplate.putMapping(User.class);
        List<User> list = new ArrayList<>();
        for(int i=0;i<infos.length;i++){
            User user = new User();
            user.setId(Long.valueOf(i));
            user.setAge((int) (Math.random()*20));
            user.setName(names[i]);
            user.setInfo(infos[i]);
            list.add(user);
        }
        Iterable<User> iterable1 = esUserService.saveAll(list);
        return ResultVO.success(iterable1);
    }

    //根据id查询
    @GetMapping("getDataById")
    public ResultVO getDataById(Integer id){
        return  ResultVO.success(esUserService.findById(id).get());
    }

    //分页查询
    @GetMapping("/getDataByPage")
    public ResultVO getAllDataByPage(){
        Pageable page = PageRequest.of(0,10, Sort.Direction.ASC,"id");
        Page<User> all = esUserService.findAll(page);
        return ResultVO.success(all);
    }


}
