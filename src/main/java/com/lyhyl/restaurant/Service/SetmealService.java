package com.lyhyl.restaurant.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyhyl.restaurant.dto.DishDto;
import com.lyhyl.restaurant.dto.SetmealDto;
import com.lyhyl.restaurant.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //添加菜品
    public void saveWithDish(SetmealDto setmealDto);
    //删除菜品
    public void deleteWithDish(List<Long> ids);
    //批量起售禁售
    public void updateStatus(Integer status, List<Long> ids);
    //根据id查询套餐信息和对应关联信息
    public SetmealDto getByIdWithDish(Long id);
    //更新信息
    public void updatewithDish(SetmealDto setmealDto);
}
