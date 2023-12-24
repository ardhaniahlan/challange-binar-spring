package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.Config;
import com.challangebinarspring.binarfud.entity.Merchant;
import com.challangebinarspring.binarfud.repository.MerchantRepository;
import com.challangebinarspring.binarfud.service.MerchantService;
import com.challangebinarspring.binarfud.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
@Slf4j
public class MerchantImpl implements MerchantService {

    @Autowired
    public MerchantRepository merchantRepository;

    @Autowired
    public Response response;

    @Override
    public Map save(Merchant merchant) {
        try{
            log.info("Save Merchant");

            if (merchant.getMerchantName().isEmpty()){
                return response.errorResponse(Config.NAME_REQUIRED);
            }

            return response.successResponse(merchantRepository.save(merchant));
        } catch (Exception e){
            log.error("Save Merchant Error: " + e.getMessage());
            return response.errorResponse("Save Merchant Error: " + e.getMessage());
        }
    }

    @Override
    public Map update(Merchant merchant) {
        try {
            log.info("Update Merchant");

            // check data
            if (merchant.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<Merchant> checkData = merchantRepository.findById(merchant.getId());
            if (checkData.isEmpty()){
                return response.errorResponse(Config.MERCHANT_NOT_FOUND);
            }

            // update
            checkData.get().setMerchantName(merchant.merchantName);
            checkData.get().setMerchantLocation(merchant.merchantLocation);
            checkData.get().setOpen(merchant.open);

            return response.successResponse(merchantRepository.save(checkData.get()));
        } catch (Exception e){
            log.error("Update Merchant Error: " + e.getMessage());
            return response.errorResponse("Update Merchant Error: " + e.getMessage());
        }
    }

    @Override
    public Map delete(Merchant merchant) {
        try {
            log.info("Delete Merchant");

            if (merchant.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<Merchant> checkData = merchantRepository.findById(merchant.getId());
            if (checkData.isEmpty()){
                return response.errorResponse(Config.MERCHANT_NOT_FOUND);
            }

            checkData.get().setDeleted_date(new Date());

            return response.successResponse(Config.DELETE_SUCCESS);
        } catch (Exception e){
            log.error("Delete Merchant Error: " + e.getMessage());
            return response.errorResponse("Delete Merchant Error: " + e.getMessage());
        }
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
