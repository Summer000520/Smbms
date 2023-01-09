<!DOCTYPE html>
<html>
    <head>
        <#include "../common.ftl">
    </head>
    <script>
        window.onload = myfun;

        function myfun() {
            var isPayment = $("#isPayment").val();

            console.log("myfun中的isPayment" + isPayment);
        }
    </script>
    <body class="childrenBody">
        <form class="layui-form" style="width:80%;">
            <#-- 用户ID -->
            <input name="id" type="hidden" value="${(orderInfo.id)!}"/>
            <input name="isPayment" type="hidden" value="${(orderInfo.isPayment)!}">

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">订单名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input productName"
                           lay-verify="required" name="productName" id="productName"  value="${(orderInfo.productName)!}" placeholder="请输入产品名称">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">订单数量</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input productCount"
                           lay-verify="required" name="productCount" id="productCount"  value="${(orderInfo.productCount)!}" placeholder="请输入订单数量">
                </div>
            </div>
            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">订单金额</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input totalPrice"
                           lay-verify="required" name="totalPrice" id="totalPrice" value="${(orderInfo.totalPrice)!}" placeholder="请输入订单金额">
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">是否付款</label>
                <div class="layui-input-block">
                    <select name="isPayment" id="isPayment">
                        <option value="${(orderInfo.isPayment)!}">请选择是否付款</option>
                        <option value="0" style="color: red">未付款</option>
                        <option value="1" style="color: green">已付款</option>
                    </select>
<#--                    <input type="text" class="layui-input isPayment"-->
<#--                           lay-verify="required" name="isPayment" id="isPayment" value="${(orderInfo.isPayment)!}" placeholder="是否付款">-->
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">供应商名称</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input providerName"
                           lay-verify="required" name="providerName" id="providerName" value="${(orderInfo.providerName)!}" placeholder="请输入供应商">
                </div>
            </div>

            <div class="layui-form-item layui-row layui-col-xs12">
                <label class="layui-form-label">下单用户</label>
                <div class="layui-input-block">
                    <input type="text" class="layui-input userName"
                           lay-verify="required" name="userName" id="userName" value="${(orderInfo.userName)!}" placeholder="请输入下单用户">
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
                            lay-filter="addOrUpdateOrder">确认
                    </button>
                    <button class="layui-btn layui-btn-lg layui-btn-normal" id="closeBtn">取消</button>
                </div>
            </div>
        </form>

    <script type="text/javascript" src="${ctx}/js/order/add.update.js"></script>
    </body>
</html>