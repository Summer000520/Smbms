package com.smbms.controller;


import com.smbms.base.BaseController;
import com.smbms.exceptions.ParamsException;
import com.smbms.model.ResultInfo;
import com.smbms.model.UserModel;
import com.smbms.service.UserService;
import com.smbms.utils.LoginUserUtil;
import com.smbms.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 1、当Controller是存在返回页面的控制器时，不可以使用@RestController
 * <p>
 * 2、当需要同时返回页面和字符串时，需要使用@Controller，在返回字符串的方法上使用@ResponseBody
 * <p>
 * 3、当不需要返回页面时，可以直接使用@RestController来代替@Controller和@ResponseBody，即可直接返回结果
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    @PostMapping("login")
    @ResponseBody
    public ResultInfo UserLogin(String userName, String userPwd) {
        System.out.println("进入登录方法");
        ResultInfo resultInfo = new ResultInfo();

//        System.out.println(userName);
//        System.out.println(userPwd);


        //调用service层登陆方法
        UserModel userModel = userService.userLogin(userName, userPwd);
        //设置ResultInfo的result值（将数据返回给请求）
        resultInfo.setResult(userModel);

        //通过try catch捕获service层的异常，如果service层抛出异常，则表示登陆失败，否则登陆成功
        try {


        } catch (ParamsException p) {
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        } catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("登陆失败！");
            e.printStackTrace();
        }
        return resultInfo;
    }

    /**
     * 用户修改密码
     *
     * @param request
     * @param oldPassword
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @PostMapping("updatePwd")
    @ResponseBody
    public ResultInfo updateUserPassword(HttpServletRequest request, String oldPassword, String newPassword, String repeatPassword) {
        ResultInfo resultInfo = new ResultInfo();
        // 获取cookie中的userId;
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        // 调用Service层修改密码方法
        userService.updatePassWord(userId, oldPassword, newPassword, repeatPassword);

        try {

        } catch (ParamsException p) {
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        } catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("修改密码失败！");
            e.printStackTrace();
        }

        return resultInfo;
    }

    /**
     * 进入修改密码的页面
     *
     * @return
     */
    @RequestMapping("toPasswordPage")
    public String toPasswordPage() {

        return "user/password";
    }

    @RequestMapping("toSettingPage")
    public String toSettingPage(HttpServletRequest request) {

        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
//        System.out.println("setting 中UserID:" + userId);
        if (userId != null) {
            // 通过id查询用户对象
//            int i = 1/0;
            User user = userService.queryUserByPrimaryKey(userId);
            // 将数据设置到请求域对象
            request.setAttribute("userInfo", user);
        }

        return "user/setting";
    }

    /**
     *  分页查询用户列表，按照Layui所需格式返回
     * @param user
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> selectByParams(User user,Integer page, Integer limit) {
//        System.out.println("------------------");
//        System.out.println("第几页："+page);
//        System.out.println("每页几个:"+limit);
        return userService.queryByParamsForTable(user,page,limit);
    }

    /**
     * 进入用户列表页面
     *
     * @return
     */

    @RequestMapping("index")
    public String index() {
        return "user/user";
    }

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("add")
    public ResultInfo addUser(User user, String repeatPwd) {
//        System.out.println("user"+user);
//        System.out.println("repeatPwd:"+repeatPwd);
//        AssertUtil.isTrue(user.getUserPwd()!=repeatPwd,"确认密码不正确！");
        userService.addUser(user);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("用户添加成功！");
        return resultInfo;
    }

    /**
     *   打开添加用户的页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("toAddOrUpdatePage")
    public String toAddOrUpdatePage(Integer id, HttpServletRequest request) {

        // 判断id是否为空，不为空表示更新操作，查询用户对象
//        System.out.println("进入添加或更新方法！！");
//        System.out.println("id:"+id);
        if (id != null) {
            // 通过id查询用户对象
            User user = userService.queryUserByPrimaryKey(id);
            // 将数据设置到请求域对象
            request.setAttribute("userInfo", user);
        }

        return "user/add_update";

    }

    /**
     *  打开修改用户的页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("toAddOrUpdatePage1")
    public String toAddOrUpdatePage1(Integer id, HttpServletRequest request) {

        // 判断id是否为空，不为空表示更新操作，查询用户对象
//        System.out.println("进入添加或更新方法！！");
//        System.out.println("id:"+id);
        if (id != null) {
            // 通过id查询用户对象
            User user = userService.queryUserByPrimaryKey(id);
            // 将数据设置到请求域对象
            request.setAttribute("userInfo", user);
        }

        return "user/add_update1";

    }

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("update")
    public ResultInfo updateUser(User user) {
        userService.updateUser(user);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("用户更新成功！");
        return resultInfo;
    }

    /**
     *  首页更新自身用户
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("updateSelf")
    public ResultInfo updateSelf(User user){

//        System.out.println("查询自己");
        userService.updateSelf(user);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("用户更新成功！");
        return resultInfo;
    }

    /**
     *  用户删除
     * @param ids 传一个参数为删除单条记录，传多条记录则删除多条记录
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer [] ids){

        userService.deleteByIds(ids);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("用户删除成功！");
        return resultInfo;
    }



























    /**
     * Restful风格根据用户ID查询用户信息
     *
     * @param userId 传入用户ID
     * @return 返回用户
     */
    @GetMapping("userId/{userId}")
    public User queryByUserId(@PathVariable Integer userId) {
        return userService.queryUserByPrimaryKey(userId);
    }

    /**
     * @param userId 传入用户ID
     * @return 返回用户
     */
    @GetMapping("userId02")
    public User queryByUserId02(Integer userId) {
        return userService.queryUserByPrimaryKey02(userId);
    }


    /**
     * 注册用户
     *
     * @param user 传入用户对象
     * @return 返回自定义信息 200表示操作成功    300表示操作失败
     */

    @PutMapping("register")
    public ResultInfo registerUser(@RequestBody User user) {
        ResultInfo resultInfo = new ResultInfo();
        try {
            userService.insert(user);
        } catch (Exception e) {
            resultInfo.setCode(300);
            resultInfo.setMsg("用户添加失败!");
            e.printStackTrace();
        }
        return resultInfo;
    }

    @PostMapping("login1")
    public ResultInfo selectByNameAndPwd(@PathVariable String userName, String userPwd) {
        ResultInfo resultInfo = new ResultInfo();

//        System.out.println(userName);
//        System.out.println(userPwd);

        //通过try catch捕获service层的异常，如果service层抛出异常，则表示登陆失败，否则登陆成功
        try {

            //调用service层登陆方法
            UserModel userModel = userService.userLogin(userName, userPwd);
            //设置ResultInfo的result值（将数据返回给请求）
            resultInfo.setResult(userModel);

        } catch (ParamsException p) {
            resultInfo.setCode(p.getCode());
            resultInfo.setMsg(p.getMsg());
            p.printStackTrace();
        } catch (Exception e) {
            resultInfo.setCode(500);
            resultInfo.setMsg("登陆失败！");
            e.printStackTrace();
        }
        return resultInfo;
    }



    //Select注解查询所有用户
    @GetMapping("selectAll02")
    public List<User> selectAll02() {
        return userService.selectAll02();
    }


    /**
     * restful 风格删除用户
     *
     * @param userId 传入用户ID
     * @return 返回自定义信息  200 操作成功       300 操作失败
     */
    @DeleteMapping("deleteUser/{userId}")
    public ResultInfo deleteUser(@PathVariable Integer userId) {
        ResultInfo resultInfo = new ResultInfo();
        try {
//            userService.delete(userId);
        } catch (Exception e) {
            resultInfo.setCode(300);
            resultInfo.setMsg("用户删除失败!");
            e.printStackTrace();
        }
        return resultInfo;
    }


}
