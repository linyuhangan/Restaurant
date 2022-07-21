package com.lyhyl.restaurant.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyhyl.restaurant.Mapper.CategoryMapper;
import com.lyhyl.restaurant.Service.CategoryService;
import com.lyhyl.restaurant.entity.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category>  implements CategoryService {
}
