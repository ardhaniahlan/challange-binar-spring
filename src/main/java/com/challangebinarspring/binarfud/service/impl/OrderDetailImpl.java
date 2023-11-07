package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.Config;
import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.entity.OrderDetail;
import com.challangebinarspring.binarfud.entity.Product;
import com.challangebinarspring.binarfud.repository.OrderDetailRepository;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.repository.ProductRepository;
import com.challangebinarspring.binarfud.service.OrderDetailService;
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
public class OrderDetailImpl implements OrderDetailService {

    @Autowired
    public OrderDetailRepository orderDetailRepository;

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public Response response;

    @Override
    public Map save(OrderDetail orderDetail) {
        try{
            log.info("Save Order Detail");

            if (orderDetail.getQuantity() == 0){
                return response.errorResponse("Quantity not 0");
            }

            if (orderDetail.getOrder() == null && orderDetail.getOrder().getId() == null){
                return response.errorResponse(Config.ORDER_REQUIRED);
            }
            Optional<Order> checkOrder = orderRepository.findById(orderDetail.getOrder().getId());
            if (checkOrder.isEmpty()){
                return response.errorResponse(Config.ORDER_NOT_FOUND);
            }

            if (orderDetail.getProduct() == null && orderDetail.getProduct().getId() == null){
                return response.errorResponse(Config.PRODUCT_REQUIRED);
            }
            Optional<Product> checkProduct = productRepository.findById(orderDetail.getProduct().getId());
            if (checkProduct.isEmpty()){
                return response.errorResponse(Config.PRODUCT_NOT_FOUND);
            }

            orderDetail.setOrder(checkOrder.get());
            orderDetail.setProduct(checkProduct.get());
            return response.successResponse(orderDetailRepository.save(orderDetail));

        } catch (Exception e){
            log.error("Save Order Detail Error: " + e.getMessage());
            return response.errorResponse("Save Order Detail Error: " + e.getMessage());
        }

    }

    @Override
    public Map update(OrderDetail orderDetail) {
        try{
            log.info("Update Order Detail");

            if (orderDetail.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<OrderDetail> checkOrderDetail = orderDetailRepository.findById(orderDetail.getOrder().getId());
            if (checkOrderDetail.isEmpty()){
                return response.errorResponse(Config.ORDER_DETAIL_NOT_FOUND);
            }

            if (orderDetail.getOrder() == null && orderDetail.getOrder().getId() == null){
                return response.errorResponse(Config.ORDER_REQUIRED);
            }
            Optional<Order> checkOrder = orderRepository.findById(orderDetail.getOrder().getId());
            if (checkOrder.isEmpty()){
                return response.errorResponse(Config.ORDER_NOT_FOUND);
            }

            if (orderDetail.getProduct() == null && orderDetail.getProduct().getId() == null){
                return response.errorResponse(Config.PRODUCT_REQUIRED);
            }
            Optional<Product> checkProduct = productRepository.findById(orderDetail.getProduct().getId());
            if (checkProduct.isEmpty()){
                return response.errorResponse(Config.PRODUCT_NOT_FOUND);
            }

            checkOrderDetail.get().setQuantity(orderDetail.quantity);
            checkOrderDetail.get().setTotalPrice(orderDetail.totalPrice);
            return response.successResponse(orderDetailRepository.save(checkOrderDetail.get()));

        }catch (Exception e){
            log.error("Update Order Detail Error: " + e.getMessage());
            return response.errorResponse("Update Order Detail Error: " + e.getMessage());
        }

    }

    @Override
    public Map delete(OrderDetail orderDetail) {
        try {
            log.info("Delete Order Detail");

            if (orderDetail.getId() == null) {
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<OrderDetail> checkOrderDetail = orderDetailRepository.findById(orderDetail.getOrder().getId());
            if (checkOrderDetail.isEmpty()) {
                return response.errorResponse(Config.ORDER_NOT_FOUND);
            }

            checkOrderDetail.get().setDeleted_date(new Date());
            return response.successResponse(Config.DELETE_SUCCESS);

        }catch (Exception e){
            log.error("Delete Order Detail Error: " + e.getMessage());
            return response.errorResponse("Delete Order Detail Error: " + e.getMessage());
        }
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
