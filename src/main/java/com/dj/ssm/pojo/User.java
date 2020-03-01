package com.dj.ssm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

@Data
@Accessors(chain = true)
@TableName("dj_user")
public class User {


    @TableId(type = IdType.AUTO)
    private Integer id;

    private String userName;

    private String pwd;

    private String email;

    private String phone;

    private String salt;

    /*激活状态0:未激活1:已激活*/
    private String isActive;

    /*0:普通用户1:vip2:管理员*/
    private Integer level;

    /*0:日/vip,月/vip,年/vip*/
    private Integer vipType;

    private String vipVolidateTime;

    @TableField(exist = false)
    private String userPhoneShow;

    public String getUserPhoneShow() {
        if(StringUtils.isEmpty(phone)){
            return "无";
        }
        return phone.replace(phone.substring(3,7),"****");
    }
}
