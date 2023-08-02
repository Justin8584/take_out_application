package edu.northeastern.reggie.dto;

import edu.northeastern.reggie.entity.Setmeal;
import edu.northeastern.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
