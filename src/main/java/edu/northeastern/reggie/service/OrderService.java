package edu.northeastern.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import edu.northeastern.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    public void submit(Orders orders);
}
