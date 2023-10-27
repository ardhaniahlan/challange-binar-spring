package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderImpl implements OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Override
    public Map save(Order order) {
        Map map = new HashMap<>();
        Order doSave = orderRepository.save(order);

        map.put("Data", doSave);
        map.put("data",doSave);
        map.put("message","Success");
        map.put("status",200);

        return map;
    }

    @Override
    public Map update(Order order) {
        Map map = new HashMap<>();
        Order cekData = orderRepository.getById(order.getId());
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setDestinationAddress(order.destinationAddress);
        cekData.setOrderTime(order.orderTime);
        cekData.setComplated(order.complated);

        Order doUpdate = orderRepository.save(cekData);
        map.put("Data", doUpdate);
        return map;
    }

    @Override
    public Map delete(Long order) {
        Map map = new HashMap<>();
        Order cekData = orderRepository.getById(order);
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setDeleted_date(new Date());

        Order doDelete = orderRepository.save(cekData);
        map.put("Data", doDelete);
        return map;
    }

    @Override
    public Map getById(Long order) {
        Map map = new HashMap<>();
        Optional<Order> getBaseOptional = orderRepository.findById(order);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }
}
