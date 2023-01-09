package com.smbms.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.smbms.dao.ProviderMapper;
import com.smbms.dao.UserMapper;
import com.smbms.utils.AssertUtil;
import com.smbms.utils.PhoneUtil;
import com.smbms.vo.Provider;
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
public class ProviderService {

    @Resource
    private ProviderMapper providerMapper;


    /**
     * 多条件分页查询 (返回数据格式必须满足LayUi中数据表格要求的格式)
     *
     * @param provider
     * @return
     */
    public Map<String, Object> queryProviderByParams(Provider provider,Integer page,Integer limit) {

        Map<String, Object> map = new HashMap<>();

        PageHelper.startPage(page, limit);

        // 得到对应的分页对象
        PageInfo<Provider> pageInfo = new PageInfo<>(providerMapper.selectByParams(provider));

        // 设置map对象
        map.put("code", 0);
        map.put("msg", "success");
        map.put("count", pageInfo.getTotal());
        // 设置分页好的数据
        map.put("data", pageInfo.getList());
        return map;
    }


    /**
     * 添加供应商
     *
     * @param provider
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addProvider(Provider provider) {

        checkProviderParams(provider);

        //设置参数默认值
        provider.setCreatedDate(new Date());

        AssertUtil.isTrue(providerMapper.insertProvider(provider) != 1, "用户添加失败！");

    }

    /**
     * 检查供应商参数
     * @param provider
     */
    private void checkProviderParams(Provider provider) {

        // 判断供应商名称是否为空
        AssertUtil.isTrue(StringUtils.isBlank(provider.getProName()), "供应商名称不能为空！");

        System.out.println("provider:"+provider.toString());
        // 判断供应商名称是否重复
        // 通过用户名查询用户对象
        Provider temp = providerMapper.selectByName(provider.getProName());
        // 如果供应商对象为空，则表示供应商可用，如果不为空，则不可用
        // 如果是添加操作，数据库中无数据，只要通过名城查到数据，则表示用户名被占用
        // 如果是修改操作，数据库中有对应的值，通过用户名查到数据，可能是当前记录本身，也可能是别的记录
        // 如果供应商存在，且与当前修改记录不是同一个，则表示其他记录占用了该用户名，不可用
        AssertUtil.isTrue(temp != null && !(temp.getId().equals(provider.getId())), "供应商名称已存在，请重试！");

        // 供应商联系人不为空
        AssertUtil.isTrue(StringUtils.isBlank(provider.getProContact()), "联系人不能为空！");

        // 手机号不能为空
        AssertUtil.isTrue(StringUtils.isBlank(provider.getProPhone()), "手机号不能为空！");

        // 手机号格式判断
        AssertUtil.isTrue(!PhoneUtil.isMobile(provider.getProPhone()), "手机号格式不正确！");

        // 地址不能为空
        AssertUtil.isTrue(StringUtils.isBlank(provider.getProAddress()), "地址不能为空！");

    }


    /**
     * 更新供应商信息
     * @param provider
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateProvider(Provider provider) {

        // 判断供应商ID是否为空，且数据存在
        AssertUtil.isTrue(provider.getId() == null, "待更新记录不存在！");

        // 通过ID查询数据
        Provider temp = providerMapper.selectById(provider.getId());

        // 判断是否存在
        AssertUtil.isTrue(temp == null, "待更新记录不存在！");

        // 参数校验
        checkProviderParams(provider);

        // 获取不需要修改的参数
        provider.setCreatedDate(temp.getCreatedDate());

        // 执行更新操作， 判断受影响的行数
        AssertUtil.isTrue(providerMapper.updateProvider(provider) != 1, "供应商更新失败！");

    }

    /**
     *  供应商删除
     * @param ids
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteByIds(Integer [] ids){
        for (int i = 0; i < ids.length; i++) {
            System.out.println("ids"+i+":"+Integer.valueOf(ids[i]));
        }
        // 判断ids是否为空，长度是否大于0
        AssertUtil.isTrue(ids == null || ids.length == 0,"待删除记录不存在！");

        // 执行删除操作，判断受影响的行数
        AssertUtil.isTrue(providerMapper.deleteByPrimaryKey(ids) != ids.length,"供应商删除失败！");
    }






    public Provider selectById(Integer id){
        return providerMapper.selectById(id);
    }


}
