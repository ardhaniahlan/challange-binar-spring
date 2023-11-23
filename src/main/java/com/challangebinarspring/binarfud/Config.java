package com.challangebinarspring.binarfud;

import org.springframework.stereotype.Component;

@Component
public class Config {
    public static Integer  EROR_CODE_404 =404;
    public static String NAME_REQUIRED = "Name is Required";
    public static String ID_REQUIRED = "Id is Required.";
    public static String DELETE_SUCCESS = "Delete Success.";
    public static String isRequired = "Dibutuhkan";

    //Merchant
    public static String MERCHANT_REQUIRED = "Merchant is Required.";
    public static String MERCHANT_NOT_FOUND = "Merchant not found.";

    //Product
    public static String PRODUCT_NOT_FOUND = "Product not found.";
    public static String PRODUCT_REQUIRED = "Product is Required.";

    //Order
    public static String ORDER_NOT_FOUND = "Order not found.";
    public static String ORDER_REQUIRED = "Order is Required.";

    //Order Detail
    public static String ORDER_DETAIL_NOT_FOUND = "Order Detail not found.";
    public static String ORDER_DETAIL_REQUIRED = "Order Detail is Required.";

    //User
    public static String USER_NOT_FOUND = "User not found.";
    public static String USER_REQUIRED = "User is Required.";


}

