package com.challangebinarspring.binarfud.service.impl;


import com.challangebinarspring.binarfud.Config;
import com.challangebinarspring.binarfud.entity.Merchant;
import com.challangebinarspring.binarfud.entity.Product;
import com.challangebinarspring.binarfud.repository.MerchantRepository;
import com.challangebinarspring.binarfud.repository.ProductRepository;
import com.challangebinarspring.binarfud.service.ProductService;
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
public class ProductImpl implements ProductService {

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public MerchantRepository merchantRepository;

    @Autowired
    public Response response;

    @Override
    public Map save(Product product) {
        try{
            log.info("Save Product");

            if (product.getProductName().isEmpty()){
                return response.errorResponse(Config.NAME_REQUIRED);
            }
            if (product.getMerchant() == null && product.getMerchant().getId() == null){
                return response.errorResponse(Config.MERCHANT_REQUIRED);
            }
            Optional<Merchant> checkMerchant = merchantRepository.findById(product.getMerchant().getId());
            if (checkMerchant.isEmpty()){
                return response.errorResponse(Config.MERCHANT_NOT_FOUND);
            }

            product.setMerchant(checkMerchant.get());
            return response.successResponse(productRepository.save(product));
        } catch (Exception e){
            log.error("Save Product Error: " + e.getMessage());
            return response.errorResponse("Save Product Error: " + e.getMessage());
        }
    }

    @Override
    public Map update(Product product) {
        try {
            log.info("Update Product");

            if (product.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<Product> checkProduct = productRepository.findById(product.getId());
            if(checkProduct.isEmpty()){
                return response.errorResponse(Config.PRODUCT_NOT_FOUND);
            }
            if (product.getMerchant() == null && product.getMerchant().getId() == null){
                return response.errorResponse(Config.MERCHANT_REQUIRED);
            }
            Optional<Merchant> checkMerchant = merchantRepository.findById(product.getMerchant().getId());
            if (checkMerchant.isEmpty()){
                return response.errorResponse(Config.MERCHANT_NOT_FOUND);
            }

            checkProduct.get().setProductName(product.productName);
            checkProduct.get().setPrice(product.price);

            return response.successResponse(productRepository.save(checkProduct.get()));
        } catch (Exception e){
            log.error("Update Product Error: " + e.getMessage());
            return response.errorResponse("Update Product Error: " + e.getMessage());
        }

    }

    @Override
    public Map delete(Product product) {
        try{
            log.info("Delete Product");

            if (product.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<Product> checkProduct = productRepository.findById(product.getId());
            if(checkProduct.isEmpty()){
                return response.errorResponse(Config.PRODUCT_NOT_FOUND);
            }

            checkProduct.get().setDeleted_date(new Date());
            return response.successResponse(Config.DELETE_SUCCESS);
        } catch (Exception e){
            log.error("Delete Product Error: " + e.getMessage());
            return response.errorResponse("Delete Product Error: " + e.getMessage());
        }
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
