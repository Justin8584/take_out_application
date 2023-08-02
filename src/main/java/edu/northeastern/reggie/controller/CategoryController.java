package edu.northeastern.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.northeastern.reggie.common.R;
import edu.northeastern.reggie.entity.Category;
import edu.northeastern.reggie.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Category Controller
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Create new category
     * @param category
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
//        log.info("Category: {} ... !!! ", category);
        categoryService.save(category);
        return R.success("Successfully add new Category. ");
    }

    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {

        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Category> lambdaQueryWrapper = new LambdaQueryWrapper<>();

        // add new sorting condition,
        lambdaQueryWrapper.orderByAsc(Category::getSort);

        categoryService.page(pageInfo, lambdaQueryWrapper);
        return R.success(pageInfo);
    }

    /**
     * Delete Category, based on id
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id) {
//        log.info("Delete , ID: {} ", id);

        categoryService.remove(id);
        return R.success("Successfully Delete Category !");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category) {
        categoryService.updateById(category);
        return R.success("Successfully Updated !");
    }

    /**
     * list the categary data by conditions
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {

        // constructor
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        // add condition, if type in category is not null
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        // sort condition
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
