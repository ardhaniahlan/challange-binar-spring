package com.challangebinarspring.binarfud.service;

import com.challangebinarspring.binarfud.entity.User;

import java.util.Map;

public interface UserService {
    Map save(User user);
    Map update(User user);
    Map delete(User user);
    Map getById(Long user);
}
