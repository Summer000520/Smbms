<!DOCTYPE html>
<html>
<head>
    <title>供货商管理</title>
    <#include "../common.ftl">
</head>
<body class="childrenBody">
<form class="layui-form" >
    <blockquote class="layui-elem-quote quoteBox">
        <form class="layui-form">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="text" name="proName" class="layui-input searchVal" placeholder="供货商名称" />
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="proContact" class="layui-input searchVal" placeholder="供货商联系人" />
                </div>
                <div class="layui-input-inline">
                    <input type="text" name="proPhone" class="layui-input searchVal" placeholder="联系人手机号" />
                </div>
                <a class="layui-btn search_btn" data-type="reload">
                    <i class="layui-icon">&#xe615;</i>
                    搜索
                </a>
            </div>
        </form>
    </blockquote>

    <#-- 在页面放置一个元素 <table id="demo"></table>，然后通过 table.render() 方法指定该容器 -->
    <table id="providerList" class="layui-table"  lay-filter="provider"></table>

    <#-- 头部工具栏 -->
    <script type="text/html" id="toolbarDemo">
        <div class="layui-btn-container">
            <a class="layui-btn layui-btn-normal addNews_btn" lay-event="add">
                <i class="layui-icon">&#xe608;</i>
                添加供货商
            </a>
            <a class="layui-btn layui-btn-normal delNews_btn" lay-event="del">
                <i class="layui-icon">&#xe608;</i>
                删除供货商
            </a>
        </div>
    </script>

    <!--操作-->
    <#-- 行工具栏 -->
    <script id="providerListBar" type="text/html">
        <a class="layui-btn layui-btn-xs" id="edit" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="del">删除</a>
    </script>
</form>

<script type="text/javascript" src="${ctx}/js/provider/provider.js"></script>
</body>
</html>