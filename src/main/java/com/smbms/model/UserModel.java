package com.smbms.model;


import lombok.Data;

@Data
public class UserModel {
    private Integer id;
    private String userName;
    private String userRole;
    private Integer userCode;
}
