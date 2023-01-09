package com.smbms.dao;

import com.smbms.vo.Order;
import org.mockito.internal.matchers.Or;

import java.util.Date;
import java.util.List;

public interface OrderMapper {

    // 查询所有订单
    List<Order> selectAll();

    // 根据id查询订单
    Order selectById(Integer id);

    // 新建订单
    int insertOrder(Order order);

    // 条件查询  根据产品名称 和 创建时间
    List<Order> selectByTarget(String productName, String creationDate);

    // 条件查询 传入对象 根据产品名称 和 创建时间
    List<Order> selectByTarget02(Order order);

    // 更新订单信息
    int updateOrder(Order order);

    // 删除订单
    int deleteById(Integer [] orderId);

}
