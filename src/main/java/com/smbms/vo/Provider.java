package com.smbms.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Provider {

    public Integer id;  // ID

    public String proName; // 供应商名称

    public String proContact; // 供应商联系人

    public String proPhone;  // 联系人手机号

    public String proAddress; // 供应商地址

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createdDate; // 创建时间
}
