package com.challangebinarspring.binarfud.request;

import lombok.Data;

@Data
public class ResetPasswordModel {
    public String email;
    public String otp;
    public String newPassword;
}

