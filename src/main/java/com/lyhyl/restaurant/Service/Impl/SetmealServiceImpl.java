package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.SetmealMapper;
import com.lyhyl.restaurant.Service.SetmealDishService;
import com.lyhyl.restaurant.Service.SetmealService;
import com.lyhyl.restaurant.common.CustomException;
import com.lyhyl.restaurant.dto.DishDto;
import com.lyhyl.restaurant.dto.SetmealDto;
import com.lyhyl.restaurant.entity.DishFlavor;
import com.lyhyl.restaurant.entity.Setmeal;
import com.lyhyl.restaurant.entity.SetmealDish;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 添加套餐
     * @param setmealDto
     */
    @Transactional
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐基本信息setmeal
        this.save(setmealDto);
        //保存套餐和菜品关联信息 setmeal_dish

        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        //Lambda表达式
        dishes = dishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(dishes);



    }
    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    public void deleteWithDish(List<Long> ids) {
        for (Long i :ids) {
            Setmeal id = this.getById(i);
            if (id.getStatus()==0){
                //删除套餐
                this.removeById(i);
                //删除套餐菜品关联信息
                LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.in(SetmealDish::getSetmealId,i);
                setmealDishService.remove(lambdaQueryWrapper);

            }else {
                throw new CustomException("当前套餐还在售卖，不能删除");
            }
        }


    }
    /**
     * 修改批量套餐起售禁售
     * @param status
     * @param ids
     */
    @Override
    public void updateStatus(Integer status, List<Long> ids) {
        for (Long i:ids) {
            Setmeal setmeal = this.getById(i);
            if (setmeal != null){
                setmeal.setStatus(status);
                this.updateById(setmeal);
            }

        }

    }
    /**
     * 根据id查询套餐信息和对应关联信息
     * @param id
     * @return
     */
    @Override
    public SetmealDto getByIdWithDish(Long id) {
        //查询基本套餐信息
        Setmeal setmeal = this.getById(id);

        SetmealDto  setmealDto = new SetmealDto();

        BeanUtils.copyProperties(setmeal,setmealDto);
        //查询关联关系
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SetmealDish::getSetmealId,setmeal.getId());
        List<SetmealDish> list = setmealDishService.list(lambdaQueryWrapper);
        setmealDto.setSetmealDishes(list);

        return setmealDto;
    }
    /**
     * 更新套餐信息
     * @param setmealDto
     */
    @Override
    @Transactional//保证一致性
    public void updatewithDish(SetmealDto setmealDto) {
        //更新setmeal表基本信息
        this.updateById(setmealDto);

        //清理当前菜品对应关联数据---setmeal_dish表的delete操作
        LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(SetmealDish::getSetmealId,setmealDto.getId());

        setmealDishService.remove(queryWrapper);

        //添加当前提交过来的关联数据---d
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();

        dishes = dishes.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(dishes);

    }

}
