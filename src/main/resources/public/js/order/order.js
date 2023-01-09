layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    //加载数据表格
    var tableIns = table.render({
        // 容器元素的Id属性值
        elem: '#orderList'
        // 容器的高度 full - 差值
        , height: 'full-125'
        // 单元格显示最小宽度
        , cellMinWidth: 95
        // 访问数据的URL (后台数据接口)
        , url: '/order/list' //数据接口
        , page: true //开启分页
        , limit: 10 // 默认每页显示的数量
        , limits: [10, 20, 30, 40, 50] // 每页页数的可选项
        , toolbar: '#toolbarDemo'
        , cols: [[ //表头
            // field: 要求field属性值与返回的数据中对应的属性字段一一对应
            // title：设置列的标题
            //  sort；是否允许排序 （默认：false）
            // fixed：固定列
            {type: 'checkbox', fixed: 'center'}
            , {field: 'id', title: '订单编号', sort: true, fixed: 'left'}
            , {field: 'productName', title: '产品名称'}
            , {field: 'productCount', title: '订单数量', sort: true}
            , {field: 'totalPrice', title: '订单金额',}
            , {field: 'isPayment', title: '是否付款', templet: function (d) {
                    return formatPayment(d.isPayment);
                }}
            , {field: 'creationDate', title: '创建日期', sort: true}
            , {field: 'providerName', title: '供应商名称', sort: true}
            , {field: 'userName', title: '下单用户', sort: true}
            , {title: '操作', templet: '#orderListBar', field: 'right', align: 'center', minWidth: 150}
        ]],
    });

    function formatPayment(isPayment){
        if (isPayment == 1){
            return "<div style='color: green; font-family: Microsoft YaHei'>已付款</div>"
        }else{
            return "<div style='color: red; font-family: Microsoft YaHei'>未付款</div>"
        }
    }

    $(".search_btn").click(function () {

        tableIns.reload({
            // 设置需要传递给后端的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 通过文本框/下拉框的值，设置传递的参数 key 与后端属性名一致  name值为前端name值
                productName: $("[name='productName']").val() // 供应商名称
                , providerName: $("[name='providerName']").val() // 供应商联系人
                , userName: $("[name='userName']").val() // 联系人手机号
            }
            , page: {
                curr: 1 // 重新从第 1 页开始
            }
        })

    })

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(order)', function (data) {

        if (data.event == "add") { // 添加订单

            // 打开添加/修改供应商的对话框
            openAddOrUpdateOrderDialog();

        } else if (data.event == "del") { // 删除用户

            var checkStatus = table.checkStatus(data.config.id);
            console.log(data.config.id);
            console.log(checkStatus);
            console.log("准备进入删除方法！")
            // 删除多个用户记录
            deleteProviders(checkStatus.data);
        }
    })


    /**
     * 打开添加/修改供应商的对话框
     */
    function openAddOrUpdateOrderDialog() {

        var title = "<h3>订单管理 -- 添加订单</h3>"

        // iframe层
        layui.layer.open({
            // 类型 固定 表示是iframe 层
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '450px'],
            // url地址
            content: "/order/toAddOrUpdatePage",
            // 可以最大化与最小化
            maxmin: true

        })

    }

    /**
     *  删除多用户记录
     * @param userData
     */

    function deleteProviders(userData) {
        // 判断用户是否选择了要删除的记录
        if (userData.length == 0) {
            layer.msg("请选择要删除的记录!", {icon: 5});
            return;
        }

        // 询问用户是否确认删除
        layer.confirm('您确定要删除选中的记录吗？', {icon: 3, title: '订单管理'}, function (index) {
            // 关闭确认框
            layer.close(index);
            // 传递的参数是数组    ids=1&ids=2&ids=3
            var ids = "ids=";
            // 循环选中的行记录的数据
            for (var i = 0; i < userData.length; i++) {
                if (i < userData.length - 1) {
                    ids = ids + userData[i].id + "&ids="
                } else {
                    ids = ids + userData[i].id;
                }
            }
            // console.log(ids);

            // 发送ajax请求, 执行删除用户操作
            $.ajax({
                type: "post",
                url: ctx + "/order/delete",
                data: ids,
                success: function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功!", {icon: 6});
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            })
        })
    }


    /**
     *  监听行工具栏
     */
    table.on('tool(order)', function (data) {
        if (data.event == "edit") { // 更新供应商

            console.log("isPayment:"+data.data.isPayment)
            var isPayment = data.data.isPayment;
            if (isPayment == 1){
                // opt = "<option value='"+data[i].id+"' selected>"+data[i].uname+"</option>";
                opt ="<option value='"+isPayment+"' selected>已付款</option>"
            }else{
                opt ="<option value='"+isPayment+"'>未付款</option>"
            }

            $("#isPayment").append(opt);

            // 打开添加/修改供应商的对话框
            openAddOrUpdateOrderDialog(data.data.id,isPayment);

            //
            // var isPayment = $("#isPayment").val();
            //
            // var opt = '';


        } else if (data.event == "del") { // 删除供应商
            deleteProvider(data.data.id);
        }

    })


    function deleteProvider(id) {
        layer.confirm('您确定要删除选中的记录吗？', {icon: 3, title: '订单管理'}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求, 执行删除用户操作
            $.ajax({
                type: "post",
                url: ctx + "/order/delete",
                data: {
                    ids: id
                },
                success: function (result) {
                    // 判断删除结果
                    if (result.code == 200) {
                        // 提示成功
                        layer.msg("删除成功!", {icon: 6});
                        tableIns.reload();
                    } else {
                        // 提示失败
                        layer.msg(result.msg, {icon: 5});
                    }
                }
            })
        })

    }


    function openAddOrUpdateOrderDialog(id,isPayment) {

        console.log("传递的isPayment："+isPayment);

        if (isPayment == 1){
            // opt = "<option value='"+data[i].id+"' selected>"+data[i].uname+"</option>";
            opt ="<option value='"+isPayment+"' selected>已付款</option>"
        }else{
            opt ="<option value='"+isPayment+"'>未付款</option>"
        }
        console.log("最后一步！！！")
        $("#isPayment").append(opt);

        console.log("传递的Id:" + id)
        var title = "<h3>订单管理 -- 添加订单</h3>";
        var url = ctx + "/order/toAddOrUpdatePage";
        // 判断id是否为空，如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>订单管理 -- 更新订单</h3>"
            url += "?id=" + id ; //传递主键，查询数据
        }


        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '450px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin: true,

        });
    }
    //
    // $.ajax({
    //     type:"get",
    //     url:""
    // })

});