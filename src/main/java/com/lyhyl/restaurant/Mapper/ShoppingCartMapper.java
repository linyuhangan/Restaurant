package com.lyhyl.restaurant.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lyhyl.restaurant.entity.Dish;
import com.lyhyl.restaurant.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
