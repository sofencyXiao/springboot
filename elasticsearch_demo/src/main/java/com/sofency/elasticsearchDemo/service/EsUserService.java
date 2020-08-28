package com.sofency.elasticsearchDemo.service;

import com.sofency.elasticsearchDemo.pojo.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaohufeng
 * @date:
 */
@Service
public interface EsUserService extends ElasticsearchRepository<User,Integer> {
//    //根据名字查询
//    List<User> findByName(String name);
//
//    //根据name和info进行查询
//    List<User> findByNameAnd(String name,String info);
}
