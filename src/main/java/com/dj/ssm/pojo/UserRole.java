package com.dj.ssm.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("user_role")
public class UserRole {

    private Integer userId;

    private Integer roleId;


}
