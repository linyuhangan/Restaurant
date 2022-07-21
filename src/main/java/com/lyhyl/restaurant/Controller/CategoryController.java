package com.lyhyl.restaurant.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lyhyl.restaurant.Service.CategoryService;
import com.lyhyl.restaurant.common.R;
import com.lyhyl.restaurant.entity.Category;
import com.lyhyl.restaurant.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

   @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param category
     * @return
     */
    @PostMapping
   public R<String> save(@RequestBody Category category){

       categoryService.save(category);

       return R.success("新添分类成功");
   }

    /**
     * 菜品信息查询分页
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public  R<Page> page(int page,int pageSize){
        //分页构造器
        Page page1 = new Page(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        //排序条件,根据sort排序
        lambdaQueryWrapper.orderByDesc(Category::getSort);

        //执行查询
       categoryService.page(page1,lambdaQueryWrapper);

        return R.success(page1);
    }

    /**
     * 分类信息删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long ids){
        log.info("删除分类为：{}",ids);

        categoryService.removeById(ids);

        return R.success("分类信息删除成功");
    }
}
