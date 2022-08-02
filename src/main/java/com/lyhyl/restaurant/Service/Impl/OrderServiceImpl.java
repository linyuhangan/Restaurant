package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.DishFlavorMapper;
import com.lyhyl.restaurant.Mapper.OrderMapper;
import com.lyhyl.restaurant.Service.*;
import com.lyhyl.restaurant.common.BaseContext;
import com.lyhyl.restaurant.common.CustomException;
import com.lyhyl.restaurant.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {
    @Autowired
    private ShoppingCartService  shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 用户下单
     * @param orders
     */
    @Transactional
    @Override
    public void submit(Orders orders) {
        //获得当前用户ID
        Long id = BaseContext.getCurrentId();
        //查询当前购物车数据
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,id);
        List<ShoppingCart> cartList = shoppingCartService.list(lambdaQueryWrapper);
        if (cartList==null || cartList.size() == 0){
            throw new CustomException("购物车为空，不能下单");
        }
        //查询用户数据
        User user= userService.getById(id);
        //查询地址数据
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook==null){
            throw new CustomException("地址为空，不能下单");
        }

        //订单表一条数据

        long orderId = IdWorker.getId();//订单号

        AtomicInteger amount=new AtomicInteger(0);

        List<OrderDetail> orderDetails=cartList.stream().map((item)->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());


        //向订单表中插入一条数据
        orders.setNumber(String.valueOf(orderId));
        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//计算总金额
        orders.setUserId(id);
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName()==null?"":addressBook.getProvinceName())
                +(addressBook.getCityName()==null?"":addressBook.getCityName())
                +(addressBook.getDistrictName()==null?"":addressBook.getDistrictName())
                +(addressBook.getDetail()==null?"":addressBook.getDetail()));
        this.save(orders);
        //订单明细表，多条数据
        orderDetailService.saveBatch(orderDetails);
        //清空购物车数据
        shoppingCartService.remove(lambdaQueryWrapper);

    }
}
