layui.use(['form', 'jquery', 'jquery_cookie'], function () {
    var form = layui.form,
        layer = layui.layer,
        $ = layui.jquery,
        $ = layui.jquery_cookie($);


    // window.onload = myfun;
    //
    // function myfun() {
    //     alert("加载事件");
    //     console.log("加载事件")
    // }
    // $("#saveBtn").click(function (data) {
    //
    //     var formData = data.field;
    //     var userId = $("[name='userId']").val()
    //     console.log(userId)
    //     var phone = $("[name='phone']").val()
    //     console.log(phone)
    //     if ($("[name='userId']").val()){
    //
    //     }
    //
    // })

    form.on('submit(saveBtn)',function (data) {
        var formData = data.field;
        console.log(formData)

        $.ajax({
            type:"post",
            url: ctx + "/user/updateSelf",
            data:{
                userName:data.field.userName,
                phone:data.field.phone,
                address:data.field.address,
                gender:data.field.gender,
                id:data.field.userId,
            },
            success:function (result) {
                if (result.code == 200){
                    layer.msg("用户信息修改成功，回到首页！",function () {
                        //跳转到登陆页面  没有parent会在页面窗口中跳转
                        window.parent.location.href = ctx +"/main"
                    })
                }else {
                    layer.msg(result.msg,{icon:5})
                }
            }
        })

    })






})