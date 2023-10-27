package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.entity.Merchant;
import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.repository.MerchantRepository;
import com.challangebinarspring.binarfud.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MerchantImpl implements MerchantService {

    @Autowired
    public MerchantRepository merchantRepository;

    @Override
    public Map save(Merchant merchant) {
        Map map = new HashMap<>();
        Merchant doSave = merchantRepository.save(merchant);

        map.put("Data", doSave);
        map.put("message","Success");
        map.put("status",200);

        return map;
    }

    @Override
    public Map update(Merchant merchant) {
        Map map = new HashMap<>();
        Merchant cekData = merchantRepository.getById(merchant.getId());
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setMerchantName(merchant.merchantName);
        cekData.setMerchantLocation(merchant.merchantLocation);
        cekData.setOpen(merchant.open);

        Merchant doUpdate = merchantRepository.save(cekData);
        map.put("Data", doUpdate);
        return map;
    }

    @Override
    public Map delete(Long merchant) {
        Map map = new HashMap<>();
        Merchant cekData = merchantRepository.getById(merchant);
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setDeleted_date(new Date());

        Merchant doDelete = merchantRepository.save(cekData);
        map.put("Data", doDelete);
        return map;
    }

    @Override
    public Map getById(Long merchant) {
        Map map = new HashMap<>();
        Optional<Merchant> getBaseOptional = merchantRepository.findById(merchant);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }
}
