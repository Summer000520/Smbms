package com.smbms.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;


@Data
public class User {
    private Integer id;         //id

    private String userName;    //用户名称

    private String userPwd;     //密码

    private String phone;       //手机

    private String address;     //地址

    private String gender;      //性别

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creationDate;  //创建时间

    private String userRole;    //用户角色 1代表用户 2代表管理员

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDate;    //修改时间

    private Integer userCode;   //用户编号

}