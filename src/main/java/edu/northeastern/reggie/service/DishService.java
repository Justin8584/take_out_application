package edu.northeastern.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.northeastern.reggie.dto.DishDto;
import edu.northeastern.reggie.entity.Dish;

public interface DishService extends IService<Dish> {

    // add new dish, and also, add new dish flavor (diagram: dish + dish flavor)
    public void saveWithFlavor(DishDto dishDto);

    // get dish by id & corresponding dish flavor
    public DishDto getByIdWithFlavor(Long id);

    // update the dish info
    public void updateWithFlavor(DishDto dishDto);

}
