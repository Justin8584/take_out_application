package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.dto.DishDto;
import edu.northeastern.reggie.entity.Dish;
import edu.northeastern.reggie.entity.DishFlavor;
import edu.northeastern.reggie.mapper.DishMapper;
import edu.northeastern.reggie.service.DishFlavorService;
import edu.northeastern.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * save new dish, and save flavor data as well
     */
    @Override
    @Transactional
    public void saveWithFlavor(DishDto dishDto) {

        // dish info into dish diagram
        this.save(dishDto);

        // dish ID
        Long dishID = dishDto.getId();
        List<DishFlavor> dishFlavors = dishDto.getFlavors();
        dishFlavors = dishFlavors.stream().map((item) -> {
            item.setDishId(dishID);
            return item;
        }).collect(Collectors.toList());

        // save dish flavor into flavor diagram
        dishFlavorService.saveBatch(dishFlavors);
    }

    /**
     * get dish by id & corresponding dish flavor
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {

        // get dish info - dish chart
        Dish dish = this.getById(id);
        DishDto dishDto = new DishDto();

        BeanUtils.copyProperties(dish, dishDto);

        // get dish flavor - dish flavor chart
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);

        return dishDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dishDto) {

        // update the dish info
        this.updateById(dishDto);

        // clear out dish flavor  dish_flavor delete
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);

        // update all new dish flavor dish_flavor insert
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }


}
