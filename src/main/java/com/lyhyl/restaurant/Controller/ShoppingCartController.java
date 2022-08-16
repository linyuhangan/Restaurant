package com.lyhyl.restaurant.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyhyl.restaurant.Service.ShoppingCartService;
import com.lyhyl.restaurant.common.BaseContext;
import com.lyhyl.restaurant.common.R;
import com.lyhyl.restaurant.entity.ShoppingCart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("shoppingCart:{}",shoppingCart);
        //设置用户id
        shoppingCart.setUserId(BaseContext.getCurrentId());

        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        if (shoppingCart.getDishId() != null){
            //添加的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());
        }else {
            //添加的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart serviceOne = shoppingCartService.getOne(lambdaQueryWrapper);
        //查询菜品或套餐是否已存在
        if (serviceOne != null){
            //已存在，数量+1
            Integer number = serviceOne.getNumber();
            serviceOne.setNumber(number + 1);
            shoppingCartService.updateById(serviceOne);

        }else {
            //添加到购物车
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            serviceOne = shoppingCart;

        }
        return R.success(serviceOne);
    }

    /**
     * 查看购物车
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        Long id = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,id);
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> shoppingCartList = shoppingCartService.list(lambdaQueryWrapper);
        return R.success(shoppingCartList);
    }

    /**
     * 清空购物车
     * @return
     */
    @DeleteMapping("/clean")
    public R<String> clean(){
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        shoppingCartService.remove(lambdaQueryWrapper);

        return R.success("清空成功");
    }
    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        log.info("shoppingCart:{}",shoppingCart);
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        if (shoppingCart.getDishId() != null){
            //减少的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId,shoppingCart.getDishId());

        }else {
            //减少的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart serviceOne = shoppingCartService.getOne(lambdaQueryWrapper);
        if (serviceOne != null && serviceOne.getNumber()>0) {
            serviceOne.setNumber(serviceOne.getNumber() - 1);
            shoppingCartService.updateById(serviceOne);
        }else {
            shoppingCartService.removeById(serviceOne.getId());
        }
        return R.success(serviceOne);
    }
}






















