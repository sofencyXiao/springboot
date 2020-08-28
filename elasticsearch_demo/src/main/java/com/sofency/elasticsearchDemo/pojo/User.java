package com.sofency.elasticsearchDemo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author sofency
 * @date 2020/4/25 7:15
 * @package IntelliJ IDEA
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user",type = "user")
public class User {
    @Id
    private Long id;

//    @Field(type= FieldType.Auto)//自动检测类型
    private Integer age;

//    @Field(type=FieldType.Keyword)
    private String name;

    @Field(type=FieldType.Text,analyzer = "ik_smart",searchAnalyzer = "ik_max_word")//z设置为text可以进行分词
    private String info;

}
