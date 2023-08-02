package edu.northeastern.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.northeastern.reggie.entity.ShoppingCart;
import edu.northeastern.reggie.mapper.ShoppingCartMapper;
import edu.northeastern.reggie.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {
}
