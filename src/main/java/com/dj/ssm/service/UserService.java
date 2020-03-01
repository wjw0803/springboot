package com.dj.ssm.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.ssm.pojo.User;

public interface UserService extends IService<User> {

    IPage<User> getAllRoles(Page<User> page, QueryWrapper queryWrapper);


}
