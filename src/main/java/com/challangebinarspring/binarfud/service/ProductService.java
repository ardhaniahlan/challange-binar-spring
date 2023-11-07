package com.challangebinarspring.binarfud.service;

import com.challangebinarspring.binarfud.entity.Product;

import java.util.Map;

public interface ProductService {
    Map save(Product product);
    Map update(Product product);
    Map delete(Product product);
    Map getById(Long product);
}
