package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.SetmealMapper;
import com.lyhyl.restaurant.Service.SetmealService;
import com.lyhyl.restaurant.entity.Setmeal;
import org.springframework.stereotype.Service;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
