package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.CategoryMapper;
import com.lyhyl.restaurant.Service.CategoryService;
import com.lyhyl.restaurant.Service.DishService;
import com.lyhyl.restaurant.Service.SetmealService;
import com.lyhyl.restaurant.common.CustomException;
import com.lyhyl.restaurant.entity.Category;
import com.lyhyl.restaurant.entity.Dish;
import com.lyhyl.restaurant.entity.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category>  implements CategoryService {

    @Autowired
    private DishService  dishService;
    @Autowired
    private SetmealService setmealService;

    /**
     * 根据ID删除分类，删除之前判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类ID查询、
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);

        if (count>0){
            //已关联菜品
            throw new CustomException("当前分类关联了菜品不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类ID查询、
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);

        if (count1>0){
            //已关联套餐
            throw new CustomException("当前分类关联了套餐不能删除");
        }
        //正常删除分类
        super.removeById(id);


    }
}
