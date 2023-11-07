package com.challangebinarspring.binarfud.service;

import com.challangebinarspring.binarfud.entity.Order;

import java.util.Map;

public interface OrderService {
    Map save(Order order);
    Map update(Order order);
    Map delete(Order order);
    Map getById(Long order);
}
