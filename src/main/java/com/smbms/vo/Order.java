package com.smbms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Order {

    private Integer id;             //id

    private String productName;     //产品名称

    private Integer productCount;   //产品数量

    private Double totalPrice;      //总金额

    private String isPayment;       //是否付款  1未付 2已付

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationDate;      //创建时间

    private String providerName;     //供货商id

    private String userName;         //用户id

}
