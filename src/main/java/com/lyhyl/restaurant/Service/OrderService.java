package com.lyhyl.restaurant.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lyhyl.restaurant.entity.DishFlavor;
import com.lyhyl.restaurant.entity.Orders;

public interface OrderService extends IService<Orders> {
    void submit(Orders orders);
}
