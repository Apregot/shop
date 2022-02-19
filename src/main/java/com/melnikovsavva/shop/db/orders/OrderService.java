package com.melnikovsavva.shop.db.orders;

import com.melnikovsavva.shop.rest.dto.order.Order;

import java.util.List;

public interface OrderService {
    void createOrder(Order order);
    Order getOrderById(Long id);
    List<Order> getSomeOrder(Integer page);
    void  updateOrderWithoutCakes(Order order);
    void updateOrder(Order order);
    List<Order> getOrderByNumber(String number, Integer page);
}
