package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.common.CustomException;
import edu.northeastern.reggie.entity.Category;
import edu.northeastern.reggie.entity.Dish;
import edu.northeastern.reggie.entity.Setmeal;
import edu.northeastern.reggie.mapper.CategoryMapper;
import edu.northeastern.reggie.service.CategoryService;
import edu.northeastern.reggie.service.DishService;
import edu.northeastern.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * remove by id, before remove check if have connection
     * @param id
     */
    @Override
    public void remove(Long id) {

        LambdaQueryWrapper<Dish> dishQueryWrapper = new LambdaQueryWrapper<>();
        // check if connect with dish
        dishQueryWrapper.eq(Dish::getCategoryId, id);
        int countDish = (int) dishService.count(dishQueryWrapper);
        if (countDish > 0) {
            // already connect with dish, give Exception
            throw new CustomException("Have connected with Other Dishes, delete Failed ... !!! ");
        }

        LambdaQueryWrapper<Setmeal> setmealQueryWrapper = new LambdaQueryWrapper<>();
        // check if connect with setmeal
        setmealQueryWrapper.eq(Setmeal::getCategoryId, id);
        int countSetmeal = (int) setmealService.count(setmealQueryWrapper);
        if (countSetmeal > 0 ) {
            // already connect with setmeal, give exception
            throw new CustomException("Have connected with Other Setmeals, delete Failed ... !!! ");
        }

        // remove successfully
        super.removeById(id);
    }
}
