package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lyhyl.restaurant.Mapper.OrderDetailMapper;
import com.lyhyl.restaurant.Service.OrderDetailService;
import com.lyhyl.restaurant.entity.OrderDetail;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}