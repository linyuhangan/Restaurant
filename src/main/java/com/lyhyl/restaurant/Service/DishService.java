package com.lyhyl.restaurant.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyhyl.restaurant.dto.DishDto;
import com.lyhyl.restaurant.entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时保存口味数据
    public void savewithFlavor(DishDto dishDto);
    //根据id查询菜品信息和对应口味信息
    public  DishDto getByIdWithFlavor(Long id);
    //更新信息
    public void updatewithFlavor(DishDto dishDto);
}
