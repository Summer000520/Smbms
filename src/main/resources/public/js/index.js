layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     * 表单submit提交
     *  form.on('submit(按钮的lay-filter属性值)', function(data){
     *
     *      return false; //阻止表单跳转。
     *  });
     */
    form.on('submit(login)', function (data) {

        // console.log(data.elem) //被执行事件的元素DOM对象，一般为button对象
        // console.log(data.form) //被执行提交的form对象，一般在存在form标签时才会返回
        console.log(data.field) //当前容器的全部表单字段，名值对形式：{name: value}

        // 表单非空校验  layui已经完成

        // 发送ajax请求，传递用户姓名与密码，执行用户登陆操作
        $.ajax({
            type: "post",
            url: ctx + "/user/login",
            data: {
                //要与后端形参名称对应:与前端name属性值一致
                userName: data.field.username,
                userPwd: data.field.password,
            },
            success: function (result) { // result是回调函数，用来接收后端返回的数据
                console.log(result);
                //判断是否登陆成功 如果code=200 则表示成功，否则失败
                if (result.code == 200) {
                    // 登陆成功
                    /**
                     *  设置用户是一个登录状态
                     *      1.利用session会话
                     *          保存用户信息，如果会话存在，则用户是登陆状态，否则是非登陆状态
                     *          缺点: 服务器关闭，会话则会失效
                     *      2.利用cookie
                     *          保存用户信息，cookie未失效，则用户是登陆状态
                    */
                    layer.msg("登陆成功!",function () {
                        // 判断用户是否选择记住密码 (判断复选框是否被选中,选中，设置cookie7天有效期)
                        if ($("#rememberMe").prop("checked")){
                            // 选中，设置7天时效
                            //将用户信息设置到cookie中，
                            //第一个result是后端返回的整个对象，后面的则是对象中的用户数据
                            $.cookie("id",result.result.id);
                            $.cookie("userName",result.result.userName);
                            $.cookie("userRole",result.result.userRole);
                        }else{
                            //将用户信息设置到cookie中，
                            //第一个result是后端返回的整个对象，后面的则是对象中的用户数据
                            $.cookie("id",result.result.id);
                            $.cookie("userName",result.result.userName);
                            $.cookie("userRole",result.result.userRole);
                        }

                        //登陆成功后，跳转到首页
                        window.location.href = ctx + "/main";
                    })



                } else {
                    //登陆失败
                    layer.msg(result.msg, {icon: 5})
                }
            }
        })


        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });

});