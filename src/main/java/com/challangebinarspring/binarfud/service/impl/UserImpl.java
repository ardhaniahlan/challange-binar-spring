package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.Config;
import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.repository.UserRepository;
import com.challangebinarspring.binarfud.service.UserService;
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
public class UserImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public Response response;

    @Override
    public Map save(User user) {
        try{
            log.info("Save User");

            if (user.getUsername().isEmpty()){
                return response.errorResponse(Config.NAME_REQUIRED);
            }

            return response.successResponse(userRepository.save(user));
        } catch (Exception e){
            log.error("Save User Error: " + e.getMessage());
            return response.errorResponse("Save User Error: " + e.getMessage());
        }
    }

    @Override
    public Map update(User user) {
        try{
            log.info("Update User");

            if (user.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<User> checkData = userRepository.findById(user.getId());
            if (checkData.isEmpty()){
                return response.errorResponse("Merchant Not Found");
            }

            checkData.get().setUsername(user.username);
            checkData.get().setEmail(user.email);
            checkData.get().setPassword(user.password);

            return response.successResponse(userRepository.save(checkData.get()));
        } catch (Exception e){
            log.error("Update User Error: " + e.getMessage());
            return response.errorResponse("Update User Error: " + e.getMessage());
        }
    }

    @Override
    public Map delete(User user) {
        try{
            log.info("Delete User");

            if (user.getId() == null){
                return response.errorResponse(Config.ID_REQUIRED);
            }
            Optional<User> checkData = userRepository.findById(user.getId());
            if (checkData.isEmpty()){
                return response.errorResponse("Merchant Not Found");
            }

            checkData.get().setDeleted_date(new Date());
            return response.successResponse(Config.DELETE_SUCCESS);
        }catch (Exception e){
            log.error("Delete User Error: " + e.getMessage());
            return response.errorResponse("Delete User Error: " + e.getMessage());
        }
    }

    @Override
    public Map getById(Long user) {
        Map map = new HashMap<>();
        Optional<User> getBaseOptional = userRepository.findById(user);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }
}
