package com.dj.ssm.pojo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("resource")
public class Resource {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String resourceName;

    private String url;

    private Integer pId;

    /*资源类型 1:菜单2:按钮*/
    private Integer resourceType;

    public Integer getpId() {
        return pId;
    }
}
