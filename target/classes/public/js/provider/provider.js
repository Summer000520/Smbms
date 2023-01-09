layui.use(['table', 'layer'], function () {
    var layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        table = layui.table;


    //加载数据表格
    var tableIns = table.render({
        // 容器元素的Id属性值
        elem: '#providerList'
        // 容器的高度 full - 差值
        , height: 'full-125'
        // 单元格显示最小宽度
        , cellMinWidth: 95
        // 访问数据的URL (后台数据接口)
        , url: '/provider/list' //数据接口
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
            , {field: 'id', title: '编号', sort: true, fixed: 'left'}
            , {field: 'proName', title: '供应商名称'}
            , {field: 'proContact', title: '供应商联系人', sort: true}
            , {field: 'proPhone', title: '联系人手机号',}
            , {field: 'proAddress', title: '供应商地址',}
            , {field: 'createdDate', title: '创建日期', sort: true}
            , {title: '操作', templet: '#providerListBar', field: 'right', align: 'center', minWidth: 150}
        ]]
    });

    $(".search_btn").click(function () {

        tableIns.reload({
            // 设置需要传递给后端的参数
            where: { //设定异步数据接口的额外参数，任意设
                // 通过文本框/下拉框的值，设置传递的参数 key 与后端属性名一致  name值为前端name值
                proName: $("[name='proName']").val() // 供应商名称
                , proContact: $("[name='proContact']").val() // 供应商联系人
                , proPhone: $("[name='proPhone']").val() // 联系人手机号
            }
            , page: {
                curr: 1 // 重新从第 1 页开始
            }
        })

    })

    /**
     * 监听头部工具栏
     */
    table.on('toolbar(provider)', function (data) {

        if (data.event == "add") { // 添加供货商

            // 打开添加/修改供应商的对话框
            openAddOrUpdateProviderDialog();

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
        layer.confirm('您确定要删除选中的记录吗？', {icon: 3, title: '供应商管理'}, function (index) {
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
                url: ctx + "/provider/delete",
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
     * 打开添加/修改供应商的对话框
     */
    function openAddOrUpdateProviderDialog() {

        var title = "<h3>供应商管理 -- 添加供应商</h3>"

        // iframe层
        layui.layer.open({
            // 类型 固定 表示是iframe 层
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['700px', '400px'],
            // url地址
            content: "/provider/toAddOrUpdatePage",
            // 可以最大化与最小化
            maxmin: true

        })


    }

    /**
     *  监听行工具栏
     */
    table.on('tool(provider)', function (data) {
        if (data.event == "edit") { // 更新供应商


            // 打开添加/修改供应商的对话框
            openAddOrUpdateUserDialog(data.data.id);

        } else if (data.event == "del") { // 删除供应商
            deleteProvider(data.data.id);
        }

    })


    function deleteProvider(id) {
        layer.confirm('您确定要删除选中的记录吗？', {icon: 3, title: '供应商管理'}, function (index) {
            // 关闭确认框
            layer.close(index);

            // 发送ajax请求, 执行删除用户操作
            $.ajax({
                type: "post",
                url: ctx + "/provider/delete",
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


    function openAddOrUpdateUserDialog(id) {
        console.log("传递的Id:" + id)
        var title = "<h3>供应商管理 -- 添加供应商</h3>";
        var url = ctx + "/provider/toAddOrUpdatePage";
        // 判断id是否为空，如果为空，则为添加操作，否则是修改操作
        if (id != null && id != '') {
            title = "<h3>供应商管理 -- 更新供应商</h3>"
            url += "?id=" + id; //传递主键，查询数据
        }
        // iframe层
        layui.layer.open({
            // 类型
            type: 2,
            // 标题
            title: title,
            // 宽高
            area: ['650px', '400px'],
            // url地址
            content: url,
            // 可以最大化与最小化
            maxmin: true

        });
    }

});