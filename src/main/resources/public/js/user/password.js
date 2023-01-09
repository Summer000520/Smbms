layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);

    /**
     *  表单监听
     *         form.on('submit(按钮元素的lay-filter属性值)',function (data) {
     */
    form.on('submit(saveBtn)',function (data) {

        //所有表单元素的值
        console.log(data.field)

        $.ajax({
            type:"post",
            url: ctx + "/user/updatePwd",
            data:{
                // key与后端形参一致,value是前端name值
                oldPassword:data.field.old_password,
                newPassword:data.field.new_password,
                repeatPassword:data.field.again_password,
            },
            //result 后端返回结果
            success:function (result) {
                //判断是否修改成功
                if (result.code == 200){

                    //修改密码成功后，清空cookie数据，跳转登陆页面
                    layer.msg("用户密码修改成功，系统将在3秒后退出...",function () {
                        //清空cookie
                        $.removeCookie("id",{domain:"localhost",path:"/"});
                        $.removeCookie("userName",{domain:"localhost",path:"/"});
                        $.removeCookie("userRole",{domain:"localhost",path:"/"});

                        //跳转到登陆页面  没有parent会在页面窗口中跳转
                        window.parent.location.href = ctx +"/index"
                    })

                }else {
                    layer.msg(result.msg,{icon:5})
                }
            }
        })
    })








});