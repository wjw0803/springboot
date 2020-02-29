<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/2/29
  Time: 15:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/md5/md5-min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <form id="fm">
            用户名<input type = "text" name="userName"><br/>
            密码<input type="password" name="pwd" id = "aa"><br/>
            邮箱<input type="text" name="email"><br/>
            手机号<input type="text" name="phone"><br/>
            <%--注册是默认是普通用户--%>
            <input type="hidden" name="level" value="0">
            <%--注册默认是未激活的--%>
            <input type="hidden" name="isActive" value="0">
            <input type="button" value="注册" onclick="add()">
        </form>

</body>
<script>

        function add() {

            /*注释：把表单里的密码md5一下传到后台，后台再加密*/
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
        }


</script>
</html>
