package com.sofency.elasticsearchDemo.reponse;

import com.sofency.elasticsearchDemo.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xiaohufeng
 * @date:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO {
    private Integer code;
    private String msg;


    public  static ResultVO  success(){
        return new ResultVO(200,"成功");
    }

    public  static ResultVO success(Object object){
        return new ResultVO(200,JsonUtils.toString(object));
    }

    public static ResultVO error(){
        return new ResultVO(404,"失败");
    }
}
