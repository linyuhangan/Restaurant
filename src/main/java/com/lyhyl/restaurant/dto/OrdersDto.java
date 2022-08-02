package com.lyhyl.restaurant.dto;


import com.lyhyl.restaurant.entity.OrderDetail;
import com.lyhyl.restaurant.entity.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private int sumNum;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
