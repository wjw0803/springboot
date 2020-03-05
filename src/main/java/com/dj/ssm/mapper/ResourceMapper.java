package com.dj.ssm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.ssm.pojo.Resource;
import org.springframework.dao.DataAccessException;

import javax.xml.crypto.Data;
import java.util.List;

public interface ResourceMapper extends BaseMapper<Resource> {

    //查找用户已关联的资源
    List<Resource> findUserResource(Integer userId) throws DataAccessException;

}
