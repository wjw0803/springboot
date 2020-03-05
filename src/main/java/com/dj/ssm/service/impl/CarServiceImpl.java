package com.dj.ssm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dj.ssm.mapper.CarMapper;
import com.dj.ssm.pojo.Car;
import com.dj.ssm.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
public class CarServiceImpl extends ServiceImpl<CarMapper, Car> implements CarService {

    @Autowired
    private CarMapper carMapper;

    @Override
    public void addList(List<Car> carList) throws Exception {
        carMapper.addList(carList);
    }

    @Override
    public void updCarIsDel(Integer[] ids, Integer isDel) throws Exception {
        carMapper.updCarIsDel(ids,isDel);
    }
}
