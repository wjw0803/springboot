<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/2
  Time: 18:18
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

            车名:<input type="text" name="carName"><br/>

            家用车<input type="radio" name="carType" value="0">豪华车<input type = "radio" name="carType" value="1">跑车<input type="radio" name="carType" value="2"><br/>

            租金范围<input type = "text" name="minMoney">~<input type="text" name="maxMoney"><br/>

            <input type="button" value="查询" onclick="search()">

            <input type="button" value="批量新增" onclick="toAdds()">

            <input type="button" value="上/下架" onclick="topOrDown()">

            <input type="button" value="删除" onclick="del()">

            <table>
                <tr>
                    <th></th>
                    <th></th>
                    <th>汽车名</th>
                    <th>车类型</th>
                    <th>日租金</th>
                </tr>
                <tbody id="tb">

                </tbody>
            </table>

        </form>

</body>
<script>

    $(function(){
        search();
    })



    function search(){


        $.post("<%=request.getContextPath()%>/car/show",

          $("#fm").serialize(),

            function(data){

                var html = "";

                for (var i = 0; i < data.data.length ; i++) {
                    var c = data.data[i];

                    html += "<tr>";
                    html += "<td><input type='hidden' id='"+c.id+"' value='"+c.status+"'></td>";
                    html += "<td><input type='checkbox' name='ids' value='"+c.id+"'></td>";
                    html += "<td>"+c.carName+"</td>";
                    if(c.carType == 0){
                        html += "<td>家用车</td>";
                    }else if(c.carType == 1){
                        html += "<td>豪华车</td>";
                    }else if(c.carType == 2){
                        html += "<td>跑车</td>";
                    }
                    html += "<td>"+c.money+"</td>";
                    html += c.status == 0 ?"<td>下架</td>":"<td>上架</td>";
                    html += "</tr>";
                }
                $("#tb").html(html)

            });
    }


    /*去批量新增页面*/
    function toAdds() {
        window.location.href = "<%=request.getContextPath()%>/car/toAdds";
    }

    function topOrDown() {

        var len = $("input[name='ids']:checked").length;

        if(len <= 0){
            alert("请选择");
            return;
        }
        if(len > 1){
            alert("只能选择一项")
            return;
        }
        var str = "";
        $("input[name='ids']:checked").each(function (index, item) {
            if ($("input[name='ids']:checked").length-1==index) {
                str += $(this).val();
            }
        });
        /*alert(str);*/

        var sta = $("#"+str).val();
        var msg = "您确定要设置为";

        var status = "";
        if(sta == 1){
            msg += "下架？";
            status = 0;
        } else {
            msg += "上架？"
            status = 1;
        }

        if(confirm(msg)){

            $.post("<%=request.getContextPath()%>/car/updTorDown",

                {"id":str,"status":status,"_method":"PUT"},

                function(data){

                    if(data.code != 200){
                        alert(data.msg)
                        return;
                    }
                    alert(data.msg)
                    window.location.href = "<%=request.getContextPath()%>/car/toShow";

                });

        }

    }

    //删除逻辑删除
    function del() {

        var len = $("input[name='ids']:checked").length

        if(len <= 0){
            alert("请选择");
            return;
        }

        var str = "";

        $("input[name='ids']:checked").each(function (index, item) {

            if ($("input[name='ids']:checked").length-1==index) {
                str += $(this).val();
            } else {
                str += $(this).val() + ",";
            }
        });

        alert(str)

        $.post("<%=request.getContextPath()%>/car/delCar",

            {"ids":str,"isDel":1,"_method":"PUT"},

            function(data){

                if(data.code != 200){
                    alert(data.msg)
                    return;
                }
                alert(data.msg)
                window.location.href = "<%=request.getContextPath()%>/car/toShow";

            });

    }


</script>
</html>
