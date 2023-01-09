package com.smbms.dao;


import com.smbms.vo.Provider;

import java.util.List;

public interface ProviderMapper {

    // 查询所有供应商
    List<Provider> selectAll();

    // 根据名称查询供应商
    Provider selectByName(String proName);

    // 根据id查询供应商
    Provider selectById(Integer id);

    // 新增供应商
    int insertProvider(Provider provider);

    // 更新供应商
    int updateProvider(Provider provider);

    // 根据Id删除供应商
    int deleteByPrimaryKey(Integer [] ids);

    // 根据参数查询供应商
    List<Provider> selectByParams(Provider provider);
}
