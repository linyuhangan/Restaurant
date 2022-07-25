package com.lyhyl.restaurant.dto;


import com.lyhyl.restaurant.entity.Setmeal;
import com.lyhyl.restaurant.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
