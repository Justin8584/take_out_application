package edu.northeastern.reggie.controller;

import edu.northeastern.reggie.common.R;
import edu.northeastern.reggie.entity.Orders;
import edu.northeastern.reggie.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * submit the order in shopping cart
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("Customer submit the order ... , {} ", orders);
        orderService.submit(orders);
        return R.success("Successfully Submit the Order ... ");
    }
}
