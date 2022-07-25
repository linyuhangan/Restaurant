package com.lyhyl.restaurant.dto;


import com.lyhyl.restaurant.entity.Dish;
import com.lyhyl.restaurant.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
