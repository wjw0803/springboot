package com.dj.ssm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.ssm.pojo.Resource;

import java.util.List;

public interface ResourceService extends IService<Resource> {


    //查找用户已关联的资源
    List<Resource> findUserResource(Integer userId) throws Exception;
}
