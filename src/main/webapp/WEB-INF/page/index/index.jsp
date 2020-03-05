<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>sss</title>
</head>

<frameset rows="20%,*">
    <frame src="<%=request.getContextPath()%>/index/toTop?token=${token}" name="top">
    <frameset cols="20%,*">
        <frame src="<%=request.getContextPath()%>/index/toMenu?token=${token}" name="left">
        <frame src="<%=request.getContextPath()%>/index/toRight?token=${token}" name="right">
    </frameset>
</frameset>

<body>

</body>
</html>