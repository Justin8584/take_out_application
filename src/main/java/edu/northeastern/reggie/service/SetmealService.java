package edu.northeastern.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.northeastern.reggie.dto.SetmealDto;
import edu.northeastern.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {

    // save the new meal choice, and save the meal and dishes relationship
    public void saveWithDish(SetmealDto setmealDto);

    // delete dish by ids
    public void removeWithDish(List<Long> ids);
}
