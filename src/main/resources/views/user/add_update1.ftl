<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 用户ID -->
            <input name="id" type="hidden" value="${(userInfo.id)!}"/>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input userName"
                           lay-verify="required" name="userName" id="userName"  value="${(userInfo.userName)!}" placeholder="请输入用户名">
                </div>
            </div>
<#--            <div class="layui-form-item layui-row layui-col-xs12">-->
<#--                <label class="layui-form-label">密码</label>-->
<#--                <div class="layui-input-block">-->
<#--                    <input type="password" class="layui-input userPwd"-->
<#--                           lay-verify="required" name="userPwd" id="userPwd" value="${(userInfo.userPwd)!}" placeholder="请输入密码">-->
<#--                </div>-->
<#--            </div>-->
<#--            <div class="layui-form-item layui-row layui-col-xs12">-->
<#--                <label class="layui-form-label">确认密码</label>-->
<#--                <div class="layui-input-block">-->
<#--                    <input type="password" class="layui-input repeatPwd"-->
<#--                           lay-verify="required" name="repeatPwd" id="repeatPwd" value="" placeholder="请再次输入密码">-->
<#--                </div>-->
<#--            </div>-->

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">手机号</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input phone"
                           lay-verify="phone" name="phone" value="${(userInfo.phone)!}" id="phone" placeholder="请输入手机号">
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">用户地址</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input address"
                           lay-verify="required" name="address" id="address" value="${(userInfo.address)!}" placeholder="请输入用户地址">
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">用户性别</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input gender"
                           lay-verify="required" name="gender" id="gender" value="${(userInfo.gender)!}" placeholder="请输入用户性别">
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">用户角色信息</label>
                <div class="layui-input-block">
                    <select name="userRole" id="userRole">
                        <option value="${(userInfo.userRole)!}">请选择用户信息</option>
                        <option value="0" style="color: red">用户</option>
                        <option value="1" style="color: green">管理员</option>
                    </select>
<#--                    <input type="text" class="layui-input userRole"-->
<#--                           lay-verify="required" name="userRole" id="userRole" value="${(userInfo.userRole)!}" placeholder="请输入用户角色信息">-->
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
                            lay-filter="addOrUpdateUser">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/user/add.update.js"></script>
    </body>
</html>