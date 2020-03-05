<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Insert title here</title>
</head>
<body>

<%--
			<a href="<%=request.getContextPath() %>/user/toShow?token=${token}" target="right">用户展示</a><br/>
--%>

<a href="<%=request.getContextPath()%>/user/toShow" target="right">用户管理</a><br/>
<a href="<%=request.getContextPath()%>/order/toShow" target="right">我的订单</a><br/>
<a href = "<%=request.getContextPath()%>/car/toShow" target="right">车辆管理</a>

</body>
</html>