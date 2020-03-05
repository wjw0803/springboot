package com.dj.ssm.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.StringUtils;

import java.util.Date;

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date vipVolidateTime;

    @TableField(exist = false)
    private String userPhoneShow;

    public void setUserPhoneShow(String userPhoneShow) {
        this.userPhoneShow = userPhoneShow;
    }

    public String getUserPhoneShow() {
        if(StringUtils.isEmpty(phone)){
            return "无";
        }
        return phone.replace(phone.substring(3,7),"****");
    }

    @TableField(exist = false)
    private String levelShow;

    public String getLevelShow() {
        if(StringUtils.isEmpty(level)){
            return "无";
        }
        if(level == 0){
            return "普通用户";
        }
        if(level ==1 &&  System.currentTimeMillis() < vipVolidateTime.getTime() ){
            return "vip";
        }else if(level == 2){
            return "管理员";
        }
        return levelShow;
    }

    public String getPhone() {
        return phone;
    }

    private String payPwd;

    /*账户余额*/
    private Double accountMoney;


    /*是否领取新人福利 0:未领取 1:已领取 */
    private Integer isGetMoney;

    /*短信验证码*/
    private String code;

    /*失效时间*/
    private Date validateCodeTime;

}
