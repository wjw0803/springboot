<%@ page import="java.util.Date" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 10:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shirp" uri="http://shiro.apache.org/tags" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>

    <form id="fm">

        <input type="hidden" id="pageNo" name="pageNo" value="1">

        手机号<input type="text" name="phone"><br/>
        未激活<input type="radio" name="isActive" value="0"><input type="radio" name="isActive" value="1">已激活<br/>

        <input type="button" value="查询" onclick="search()">

        <shiro:hasPermission name="user:chong">
          <input type="button" value="账户充值" onclick="toChong()">
        </shiro:hasPermission>
        
        <shiro:hasPermission name="user:chongvip">
           <input type="button" value="充值vip" onclick="topVip()">
        </shiro:hasPermission>
        
        <shiro:hasPermission name="user:borcar">
          <input type="button" value="我要租车" onclick="borrow()">
        </shiro:hasPermission>

        <table>
            <tr>
                <th>id</th>
                <th>用户名</th>
                <th>密码</th>
                <th>邮箱</th>
                <th>手机号</th>
                <th>激活状态</th>
                <th>等级</th>
               <th>vip失效时间</th>
                <th>账户余额</th>
                <th>操作</th>
            </tr>
            <tbody id="tb">

            </tbody>
        </table>
        <div id="pageInfo"></div>
    </form>

</body>
<script>

    $(function(){
        search()
    })

        function search() {

            $.post("<%=request.getContextPath()%>/user/show",

                $("#fm").serialize(),

                function(data){

                    var totalNum = data.data.pages;

                    var html = "";

                    for (var i = 0; i < data.data.records.length ; i++) {

                        var u  = data.data.records[i];

                        html += "<tr>";
                        html += "<td>"+u.id+"</td>";
                        html += "<td>"+u.userName+"</td>";
                        html += "<td>"+u.pwd+"</td>";
                        html += "<td>"+u.email+"</td>";
                        html += "<td>"+u.userPhoneShow+"</td>";
                        html += u.isActive == 0 ?"<td>未激活</td>":"<td>已激活</td>" ;
                        html += "<td>"+u.levelShow+"</td>";
                        if(u.level == 1){
                            html += "<td>"+u.vipVolidateTime+"</td>";
                        }else if(u.vipVolidateTime == null){
                            html += "<td>暂未开通vip</td>";
                        }
                        if(${user.level == 0 } || ${user.level == 1}){
                            if(u.accountMoney == null){
                                html += "<td><font color='red' '>账户充值有新人福利哦</font></td>";
                            }else{
                                html += "<td>"+u.accountMoney+"</td>";
                            }
                        }else if(u.accountMoney == null){
                            html += "<td>无金额</td>";
                        }else{
                            html += "<td>"+u.accountMoney+"</td>";
                        }
                        if(${user.level != 2}){
                             html += "<td><input type='button' value='设置支付密码' onclick='setPayPwd("+u.id+")'></td>";
                        }
                        html += "</tr>";
                    }

                    $("#tb").html(html);
                    var pageNo = $("#pageNo").val()
                    var pageNo2 = parseInt(pageNo)
                    var pageHtml = "<td><input type = 'button' value='上一页' onclick='pageLimit(" + (pageNo2 - 1) + "," + (totalNum) + ")'></td>";
                    pageHtml += "<td><input type = 'button' value='下一页' onclick='pageLimit(" + (pageNo2 + 1) + "," + (totalNum) + ")'></td>";
                    $("#pageInfo").html(pageHtml);

                });
        }

    //分页
    function pageLimit(pageNo2, totalNum) {
        if (pageNo2 < 1) {
            alert("首页");
            return;
        }
        if (pageNo2 > totalNum) {
            alert("尾页");
            return;
        }
        /*alert(pageNo2)*/
        $("#pageNo").val(pageNo2)
        search();
    }


        //去升级vip页面
        function topVip() {
            window.location.href = "<%=request.getContextPath()%>/user/toTopVip";
        }

        //去租车页面
        function borrow() {
            window.location.href = "<%=request.getContextPath()%>/user/toBorrowCar";
        }

        //去设置支付密码
        function setPayPwd(id) {
            alert(id)
            window.location.href = "<%=request.getContextPath()%>/user/setPayPwd/"+id;
        }

        //去充值页面
        function toChong() {
           window.location.href = "<%=request.getContextPath()%>/user/toChong";
        }

</script>
</html>
