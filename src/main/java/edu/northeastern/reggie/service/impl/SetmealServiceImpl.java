package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.common.CustomException;
import edu.northeastern.reggie.dto.SetmealDto;
import edu.northeastern.reggie.entity.Setmeal;
import edu.northeastern.reggie.entity.SetmealDish;
import edu.northeastern.reggie.mapper.SetmealMapper;
import edu.northeastern.reggie.service.SetmealDishService;
import edu.northeastern.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * save the new meal choice, and save the meal and dishes relationship
     * @param setmealDto
     */
    @Override
    @Transactional
    public void saveWithDish(SetmealDto setmealDto) {

        // save meal info, set meal, and insert meal
        this.save(setmealDto);

        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList.stream().map( (item) -> {
            item.setSetmealId(setmealDto.getId() );
            return item;
        }).collect(Collectors.toList());

        // save the relationship with meal and dishes
        setmealDishService.saveBatch(setmealDishList);
    }

    // delete dish by ids
    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {

        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus, 1);

        int count = (int) this.count(queryWrapper);
        // make sure the deletable dish
        if (count > 0) {
            // cannot delete
            throw new CustomException("The meal is in selling, cannot delete ! ");
        }
        // deletable, then delete setmeal
        this.removeByIds(ids);

        // delete record in database setmeal-dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);

        setmealDishService.remove(lambdaQueryWrapper);
    }
}
