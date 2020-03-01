<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/gVerify.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>
        <h4>欢迎充值</h4>
        充值说明1日vip:3元<br/>
                1月vip:30元<br/>
                1年vip:256元<br/>

        <form id="fm">
            <select name="vipType">
                <option value="">==请选择==</option>
                <option value="0">1日vip</option>
                <option value="1">1月vip</option>
                <option value="2">1年vip</option>
            </select><br/>
            <input type="text">
        </form>

</body>
</html>
