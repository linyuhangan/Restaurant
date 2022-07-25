package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.DishFlavorMapper;
import com.lyhyl.restaurant.Mapper.DishMapper;
import com.lyhyl.restaurant.Service.DishFlavorService;
import com.lyhyl.restaurant.Service.DishService;
import com.lyhyl.restaurant.entity.Dish;
import com.lyhyl.restaurant.entity.DishFlavor;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
