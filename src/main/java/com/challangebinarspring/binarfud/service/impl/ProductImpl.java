package com.challangebinarspring.binarfud.service.impl;


import com.challangebinarspring.binarfud.entity.Order;
import com.challangebinarspring.binarfud.entity.Product;
import com.challangebinarspring.binarfud.repository.OrderRepository;
import com.challangebinarspring.binarfud.repository.ProductRepository;
import com.challangebinarspring.binarfud.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductImpl implements ProductService {
    @Autowired
    public ProductRepository productRepository;


    @Override
    public Map save(Product product) {
        Map map = new HashMap<>();
        Product doSave = productRepository.save(product);

        map.put("Data", doSave);
        map.put("message","Success");
        map.put("status",200);

        return map;
    }

    @Override
    public Map update(Product product) {
        Map map = new HashMap<>();
        Product cekData = productRepository.getById(product.getId());
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setProductName(product.productName);
        cekData.setPrice(product.price);

        Product doUpdate = productRepository.save(cekData);
        map.put("Data", doUpdate);
        return map;
    }

    @Override
    public Map delete(Long product) {
        Map map = new HashMap<>();
        Product cekData = productRepository.getById(product);
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setDeleted_date(new Date());

        Product doDelete = productRepository.save(cekData);
        map.put("Data", doDelete);
        return map;
    }

    @Override
    public Map getById(Long product) {
        Map map = new HashMap<>();
        Optional<Product> getBaseOptional = productRepository.findById(product);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }
}
