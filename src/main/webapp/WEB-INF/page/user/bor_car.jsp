<%@ page import="javax.xml.ws.RequestWrapper" %><%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 19:07
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

            <input type="hidden" name="carId" value="${car.id}">
            <input type="hidden" name="money" value="${car.money}">
            车名:${car.carName}<br/>
            日租金:${car.money}<br/>
            请输入用车天数<input type = "text" name="borrowDay"><br/>
            请输入支付密码<input type="text" name="MiMa">
            <input type="button" value="确认租车" onclick="borcar()">

        </form>


</body>
<script>

    var n = 0;

    function borcar(){

        $.post("<%=request.getContextPath()%>/car/borCar",

            $("#fm").serialize(),

            function(data){

                if(data.code != 200){
                    n ++;
                    if(n == 3){
                        alert("错误次数太多,请5分钟后操作");
                        return;
                    } else if(n < 3){
                        alert(data.msg)
                        return;
                    }
                }
                alert(data.msg)
                window.location.href = "<%=request.getContextPath()%>/user/toBorrowCar";

            });
    }

</script>
</html>
