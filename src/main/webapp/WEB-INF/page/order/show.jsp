<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/2
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <table>
            <tr>
                <th>姓名</th>
                <th>车辆</th>
                <th>借车天数</th>
                <th>缴纳金额</th>
                <th>订单状态</th>
            </tr>
            <tbody id="tb">

            </tbody>
        </table>

</body>
<script>

    $(function(){

        $.post("<%=request.getContextPath()%>/order/show",

            {},

            function(data){

            var html = "";

                for (var i = 0; i < data.data.length ; i++) {

                    var o = data.data[i];
                    html += "<tr>";
                    html += "<td>"+o.userNameShow+"</td>";
                    html += "<td>"+o.carNameShow+"</td>";
                    html += "<td>"+o.borrowDay+"</td>";
                    html += "<td>"+o.payMoney+"</td>";
                    html += o.carStatus == 1 ? "<td>订单进行中</td>":"<td>订单已结束</td>";
                    if(${user.level == 0} ||${user.level == 1}){
                        if(o.carStatus == 1){
                            html += "<td><input type='button' value='结束订单' onclick='endDing("+o.id+")'></td>";
                        }
                    }
                    html += "</tr>";
                }

                $("#tb").html(html)

            });

    })

    //结束订单
    function endDing(id){
        alert(id)
        $.post("<%=request.getContextPath()%>/order/endDing",

            {"id":id,"carStatus":2,"_method":"PUT"},

            function(data){

                if(data.code != 200){
                    alert(data.msg)
                    return;
                }
                alert(data.msg)
                window.location.href = "<%=request.getContextPath()%>/order/toShow";

            });
    }

</script>
</html>
