package com.smbms.controller;

import com.smbms.base.BaseController;
import com.smbms.model.ResultInfo;
import com.smbms.service.OrderService;
import com.smbms.vo.Order;
import com.smbms.vo.Provider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController {

    @Resource
    private OrderService orderService;

    /**
     * 进入订单模块首页
     * @return
     */
    @RequestMapping("index")
    public String index() {
        return "order/order";
    }

    /**
     * 分页查询订单列表，按照Layui所需格式返回
     * @param order
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryOrderByParams(Order order, Integer page, Integer limit) {
//        System.out.println("------------------");
//        System.out.println("第几页："+page);
//        System.out.println("每页几个:"+limit);
        return orderService.queryOrderByParams(order,page,limit);
    }

    /**
     *  进入添加或更新页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("toAddOrUpdatePage")
    public String toAddOrUpdatePage(Integer id, HttpServletRequest request) {

        // 判断id是否为空，不为空表示更新操作，查询供应商对象
//        System.out.println("进入更新供应商界面,传递的ID："+id);
        if (id != null) {
            // 通过id查询供应商对象
            Order order = orderService.selectById(id);
            // 将数据设置到请求域对象
//            System.out.println("更新操作查询到的供应商对象:"+provider);
            request.setAttribute("orderInfo", order);
        }
        return "order/add_update";
    }



    /**
     * 添加订单
     *
     * @param order 订单信息
     * @return
     */
    @PostMapping("add")
    @ResponseBody
    public ResultInfo insertOrder(Order order) {
        ResultInfo resultInfo = new ResultInfo();
        orderService.addOrder(order);
        resultInfo.setMsg("订单添加成功！");
        return resultInfo;
    }


    /**
     * 更新订单
     * @param order
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateProvider(Order order){

        orderService.updateOrder(order);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("订单更新成功！");
        return resultInfo;
    }

    /**
     *  订单删除
     * @param ids
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteUser(Integer [] ids){

        orderService.deleteByIds(ids);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("订单删除成功!");
        return resultInfo;
    }

















    /**
     * 根据传入参数 查询订单信息  没参数则查询所有订单
     *
     * @param productName  产品名称（可有可无）
     * @param creationDate 订单产生时间
     * @return 返回查询信息
     * @throws ParseException
     */

    @GetMapping("order/selectByTarget")
    public List<Order> selectByTarget(String productName, String creationDate) throws ParseException {
        //格式化日期
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //判断传入日期是否为空 日期不为空则处理日期格式
//        if (creationDate01 != null) {
//            Date creationDate = sdf.parse(creationDate01);
//            return orderService.selectByTarget(productName, creationDate);
//        }
        //传入字符日期为空  设置日期对象为空
//        Date creationDate = null;
        //返回查询结果
        System.out.println(creationDate);
        return orderService.selectByTarget(productName, creationDate);
    }

    /**
     * 多条件查询
     *
     * @param order 传入的订单信息
     * @return 返回查询结果
     * @throws ParseException
     */

//    @GetMapping("order/selectByTarget02")
//    public List<Order> selectByTarget(@RequestBody Order order) throws ParseException {
//        //日期格式化
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        //判断是否有查询条件 没有查询条件则查全部
//        if (order.getCreationDate() != null && order.getProductName() != null) {
//            //将日期格式转化为字符串格式
//            String date = order.getCreationDate().toString();
//            //剪切字符串
//            Date parse1 = DateFormatUtil.parse(date, "EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
//            String creationDate01 = sdf.format(parse1);
//            System.out.println(creationDate01);
//            //字符串转为日期格式
//            Date creationDate = sdf.parse(creationDate01);
//            order.setCreationDate(creationDate);
//            return orderService.selectByTarget02(order);
//        }
//        // JSON 传空值 也必须要有键值对 只不过值为null
//        return orderService.selectByTarget02(order);
//    }


    /**
     * 多条件查询
     *
     * @param order 传入的订单信息
     * @return 返回查询结果
     * @throws ParseException
     */

    @PostMapping("order/selectTarget02")
    public List<Order> selectByTarget02(@RequestBody Order order) {
//        System.out.println(order);
        return orderService.selectByTarget02(order);
    }


    @PostMapping("order/updateOrder")
    public ResultInfo updateOrder(@RequestBody Order order) {
        System.out.println(order);
        ResultInfo resultInfo = new ResultInfo();
        try {
            orderService.updateOrder(order);
        } catch (Exception e) {
            resultInfo.setCode(300);
            resultInfo.setMsg("更新失败！");
            e.printStackTrace();
        }
        return resultInfo;
    }



}