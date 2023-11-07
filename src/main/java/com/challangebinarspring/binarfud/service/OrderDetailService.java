package com.challangebinarspring.binarfud.service;

import com.challangebinarspring.binarfud.entity.OrderDetail;

import java.util.Map;

public interface OrderDetailService {
    Map save(OrderDetail orderDetail);
    Map update(OrderDetail orderDetail);
    Map delete(OrderDetail orderDetail);
    Map getById(Long orderDetail);
}
