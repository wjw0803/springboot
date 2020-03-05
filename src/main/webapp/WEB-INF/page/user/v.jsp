<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/lib/jquery.mockjax.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
    <script src="https://static.runoob.com/assets/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/res/js/jquery-1.12.4.min.js"></script>
    <script src="<%=request.getContextPath()%>/res/jquery.validate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath() %>/res/layer/layer.js"></script>
    <title>Insert title here</title>
</head>
<body>

<form id ="fm">

    用户名<input type = "text" name = "userName" id = "userName"><br/>
    密码<input type = "password" name  = "pwd"><br/>
    手机号<input type = "text" name = "phone"><br/>
    邮箱<input type = "text" name = "email"><br/>
    级别<input type = "radio" name = "level" value ="0" onclick = "isShow(0)" >员工<input type = "radio" name = "level" value = "1" onclick = "isShow(1)">经理
    <input type = "radio" name = "level" value = "2" onclick = "isShow(2)">管理员<br/>
    <div id = "aa"></div>
    <div id= "lev">
        上级<select name="managerId">
        <option value="">请选择</option>
        <c:forEach items="${list}" var="s">
            <option value="${s.id}">${s.userName}</option>
        </c:forEach>
    </select><br/>
    </div>
    <div id = "bb"></div>
    <input type = "submit">

</form>


</body>
<!-- 错误时提示颜色 -->
<style>
    .error{
        color:red;
    }
</style>
<script type="text/javascript">

    jQuery.validator.addMethod("isPhoneNum", function (value, element) {
        var phone = $("input[name='phone']").val();
        var pattern = /^1[34578]\d{9}$/;
        return (pattern.test(phone));
    }, "请输入正确的联系人电话");


    $(function(){

        $("#fm").validate({


            errorPlacement: function (error, element) {
                if (element.is("[name='level']")){ //如果需要验证的元素名为userHobby
                    error.appendTo($("#aa"));   //错误增加在id为'checkbox-lang'中
                }else if(element.is("[name='managerId']")){
                    error.appendTo($("#bb"));
                }else{
                    error.insertAfter(element);//其他的就显示在添加框后
                }
            },

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
                    minlength:2
                },
                phone:{
                    required:true,
                    isPhoneNum:true
                },
                email:{
                    required:true,
                    email:true
                },
                level:{
                    required:true
                },
                managerId:{
                    required:true
                }
            },messages:{
                userName:{
                    required:"名字必填",
                    minlength:"至少两个字符",
                    remote:"用户已存在"
                },
                pwd:{
                    required:"密码必填",
                    minlength:"密码长度至少二位"
                },
                phone:{
                    required:"手机号必填",
                    isPhoneNum:"请输入正确的手机号"
                },email:{
                    required:"请输入邮箱",
                    email:"邮箱格式不正确"
                },level:{
                    required:"请选择级别"
                },managerId:{
                    required:"请选择上级"
                }
            }

        })
    })


    function isShow(num){
        if(num == 0){
            $("#lev").show()
        }else{
            $("#lev").hide()
        }
    }

    $.validator.setDefaults({
        submitHandler: function() {
            $.post("<%=request.getContextPath() %>/user/add",
                $("#fm").serialize(),
                function(data){

                    if(data.code != 200){
                        alert(data.msg)
                        return;
                    }
                    alert(data.msg)
                    parent.location.href = "<%=request.getContextPath()%>/user/toLogin";

                })
        }
    });

    <%-- 	function add(){

            var index = layer.load(1);

            $.post("<%=request.getContextPath()%>/user/add",

                    $("#fm").serialize(),

                    function(data){

                        layer.close(index);

                        if(data.code != 200){
                            layer.msg(data.msg, {icon: 5});
                            return;
                        }

                        layer.msg(data.msg, {
                              icon: 6,
                              time: 2000 //2秒关闭（如果不配置，默认是3秒）
                        }, function(){
                          //do something
                            parent.location.href = "<%=request.getContextPath()%>/user/toLogin";
                        });

                    });

        } --%>


</script>
</html>