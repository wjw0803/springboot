<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 18:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <h3>欢迎租车</h3>

        <table>
            <tr>
                <th>id</th>
                <th>汽车名</th>
                <th>车类型</th>
                <th>日租金</th>
            </tr>
            <tbody id="tb">

            </tbody>
        </table>


</body>
<script>

    $(function(){

        $.post("<%=request.getContextPath()%>/car/show",

            {},

            function(data){

                var html = "";

                for (var i = 0; i < data.data.length ; i++) {
                    var c = data.data[i];

                    html += "<tr>";
                    html += "<td>"+c.id+"</td>";
                    html += "<td>"+c.carName+"</td>";
                    if(c.carType == 0){
                        html += "<td>家用车</td>";
                    }else if(c.carType == 1){
                        html += "<td>豪华车</td>";
                    }else if(c.carType == 2){
                        html += "<td>跑车</td>";
                    }
                    html += "<td>"+c.money+"</td>";
                    html += "<td><input type = 'button' value='租车' onclick='borCar("+c.id+")'></td>";
                    html += "</tr>";
                }
                $("#tb").html(html)

            });

    })

    //去租车页面
    function borCar(id){
        alert(id)
        window.location.href = "<%=request.getContextPath()%>/car/borCar/"+id;
    }


</script>
</html>
