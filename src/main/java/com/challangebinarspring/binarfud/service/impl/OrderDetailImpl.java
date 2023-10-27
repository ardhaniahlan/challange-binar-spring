package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.entity.OrderDetail;
import com.challangebinarspring.binarfud.repository.OrderDetailRepository;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    public OrderDetailRepository orderDetailRepository;

    @Override
    public Map save(OrderDetail orderDetail) {
        Map map = new HashMap<>();
        OrderDetail doSave = orderDetailRepository.save(orderDetail);

        map.put("Data", doSave);
        map.put("message","Success");
        map.put("status",200);

        return map;
    }

    @Override
    public Map update(OrderDetail orderDetail) {

        Map map = new HashMap<>();
        OrderDetail cekData = orderDetailRepository.getById(orderDetail.getId());
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setQuantity(orderDetail.quantity);
        cekData.setTotalPrice(orderDetail.totalPrice);

        OrderDetail doUpdate = orderDetailRepository.save(cekData);
        map.put("Data", doUpdate);
        return map;
    }

    @Override
    public Map delete(Long orderDetail) {
        Map map = new HashMap<>();
        OrderDetail cekData = orderDetailRepository.getById(orderDetail);
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setDeleted_date(new Date());

        OrderDetail doDelete = orderDetailRepository.save(cekData);
        map.put("Data", doDelete);
        return map;
    }

    @Override
    public Map getById(Long orderDetail) {
        Map map = new HashMap<>();
        Optional<OrderDetail> getBaseOptional = orderDetailRepository.findById(orderDetail);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }
}
