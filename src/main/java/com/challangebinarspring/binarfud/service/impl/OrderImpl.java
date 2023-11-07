package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.Config;
import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.repository.UserRepository;
import com.challangebinarspring.binarfud.service.OrderService;
import com.challangebinarspring.binarfud.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class OrderImpl implements OrderService {

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public Response response;

    @Override
    public Map save(Order order) {
       /* Map map = new HashMap<>();
        Order doSave = orderRepository.save(order);

        map.put("Data", doSave);
        map.put("message","Success");
        map.put("status",200);

        return map;*/
        try{
            log.info("Save Order");

            if (order.getUser() == null && order.getUser().getId() == null){
                return response.errorResponse(Config.USER_REQUIRED);
            }
            Optional<User> checkUser = userRepository.findById(order.getUser().getId());
            if (checkUser.isEmpty()){
                return response.errorResponse(Config.USER_NOT_FOUND);
            }

            order.setUser(checkUser.get());
            return response.successResponse(orderRepository.save(order));
        } catch (Exception e){
            log.error("Save Order Error: " + e.getMessage());
            return response.errorResponse("Save Order Error: " + e.getMessage());
        }

    }

    @Override
    public Map update(Order order) {
        try{
            log.info("Update Order");

            if (order.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<Order> checkOrder = orderRepository.findById(order.getId());
            if (checkOrder.isEmpty()){
                return response.errorResponse(Config.ORDER_NOT_FOUND);
            }

            if (order.getUser() == null && order.getUser().getId() == null){
                return response.errorResponse(Config.USER_REQUIRED);
            }
            Optional<User> checkUser = userRepository.findById(order.getUser().getId());
            if (checkUser.isEmpty()){
                return response.errorResponse(Config.USER_NOT_FOUND);
            }

            checkOrder.get().setDestinationAddress(order.destinationAddress);
            checkOrder.get().setOrderTime(order.orderTime);
            checkOrder.get().setComplated(order.complated);

            return response.successResponse(orderRepository.save(checkOrder.get()));
        }catch (Exception e){
            log.error("Update Order Error: " + e.getMessage());
            return response.errorResponse("Update Order Error: " + e.getMessage());
        }
    }

    @Override
    public Map delete(Order order) {
        try{
            log.info("Delete Order");

            if (order.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<Order> checkOrder = orderRepository.findById(order.getId());
            if (checkOrder.isEmpty()){
                return response.errorResponse(Config.ORDER_NOT_FOUND);
            }

            checkOrder.get().setDeleted_date(new Date());
            return response.successResponse(Config.DELETE_SUCCESS);
        } catch (Exception e){
            log.error("Delete Order Error: " + e.getMessage());
            return response.errorResponse("Delete Order Error: " + e.getMessage());
        }
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
