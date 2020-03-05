<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/2
  Time: 18:25
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

            <input type = "button" value = "+" onclick = "adds()">

            车名<input type = "text" name="carList[0].carName"><br/>
            车类型<select name="carList[0].carType">
                        <option value="0">家用车</option>
                        <option value="1">豪华车</option>
                        <option value="2">跑车</option>
                </select><br/>
            日租金<input type = "text" name="carList[0].money"><br/>
            <div id = "addDiv"></div>
            <input type = "button" value = "新增" onclick = "add()">

        </form>

</body>
<script>
        
        function add() {

            $.post("<%=request.getContextPath()%>/car/addCars",

                $("#fm").serialize(),

                function(data){

                    if(data.code != 200){
                        alert(data.msg)
                        return;
                    }
                    alert(data.msg)
                    window.location.href = "<%=request.getContextPath()%>/car/toShow";

                });

        }

        var  i = 0;

        function adds(){

            i++;
            var html = "";

            html += '<div class="divA"><hr/>车名<input type = "text" name="carList['+i+'].carName"><input type = "button" value = "-" onclick = "removeInfo(this)"><br/>';
            html += '车类型<select name="carList['+i+'].carType">';
            html += '<option value="0">家用车</option>';
            html += '<option value="1">豪华车</option>';
            html += '<option value="2">跑车</option>';
            html += ' </select><br/>';
            html += ' 日租金<input type = "text" name="carList['+i+'].money"><br/>';
            html += '</div>';
            $("#addDiv").append(html);
        }

        function removeInfo(obj){
            $(obj).parent(".divA").remove();
        }




</script>
</html>
