package com.smbms.controller;

import com.smbms.base.BaseController;
import com.smbms.model.ResultInfo;
import com.smbms.service.ProviderService;
import com.smbms.vo.Provider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("provider")
public class ProviderController extends BaseController {

    @Resource
    private ProviderService providerService;


    /**
     * 进入供应商列表页面
     *
     * @return
     */

    @RequestMapping("index")
    public String index() {
        return "provider/provider";
    }


    /**
     * 供货商数据查询，（分页多条件查询）
     *
     * @param provider
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public Map<String, Object> queryProviderByParams(Provider provider,Integer page,Integer limit) {

        return providerService.queryProviderByParams(provider,page,limit);

    }

    /**
     * 添加供应商
     * @param provider
     * @return
     */
    @ResponseBody
    @PostMapping("add")
    public ResultInfo addProvider(Provider provider) {
        System.out.println("进入后端添加供应商方法");
        providerService.addProvider(provider);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("供应商添加成功！");
        return resultInfo;
    }

    /**
     *  跳转到添加或更新页面
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("toAddOrUpdatePage")
    public String toAddOrUpdatePage(Integer id, HttpServletRequest request){

        // 判断id是否为空，不为空表示更新操作，查询供应商对象
//        System.out.println("进入更新供应商界面,传递的ID："+id);
        if(id != null){
            // 通过id查询供应商对象
            Provider provider = providerService.selectById(id);
            // 将数据设置到请求域对象
//            System.out.println("更新操作查询到的供应商对象:"+provider);
            request.setAttribute("providerInfo", provider);
        }
        return "provider/add_update";
    }

    /**
     * 更新供应商信息
     * @param provider
     * @return
     */
    @PostMapping("update")
    @ResponseBody
    public ResultInfo updateProvider(Provider provider){

        providerService.updateProvider(provider);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("用户更新成功！");
        return resultInfo;
    }

    /**
     *  供货商删除
     * @param ids
     * @return
     */
    @PostMapping("delete")
    @ResponseBody
    public ResultInfo deleteProvider(Integer [] ids){

        providerService.deleteByIds(ids);
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setMsg("供应商删除成功!");
        return resultInfo;
    }


}
