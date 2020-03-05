package com.dj.ssm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dj.ssm.pojo.Car;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface CarMapper extends BaseMapper<Car> {

    //批量新增
    void addList(List<Car> carList) throws DataAccessException;

    //逻辑删除
    void updCarIsDel(@Param("ids") Integer[] ids, @Param("isDel") Integer isDel) throws  DataAccessException;


}
