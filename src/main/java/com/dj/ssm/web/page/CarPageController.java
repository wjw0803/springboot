package com.dj.ssm.web.page;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dj.ssm.pojo.Car;
import com.dj.ssm.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.ConditionalOnRepositoryType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/car/")
public class CarPageController {

    @Autowired
    private CarService carService;

    //去租车展示页面
    @RequestMapping("borCar/{id}")
    public String borCar(@PathVariable Integer id, Model model){
        //根据id查询该车的信息
        QueryWrapper<Car> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        Car car = carService.getOne(queryWrapper);
        model.addAttribute("car",car);
        return "/user/bor_car";
    }

    //去车辆展示页面
    @RequestMapping("toShow")
    public String toShow(){
        return "/car/show";
    }


    //去批量新增页面
    @RequestMapping("toAdds")
    public String toAdds(){
        return "/car/adds";
    }

}
