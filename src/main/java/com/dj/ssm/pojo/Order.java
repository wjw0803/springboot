package com.dj.ssm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("dj_order")
public class Order {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer carId;

    private Integer borrowDay;

    private Double payMoney;

    /*租车状态1:已租车2:已结束*/
    private Integer carStatus;

    @TableField(exist = false)
    private String userNameShow;

    @TableField(exist = false)
    private String carNameShow;




}
