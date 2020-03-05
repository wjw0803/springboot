<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/1
  Time: 20:42
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
    <input type="button" value="领取新人福利" onclick="getUserFu()"><br/>
    <input type="hidden" name="_method" value="PUT">
    请输入手机号<input type="text" name="phone"><br/>
    验证码<input type = "text" name ="code"><input type = "button" value = "点此获取短信验证码" onclick ="getCode()"><br/>
    请输入充值金额:<input type="text" name="accountMoney"><br/>

    <input type="button" value="充值" onclick="chongZhi()">
</form>

<%--
<img alt="微信支付" src="<%=request.getContextPath() %>/static/res/img/wei_xin.png" height="300px" width="200px" >
--%>


</body>
<script>

    //领取福利
     function getUserFu(){
         $.post("<%=request.getContextPath()%>/user/getUserFu",
             {"_method":"PUT"},
             function(data){
                     if(data.code != 200){
                         alert(data.msg)
                         return;
                     }
                     alert(data.msg)
                     window.location.href = "<%=request.getContextPath()%>/user/toShow";

     });
 }

 //充值金额
    function  chongZhi() {
        $.post("<%=request.getContextPath()%>/user/chongZhi",

            $("#fm").serialize(),

            function(data){

                if(data.code != 200){
                    alert(data.msg)
                    return;
                }
                alert(data.msg)
                window.location.href = "<%=request.getContextPath()%>/user/toShow";

            });
    }

    //获取验证码
    function getCode() {
        $.post("<%=request.getContextPath()%>/user/getCode",

            $("#fm").serialize(),
            function(data){

                if(data.code != 200){
                    alert(data.msg);
                    return;
                }
                    alert(data.msg);
                    return;
            });
    }

/*
    $('button').click(function getwelfare () {$(this).attr('disabled', true);});
*/

</script>
</html>
