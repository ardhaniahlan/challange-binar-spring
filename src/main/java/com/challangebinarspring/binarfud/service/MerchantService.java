package com.challangebinarspring.binarfud.service;

import com.challangebinarspring.binarfud.entity.Merchant;

import java.util.Map;

public interface MerchantService {
    Map save(Merchant merchant);
    Map update(Merchant merchant);
    Map delete(Long merchant);
    Map getById(Long merchant);
}
