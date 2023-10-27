package com.challangebinarspring.binarfud.service.impl;

import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.repository.UserRepository;
import com.challangebinarspring.binarfud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class UserImpl implements UserService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public Map save(User user) {
        Map<String, java.io.Serializable> map = new HashMap<>();
        User doSave = userRepository.save(user);

        map.put("Data", doSave);
        map.put("data",doSave);
        map.put("message","Success");
        map.put("status",200);

        return map;
    }

    @Override
    public Map update(User user) {
        Map map = new HashMap<>();
        User cekData = userRepository.getById(user.getId());
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setUsername(user.username);
        cekData.setEmail(user.email);
        cekData.setPassword(user.password);

        User doUpdate = userRepository.save(cekData);
        map.put("Data", doUpdate);
        return map;
    }

    @Override
    public Map delete(Long user) {
        Map map = new HashMap<>();
        User cekData = userRepository.getById(user);
        if (cekData == null){
            map.put("message", "Data not Found");
            return map;
        }

        cekData.setDeleted_date(new Date());

        User doDelete = userRepository.save(cekData);
        map.put("Data", doDelete);
        return null;
    }

    @Override
    public Map getById(Long user) {
        Map<String, java.io.Serializable> map = new HashMap<>();
        Optional<User> getBaseOptional = userRepository.findById(user);
        if(getBaseOptional.isEmpty()){
            map.put("message", "Data not Found");
            return map;
        }

        map.put("Data", getBaseOptional.get());
        return map;
    }
}
