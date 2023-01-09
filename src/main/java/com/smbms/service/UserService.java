package com.smbms.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.UserMapper;
import com.smbms.model.UserModel;
import com.smbms.utils.AssertUtil;
import com.smbms.utils.Md5Util;
import com.smbms.utils.PhoneUtil;
import com.smbms.vo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


    /**
     *  分页查询用户列表
     * @param user
     * @param page
     * @param limit
     * @return
     */
    public Map<String, Object> queryByParamsForTable(User user, Integer page, Integer limit) {
//        System.out.println("------------------");
//        System.out.println("第几页："+page);
//        System.out.println("每页几个:"+limit);
        Map<String, Object> result = new HashMap<String, Object>();
        PageHelper.startPage(page, limit);
        PageInfo pageInfo = new PageInfo(userMapper.selectByTarget(user));
        result.put("count", pageInfo.getTotal());
        result.put("data", pageInfo.getList());
        result.put("code", 0);
        result.put("msg", "");
//        System.out.println("前端传的user:" + user);
//        System.out.println("pageInfo:"+pageInfo);
//        System.out.println("result:"+result);
        return result;
    }


    /**
     * 用户登录
     *
     * @param userName
     * @param userPwd
     * @return
     */
    public UserModel userLogin(String userName, String userPwd) {
        // 1. 验证参数
        checkLoginParams(userName, userPwd);
        // 2. 根据用户名，查询用户对象
        User user = userMapper.queryUserByName(userName);

        // 3. 判断用户是否存在 (用户对象为空，记录不存在，方法结束)
        AssertUtil.isTrue(null == user, "用户不存在或已注销！");
        // 4. 用户对象不为空（用户存在，校验密码。密码不正确，方法结束）
        checkLoginPwd(userPwd, user.getUserPwd());
//        System.out.println("数据库密码：" + user.getUserPwd());

        // 5. 密码正确（用户登录成功，返回用户的相关信息）
        return buildUserInfo(user);
    }

    /**
     * 构建返回的用户信息
     *
     * @param user
     * @return
     */
    private UserModel buildUserInfo(User user) {
        UserModel userModel = new UserModel();
        // 设置用户信息
        userModel.setId(user.getId());
        userModel.setUserName(user.getUserName());
        userModel.setUserCode(user.getUserCode());
        userModel.setUserRole(user.getUserRole());
        return userModel;
    }

    /**
     * 验证用户登录参数
     *
     * @param userName
     * @param userPwd
     */
    private void checkLoginParams(String userName, String userPwd) {
        // 判断姓名
        AssertUtil.isTrue(StringUtils.isBlank(userName), "用户姓名不能为空！");
        // 判断密码
        AssertUtil.isTrue(StringUtils.isBlank(userPwd), "用户密码不能为空！");
    }

    /**
     * 验证登录密码
     *
     * @param userPwd 前台传递的密码
     * @param upwd    数据库中查询到的密码
     * @return
     */

    private void checkLoginPwd(String userPwd, String upwd) {
        // 数据库中的密码是经过加密的，将前台传递的密码先加密，再与数据库中的密码作比较

        userPwd = Md5Util.encode(userPwd);
        /*
            ==  基本数据类型：比较的是他们的值是否相同。
                引用数据类型：比较的是他们的内存地址是否同一地址。
            equals   方法常用来比较对象的内容是否相同。

         */
        // 比较密码
        AssertUtil.isTrue(!userPwd.equals(upwd), "用户密码不正确！");
    }





    /**
     * 修改密码
     * 1. 接收四个参数 （用户ID、原始密码、新密码、确认密码）
     * 2. 通过用户ID查询用户记录，返回用户对象
     * 3. 参数校验
     * 待更新用户记录是否存在 （用户对象是否为空）
     * 判断原始密码是否为空
     * 判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
     * 判断新密码是否为空
     * 判断新密码是否与原始密码一致 （不允许新密码与原始密码）
     * 判断确认密码是否为空
     * 判断确认密码是否与新密码一致
     * 4. 设置用户的新密码
     * 需要将新密码通过指定算法进行加密（md5加密）
     * 5. 执行更新操作，判断受影响的行数
     *
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updatePassWord(Integer userId, String oldPwd, String newPwd, String repeatPwd) {
        //通过用户Id查询用户记录，返回用户对象
        User user = userMapper.selectByPrimaryKey(userId);
        //判断用户记录是否存在
        AssertUtil.isTrue(null == user, "待更新记录不存在!");

        //参数校验
        checkPasswordParams(user, oldPwd, newPwd, repeatPwd);

        //设置用户新密码
        user.setUserPwd(Md5Util.encode(newPwd));

        //执行更新操作，判断受影响的行数
//        AssertUtil.isTrue(userMapper.updateByPrimaryKeySelective(user)<1,"用户修改密码失败！");
        AssertUtil.isTrue(userMapper.updateByPrimaryKey(user) < 1, "用户修改密码失败！");

    }

    /**
     * 修改密码的参数校验
     * 判断原始密码是否为空
     * 判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
     * 判断新密码是否为空
     * 判断新密码是否与原始密码一致 （不允许新密码与原始密码）
     * 判断确认密码是否为空
     * 判断确认密码是否与新密码一致
     *
     * @param user
     * @param oldPwd
     * @param newPwd
     * @param repeatPwd
     */
    private void checkPasswordParams(User user, String oldPwd, String newPwd, String repeatPwd) {
        //判断原始密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd), "原始密码不能为空！");
        //判断原始密码是否正确（查询的用户对象中的用户密码是否原始密码一致）
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPwd)), "原始密码不正确！");

        //判断新密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(newPwd), "新密码不能为空！");
        //判断新密码是否与原始密码一致 （不允许新密码与原始密码）
        AssertUtil.isTrue(user.getUserPwd().equals(Md5Util.encode(newPwd)), "新密码不能与原始密码相同！");

        //判断确认密码是否为空
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd), "确认密码不能为空！");
        //判断确认密码是否与新密码一致
        AssertUtil.isTrue(!newPwd.equals(repeatPwd), "确认密码与新密码不一致！");
    }

    /**
     *  注册用户
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addUser(User user) {
//        System.out.println("userName:"+user.getUserName());

        user.setId(null);
        user.setUserPwd(Md5Util.encode(user.getUserPwd()));
        // 参数校验
        checkUserParams(user);

        // 设置参数默认值
//        System.out.println("当前系统时间：" + new Date());
        user.setCreationDate(new Date());
        user.setModifyDate(new Date());
        user.setUserCode(1);

        // 执行添加操作，判断受影响的行数

        AssertUtil.isTrue(userMapper.insert(user) != 1, "用户添加失败！");
    }

//    private void checkUserParamsAdd(User user) {
//        // 判断用户名是否为空
//        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空！");
//        // 判断用户名的唯一性
//        // 通过用户名查询用户对象
//        User user1 = userMapper.queryUserByName(user.getUserName());
////        System.out.println("user1"+user1);
////        System.out.println("user:"+user);
//        //如果用户对象为空，则表示用户名可用，如果不为空，则不可用
//        // 如果是添加操作，数据库中无数据，只要通过名城查到数据，则表示用户名被占用
//        // 如果是修改操作，数据库中有对应的值，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
//        // 如果用户名存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
////        AssertUtil.isTrue(user1 != null && !(user.getId().equals(user1.getId())), "用户名已存在,请重新输入！");
//
//        //密码判断
//        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()), "用户密码不能为空！");
//
//        //手机号非空
//        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "用户手机号不能为空！");
//
//        //手机号 格式判断
//        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机号格式不正确！");
//
//        //性别非空
//        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户性别不能为空！");
//
//        //地址非空
//        AssertUtil.isTrue(StringUtils.isBlank(user.getAddress()), "用户地址不能为空！");
//
//        //角色非空
//        AssertUtil.isTrue(StringUtils.isBlank(user.getUserRole()), "用户角色信息不能为空！");
//
//    }

    /**
     * 参数校验
     *
     * @param user
     */
    private void checkUserParams(User user) {
        // 判断用户名是否为空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空！");
        // 判断用户名的唯一性
        // 通过用户名查询用户对象
        User user1 = userMapper.queryUserByName(user.getUserName());
//        System.out.println("user1"+user1);
//        System.out.println("user:"+user);
        //如果用户对象为空，则表示用户名可用，如果不为空，则不可用
        // 如果是添加操作，数据库中无数据，只要通过名城查到数据，则表示用户名被占用
        // 如果是修改操作，数据库中有对应的值，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
        // 如果用户名存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
        AssertUtil.isTrue(user1 != null && !(user.getId().equals(user1.getId())), "用户名已存在,请重新输入！");

        //密码判断
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserPwd()), "用户密码不能为空！");

        //手机号非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "用户手机号不能为空！");

        //手机号 格式判断
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机号格式不正确！");

        //性别非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户性别不能为空！");

        //地址非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getAddress()), "用户地址不能为空！");

        //角色非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserRole()), "用户角色信息不能为空！");

    }

    /**
     * 更新用户
     *
     * @param user
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUser(User user) {

        // 判断用户ID是否为空,且数据存在
        AssertUtil.isTrue(user.getId() == null, "待更新记录不存在!");

        // 通过id查询数据
        User temp = userMapper.selectByPrimaryKey(user.getId());

        // 判断是否存在
        AssertUtil.isTrue(temp == null, "待更新记录不存在！");

        // 获取修改账户的密码
        user.setUserPwd(temp.getUserPwd());
        //参数校验
        checkUserParams(user);

        // 设置创建时间
        user.setCreationDate(temp.getCreationDate());
        // 设置默认值
        user.setModifyDate(new Date());

        //执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKey(user) != 1, "用户更新失败！");
    }

    public void updateSelf(User user) {
        // 判断用户ID是否为空,且数据存在
        AssertUtil.isTrue(user.getId() == null, "待更新记录不存在!");

        // 通过id查询数据
        User temp = userMapper.selectById(user.getId());

        // 判断是否存在
        AssertUtil.isTrue(temp == null, "待更新记录不存在！");

        // 参数校验
        checkUserUpdateParams(user);

        // 获取不需要修改的信息

        user.setUserPwd(temp.getUserPwd());
        user.setCreationDate(temp.getCreationDate());
        user.setUserRole(temp.getUserRole());
        user.setModifyDate(new Date());
        user.setUserCode(temp.getUserCode());

        // 执行更新操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.updateByPrimaryKey(user) != 1, "用户更新失败！");

    }

    /**
     * 判断更新自身时参数
     * @param user
     */
    private void checkUserUpdateParams(User user) {
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户名不能为空！");

        // 判断用户名的唯一性
        // 通过用户名查询用户对象
        User user1 = userMapper.queryUserByName(user.getUserName());

        AssertUtil.isTrue(user1 != null && !(user.getId().equals(user1.getId())), "用户名已存在,请重新输入！");

        //手机号非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()), "用户手机号不能为空！");

        //手机号 格式判断
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()), "手机号格式不正确！");

        //性别非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()), "用户性别不能为空！");

        //地址非空
        AssertUtil.isTrue(StringUtils.isBlank(user.getAddress()), "用户地址不能为空！");

    }

    /**
     * 删除用户
     *
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer[] ids) {

//        for (int i = 0; i < ids.length; i++) {
//            System.out.println("ids" + i + ":" + Integer.valueOf(ids[i]));
//        }
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0, "待删除记录不存在！");

        // 执行删除操作，判断受影响的行数
        AssertUtil.isTrue(userMapper.deleteByPrimaryKey(ids) != ids.length, "用户删除失败！");

    }

    //根据主键ID查询用户并返回
    public User queryUserByPrimaryKey(Integer id) {
        return userMapper.selectById(id);
    }

    //根据主键ID查询用户并返回
    public User queryUserByPrimaryKey02(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    //注册用户
    public int insert(User user) {
        return userMapper.insert(user);
    }


    //更新用户
    public int update(User user) {
        return userMapper.updateByPrimaryKey(user);
    }


    //删除用户
    public int delete(Integer id) {
        return userMapper.deleteByPrimaryKey1(id);
    }

    //查询所有用户
    public List<User> selectAll() {
        return userMapper.selectAll();
    }

    //查询所有用户
    public List<User> selectAll02() {
        return userMapper.selectAll02();
    }



}
