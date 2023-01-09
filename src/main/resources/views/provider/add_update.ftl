<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 用户ID -->
            <input name="id" type="hidden" value="${(providerInfo.id)!}"/>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">供应商名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input proName"
                           lay-verify="required" name="proName" id="proName"  value="${(providerInfo.proName)!}" placeholder="请输入供应商名称">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">供应商联系人</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input proContact"
                           lay-verify="required" name="proContact" id="proContact"  value="${(providerInfo.proContact)!}" placeholder="请输入供应商联系人">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input proPhone"
                           lay-verify="phone" name="proPhone" value="${(providerInfo.proPhone)!}" id="proPhone" placeholder="请输入手机号">
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">用户地址</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input proAddress"
                           lay-verify="required" name="proAddress" id="proAddress" value="${(providerInfo.proAddress)!}" placeholder="请输入用户地址">
                </div>
            </div>

            </div>

<#--            <div class="layui-form-item layui-row layui-col-xs12">-->
<#--                <label class="layui-form-label">角色</label>-->
<#--                <div class="layui-input-block">-->
<#--                    <select name="roleIds"  xm-select="selectId">-->
<#--                    </select>-->
<#--                </div>-->
<#--            </div>-->


            <br/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <div class="layui-input-block">
                    <button class="layui-btn layui-btn-lg" lay-submit=""
                            lay-filter="addOrUpdateProvider">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/provider/add.update.js"></script>
    </body>
</html>