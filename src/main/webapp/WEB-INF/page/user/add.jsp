<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/2/29
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.mockjax.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath()%>/static/res/jquery.validate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/static/res/md5/md5-min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <form id="fm">
            用户名<input type = "text" name="userName" id = "userName"><br/>
            密码<input type="password" name="pwd" id = "aa"><br/>
            邮箱<input type="text" name="email"><br/>
            手机号<input type="text" name="phone"><br/>
            <%--注册是默认是普通用户--%>
            <input type="hidden" name="level" value="0">
            <%--注册默认是未激活的--%>
            <input type="hidden" name="isActive" value="0">
            <%--注册默认未领取过新人福利--%>
            <input type="hidden" name="isGetMoney" value="0">
            <input type = "submit">
        </form>

</body>
<!-- 错误时提示颜色 -->
<style>
    .error{
        color:red;
    }
</style>
<script>


    jQuery.validator.addMethod("isPhoneNum", function (value, element) {
        var phone = $("input[name='phone']").val();
        var pattern = /^1[34578]\d{9}$/;
        return (pattern.test(phone));
    }, "请输入正确的联系人电话");



    $(function(){

        $("#fm").validate({

            rules:{
                userName:{
                    required:true,
                    minlength:2,
                    remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}
                        type: 'POST',
                        url: "<%=request.getContextPath()%>/user/findByName",
                        data:{
                            userName:function() {
                                return $("#userName").val();
                            },
                            dataType:"json",
                            dataFilter:function(data,type){
                                if (data == 'true'){
                                    return true;
                                }else {
                                    return false;
                                }
                            }
                        }
                    }
                },
                pwd:{
                    required:true,
                    minlength:3
                },
                email:{
                    required:true,
                    email:true
                },
                phone:{
                    required:true,
                    isPhoneNum:true
                }
            },messages:{
                userName:{
                    required:"名字必填",
                    minlength:"至少两个字符",
                    remote:"用户已存在"
                },
                pwd:{
                    required:"密码必填",
                    minlength:"密码长度至少3位"
                },
                email:{
                    required:"请输入邮箱",
                    email:"邮箱格式不正确"
                },
                phone:{
                    required:"请输入手机号",
                    isPhoneNum:"请输入正确的手机号"
                }
            }

        })

    })



    $.validator.setDefaults({
        submitHandler: function() {

            /*注释：把表单里的密码md5一下传到后台，后台再加密*/
            var password = md5($("#aa").val());
            $("#aa").val(password);

            $.post("<%=request.getContextPath() %>/user/add",
                $("#fm").serialize(),
                function(data){

                    if(data.code != 200){
                        alert(data.msg)
                        return;
                    }
                    alert(data.msg);
                    window.location.href = "<%=request.getContextPath()%>/user/toLogin";
                })
        }
    });


     /*   function add() {

            /!*注释：把表单里的密码md5一下传到后台，后台再加密*!/
            var password = md5($("#aa").val());
            $("#aa").val(password);

            $.post("<%=request.getContextPath()%>/user/add",

                $("#fm").serialize(),

                function(data){

                    if(data.code != 200){
                        alert(data.msg)
                        return;
                    }
                    alert(data.msg);
                    window.location.href = "<%=request.getContextPath()%>/user/toLogin";
                });
        }*/


</script>
</html>
