package com.challangebinarspring.binarfud.service;

import com.challangebinarspring.binarfud.entity.User;
import com.challangebinarspring.binarfud.request.LoginModel;
import com.challangebinarspring.binarfud.request.RegisterModel;

import java.util.Map;

public interface UserService {
    Map save(User user);
    Map update(User user);
    Map delete(User user);
    Map getById(Long user);

    Map registerManual(RegisterModel objModel) ;
    Map registerByGoogle(RegisterModel objModel) ;
    public Map login(LoginModel objLogin);
}
