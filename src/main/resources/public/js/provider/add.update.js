layui.use(['form', 'layer', 'formSelects'], function () {
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;
    var formSelects = layui.formSelects;


    /**
     * 表单Submit监听
     */
    form.on('submit(addOrUpdateProvider)', function (data) {

        console.log("进入submit监听方法");
        // console.log(data);
        // 提交数据时的加载层 （https://layer.layui.com/）
        var index = top.layer.msg("数据提交中,请稍后...",{
            icon:16, // 图标
            time:false, // 不关闭
            shade:0.8 // 设置遮罩的透明度
        });
        // console.log(data);
        // 得到所有的表单元素的值
        var formData = data.field;
        console.log(formData);

        // 请求的地址
        // console.log("进入前端添加方法")
        var url = ctx + "/provider/add"; // 添加操作

        // 判断用户ID是否为空，如果不为空则为更新操作
        if ($("[name='id']").val()) {
            console.log("进入前端更新方法")
            // 更新操作
            var url = ctx + "/provider/update";
        }


        $.post(url, formData, function (result) {
            // 判断操作是否执行成功 200=成功
            if (result.code == 200) {
                // 成功
                // 提示成功
                top.layer.msg("操作成功！",{icon:6});
                // 关闭加载层
                top.layer.close(index);
                // 关闭弹出层
                layer.closeAll("iframe");
                // 刷新父窗口，重新加载数据
                parent.location.reload();
            } else {
                // 失败
                layer.msg(result.msg, {icon:5});
            }
        });

        // 阻止表单提交
        return false;
    });

    /**
     * 关闭弹出层
     */
    $("#closeBtn").click(function () {
        // 当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); // 先得到当前iframe层的索引
        parent.layer.close(index); // 再执行关闭
    });






});