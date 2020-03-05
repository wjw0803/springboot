package com.dj.ssm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@TableName("dj_car")
public class Car {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String carName;

    /*0:家用车1:豪华车2:跑车*/
    private Integer carType;

    private Double money;

    /*商品上下架 0:下架 1:上架*/
    private Integer status;

    /*是否删除 0:未删除 1:已删除*/
    private  Integer isDel;

    @TableField(exist = false)
    private List<Car> carList;



}
