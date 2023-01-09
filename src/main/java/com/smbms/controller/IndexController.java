package com.smbms.controller;

import com.smbms.base.BaseController;
import com.smbms.service.UserService;
import com.smbms.utils.LoginUserUtil;
import com.smbms.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @Resource
    private UserService userService;
    /**
     * 系统登录页
     *
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "index";
    }

    // 系统界面欢迎页
    @RequestMapping("welcome")
    public String welcome() {
        return "welcome";
    }

    /**
     * 后端管理主页面
     *
     * @return
     */
    @RequestMapping("main")
    public String main(HttpServletRequest request) {
        //查询用户对象，设置session作用域
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        //查询用户对象，设置session作用域
//        System.out.println("查询的cookie的id："+userId);
        User user =userService.queryUserByPrimaryKey(userId);
        request.setAttribute("user",user);
//        System.out.println("cookie中user:"+user);
        return "main";
    }

}
