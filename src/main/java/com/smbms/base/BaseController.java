package com.smbms.base;


import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @ModelAttribute
    public void preHandler(HttpServletRequest request){
//        System.out.println("ctx:"+request.getContextPath());
        request.setAttribute("ctx", request.getContextPath());
    }

}
