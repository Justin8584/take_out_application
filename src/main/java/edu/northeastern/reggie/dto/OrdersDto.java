package edu.northeastern.reggie.dto;

import edu.northeastern.reggie.entity.OrderDetail;
import edu.northeastern.reggie.entity.Orders;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private int sumNum;

    private List<OrderDetail> orderDetails;
	
}
