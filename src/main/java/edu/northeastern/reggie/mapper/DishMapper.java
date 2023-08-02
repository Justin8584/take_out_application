package edu.northeastern.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.northeastern.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
