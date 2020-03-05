package com.dj.ssm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dj.ssm.pojo.Car;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CarService extends IService<Car> {

    //批量新增
    void addList(List<Car> carList) throws Exception;

    //逻辑删除
    void updCarIsDel( Integer[] ids, Integer isDel) throws Exception;
}
