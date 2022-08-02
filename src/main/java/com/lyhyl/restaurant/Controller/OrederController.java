package com.lyhyl.restaurant.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyhyl.restaurant.Service.OrderDetailService;
import com.lyhyl.restaurant.Service.OrderService;
import com.lyhyl.restaurant.common.R;
import com.lyhyl.restaurant.dto.DishDto;
import com.lyhyl.restaurant.dto.OrdersDto;
import com.lyhyl.restaurant.entity.Category;
import com.lyhyl.restaurant.entity.Dish;
import com.lyhyl.restaurant.entity.OrderDetail;
import com.lyhyl.restaurant.entity.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrederController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){

        orderService.submit(orders);
        return R.success("下单成功");
    }
    @GetMapping("/userPage")
    public R<Page> page(int page,int pageSize){

        //构造分页构造器对象
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行分页查询
        orderService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,ordersDtoPage,"records");

        List<Orders> records = pageInfo.getRecords();

        List<OrdersDto> list = records.stream().map((item) -> {
            OrdersDto ordersDto = new OrdersDto();

            BeanUtils.copyProperties(item, ordersDto);
            Long Id = item.getId();

            //根据id查分类对象
            Orders orders = orderService.getById(Id);
            String number = orders.getNumber();
            LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper=new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(OrderDetail::getOrderId,number);
            List<OrderDetail> orderDetailList = orderDetailService.list(lambdaQueryWrapper);

            int num=0;

            for(OrderDetail l:orderDetailList){
                num+=l.getNumber().intValue();
            }
            ordersDto.setSumNum(num);
            return ordersDto;
        }).collect(Collectors.toList());


        ordersDtoPage.setRecords(list);

        return R.success(ordersDtoPage);

    }
}
