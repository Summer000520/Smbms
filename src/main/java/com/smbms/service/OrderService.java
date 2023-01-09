package com.smbms.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mchange.lang.IntegerUtils;
import com.smbms.dao.OrderMapper;
import com.smbms.utils.AssertUtil;
import com.smbms.vo.Order;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {


    @Resource
    private OrderMapper orderMapper;


    /**
     *  分页查询用户列表
     * @param order
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> queryOrderByParams(Order order,Integer page, Integer limit) {

        Map<String, Object> map = new HashMap<>();

        System.out.println("第几页："+page);
        System.out.println("每页几个:"+limit);
        PageHelper.startPage(page, limit);

        // 得到对应的分页对象
        PageInfo<Order> pageInfo = new PageInfo<>(orderMapper.selectByTarget02(order));
        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的数据
        map.put("data", pageInfo.getList());

        return map;

    }


    /**
     * 添加订单
     *
     * @param order
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addOrder(Order order) {

        // 参数校验
        checkOrderParams(order);

        // 设置默认参数值
        order.setCreationDate(new Date());
        AssertUtil.isTrue(orderMapper.insertOrder(order) != 1, "订单添加失败！");
        System.out.println("添加的订单："+order);
    }

    /**
     *  更新订单
     * @param order
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateOrder(Order order) {

        //判断订单Id是否为空，且数据存在
        AssertUtil.isTrue(order.getId() == null, "待更新记录不存在！");

//        System.out.println("order:"+order);
        // 通过ID查询数据
        Order temp = orderMapper.selectById(order.getId());

        // 判断是否存在
        AssertUtil.isTrue(temp == null,"待更新记录不存在！" );

        // 参数校验
        checkOrderParams(order);

        // 创建时间添加到对象中
        order.setCreationDate(temp.getCreationDate());

        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(orderMapper.updateOrder(order)!=1,"订单更新失败!");

    }

    /**
     * 删除订单
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除记录不存在！");

        // 执行删除操作，判断受影响的行数
        AssertUtil.isTrue(orderMapper.deleteById(ids) != ids.length, "订单删除失败！");

    }

    /**
     * 判断订单参数
     *
     * @param order
     */
    private void checkOrderParams(Order order) {

        // 判断产品名称是否为空
        AssertUtil.isTrue(StringUtils.isBlank(order.getProductName()), "产品名称不能为空！");

        // 判断产品数量是否为空
        // 只判断是否为整数  小数和负数返回false
//        System.out.println("产品数量："+Integer.toString(order.getProductCount()));
        AssertUtil.isTrue(!NumberUtils.isDigits(Integer.toString(order.getProductCount())), "产品数量不能为空！");

        // 判断订单金额是否为空
//        System.out.println("产品金额："+Double.toString(order.getTotalPrice()));
        AssertUtil.isTrue(!NumberUtils.isCreatable(Double.toString(order.getTotalPrice())), "产品金额不能为空！");

        // 判断订单状态是否为空
        AssertUtil.isTrue(StringUtils.isBlank(order.getIsPayment()), "订单状态不能为空！");

        // 判断下单用户是否为空
        AssertUtil.isTrue(StringUtils.isBlank(order.getUserName()), "下单用户不能为空！");

        // 判断供应商是否为空
        AssertUtil.isTrue(StringUtils.isBlank(order.getProviderName()), "供应商不能为空！");

    }






    public List<Order> selectAll() {
        return orderMapper.selectAll();
    }

    public List<Order> selectByTarget(String productName, String creationDate) {
        return orderMapper.selectByTarget(productName, creationDate);
    }

    public List<Order> selectByTarget02(Order order) {
        return orderMapper.selectByTarget02(order);
    }

    public Order selectById(Integer orderId) {
        return orderMapper.selectById(orderId);
    }

}
