package com.smbms;

import com.alibaba.fastjson.JSON;
import com.smbms.exceptions.NoLoginException;
import com.smbms.exceptions.ParamsException;
import com.smbms.model.ResultInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常统一处理
 */

@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {


    /**
     * 异常处理方法
     * 方法返回值：
     * 1.返回视图
     * 2.返回数据（JSON数据）
     * <p>
     * 判断方法的返回值？
     * 通过方法上是否声明@ResponseBody注解
     * 如果未声明，则返回视图
     * 如果声明了，则返回数据
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  方法对象
     * @param e        异常对象
     * @return org.springframework.web.servlet.ModelAndView;
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {

        /**
         *  非法请求拦截
         *      判断是否抛出未登录异常
         *          如果抛出未登录异常，则重定向跳转到登陆页面
         */

        if (e instanceof NoLoginException){
            // 重定向到登陆页面
            ModelAndView mv = new ModelAndView();
            mv.setViewName("redirect:/index");

            return mv;
        }

        /**
         * 设置默认异常处理（返回试图）
         */

        ModelAndView modelAndView = new ModelAndView("error");
        //设置异常信息
        modelAndView.addObject("code", 500);
        modelAndView.addObject("msg", "异常异常，请重试...");

        // 判断HandlerMethod
        if (handler instanceof HandlerMethod) {
            //类型转换
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法上声明的@ResponseBody注解对象
            ResponseBody responseBody = handlerMethod.getMethod().getDeclaredAnnotation(ResponseBody.class);
            // 判断responseBody是否为空 （如果为空，返回的是视图，如果不为空，则表示返回的是数据）
            if (responseBody == null) {
                /**
                 *  返回试图
                 */
                //判断异常类型
                if (e instanceof ParamsException){
                    ParamsException p = (ParamsException) e;
                    // 设置异常信息
                    modelAndView.addObject("code",p.getCode());
                    modelAndView.addObject("msg",p.getMsg());
                }else {
                    modelAndView.addObject("code",404);
                    modelAndView.addObject("msg","出现Exception");
                }
                return modelAndView;
            } else {
                /**
                 * 方法返回数据
                 */
                // 设置默认的异常处理
                ResultInfo resultInfo = new ResultInfo();
                resultInfo.setCode(500);
                resultInfo.setMsg("出现异常，请重试！");

                // 判断异常类型是否是自定义异常
                if (e instanceof ParamsException){
                    ParamsException p = (ParamsException) e;
                    resultInfo.setCode(p.getCode());
                    resultInfo.setMsg(p.getMsg());
                }

                // 通过流写入出去
                // 设置响应类型及编码格式（json格式数据）
                response.setContentType("application/json;charset=UTF-8");
                // 得到字符输出流
                PrintWriter out = null;
                try {
                     out = response.getWriter();

                     // 将需要返回的对象转换为JSON格式的字符
                    String json = JSON.toJSONString(resultInfo);
                     // 输出数据
                    out.write(json);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }finally {
                    //如果对象不为空，则关闭对象
                    if (out !=null){
                        out.close();
                    }
                }

                return null;
            }
        }

        return modelAndView;
    }

}
