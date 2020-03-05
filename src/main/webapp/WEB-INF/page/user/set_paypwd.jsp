<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 19:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <form id="fm">
            <input type="hidden" name="_method" value="PUT">
            <input type="text" name="id" value="${id}">
            支付密码<input type="text" name="payPwd"><br/>
            <input type="button" value="确认支付密码" onclick="upd()">

        </form>

</body>
<script>

    function upd(){
        $.post("<%=request.getContextPath()%>/user/updPayPwd",

            $("#fm").serialize(),

            function(data){

                if(data.code != 200){
                    alert(data.msg)
                    return;
                }
                alert(data.msg)
                window.location.href = "<%=request.getContextPath()%>/user/toShow";

            });
    }

</script>
</html>
