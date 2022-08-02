package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.EmployeeMapper;
import com.lyhyl.restaurant.Mapper.ShoppingCartMapper;
import com.lyhyl.restaurant.Service.EmployeeService;
import com.lyhyl.restaurant.Service.ShoppingCartService;
import com.lyhyl.restaurant.common.BaseContext;
import com.lyhyl.restaurant.entity.Employee;
import com.lyhyl.restaurant.entity.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
