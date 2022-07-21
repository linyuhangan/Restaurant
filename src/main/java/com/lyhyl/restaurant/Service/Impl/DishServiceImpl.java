package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.DishMapper;
import com.lyhyl.restaurant.Service.DishService;
import com.lyhyl.restaurant.entity.Dish;
import org.springframework.stereotype.Service;

@Service
public class DishServiceImpl  extends ServiceImpl<DishMapper, Dish> implements DishService {
}
