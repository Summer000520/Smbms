package com.smbms.model;


import lombok.Data;

@Data
public class ResultInfo {
    private Integer code = 200;
    private String msg = "操作成功";
    private Object result;

}
