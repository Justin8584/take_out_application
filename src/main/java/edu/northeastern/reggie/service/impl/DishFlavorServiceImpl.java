package edu.northeastern.reggie.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.entity.DishFlavor;
import edu.northeastern.reggie.mapper.DishFlavorMapper;
import edu.northeastern.reggie.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
