package com.smbms.dao;

import com.smbms.vo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    // 删除用户
    int deleteByPrimaryKey1(Integer id);

    // 删除用户
    int deleteByPrimaryKey(Integer[] ids);

    // 新建用户
    int insert(User user);

    // 根据主键ID查询用户
    User selectByPrimaryKey(Integer id);

    // 查询所有用户
//    @Select("")
    List<User> selectAll();

    //更新信息
    int updateByPrimaryKey(User user);

    // 根据主键ID查询用户
    User selectById(Integer id);

    // 测试注解SQL
    @Select("select * from user")
    List<User> selectAll02();


    // 根据名称查询用户
    User queryUserByName(String userName);

    // 根据返回具体参数查询用户
    List selectByTarget(User user);

}