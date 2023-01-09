<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>基本资料</title>
    <#include "../common.ftl">
    <style>
        .layui-form-item .layui-input-company {
            width: auto;
            padding-right: 10px;
            line-height: 38px;
        }
    </style>
</head>
<body>
    <div class="layuimini-container">
        <div class="layuimini-main">
            <div class="layui-form layuimini-form">
                <div class="layui-form-item">
                    <label class="layui-form-label required">管理账号</label>
                    <div class="layui-input-block">
                        <input type="text" name="userName" lay-verify="required" readonly="readonly" class="layui-input"
                               value="${(userInfo.userName)!}">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label required">手机</label>
                    <div class="layui-input-block">
                        <input type="phone" name="phone" lay-verify="required" lay-reqtext="手机不能为空" placeholder="请输入手机"
                               value="${(userInfo.phone)!}" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">地址</label>
                    <div class="layui-input-block">
                        <input type="text" name="address" placeholder="请输入地址" value="${(userInfo.address)!}"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">性别</label>
                    <div class="layui-input-block">
                        <input type="text" name="gender" placeholder="请输入用户性别" value="${(userInfo.gender)!}"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <div class="layui-input-block">
                        <input type="hidden" name="userId" value="${(userInfo.id)!}">
                        <button class="layui-btn" lay-submit lay-filter="saveBtn" id="saveBtn">确认保存</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
<script>

</script>
<script type="text/javascript" src="${ctx}/js/user/setting.js"></script>
<#--<script type="text/javascript" src="${ctx}/"-->
</body>
</html>