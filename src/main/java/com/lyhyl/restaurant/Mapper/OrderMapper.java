package com.lyhyl.restaurant.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyhyl.restaurant.entity.Dish;
import com.lyhyl.restaurant.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
