<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>

        <input type="button" value="升级vip" onclick="topVip()">

        <table>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>密码</th>
                <th>邮箱</th>
                <th>手机号</th>
                <th>激活状态</th>
                <th>等级</th>
            </tr>
            <tbody id="tb">

            </tbody>
        </table>

</body>
<script>

        $(function(){
            $.post("<%=request.getContextPath()%>/user/show",

                {},

                function(data){

                var html = "";

                    for (var i = 0; i < data.data.length ; i++) {

                        var u  = data.data[i];

                        html += "<tr>";
                        html += "<td>"+u.id+"</td>";
                        html += "<td>"+u.userName+"</td>";
                        html += "<td>"+u.pwd+"</td>";
                        html += "<td>"+u.email+"</td>";
                        html += "<td>"+u.userPhoneShow+"</td>";
                        html += u.isActive == 0 ?"<td>未激活</td>":"<td>已激活</td>" ;
                        if(u.level == 0){
                            html += "<td>普通用户</td>";
                        }else if(u.level == 1){
                            html += "<td>vip</td>";
                        }else if(u.level == 2){
                            html += "<td>管理员</td>";
                        }

                        html += "</tr>";
                    }

                    $("#tb").html(html);

                });
        })

        //去升级vip页面
        function topVip() {
            window.location.href = "<%=request.getContextPath()%>/user/toTopVip";
        }

</script>
</html>
