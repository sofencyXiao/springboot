package com.sofency.elasticsearch_demo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author sofency
 * @date 2020/4/25 7:15
 * @package IntelliJ IDEA
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String name;
    private int age;
}
