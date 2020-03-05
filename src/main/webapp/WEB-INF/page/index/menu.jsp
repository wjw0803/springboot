<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dj
  Date: 2020/3/2
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/static/res/zTree_v3/css/zTreeStyle/zTreeStyle.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/js/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/res/zTree_v3/js/jquery.ztree.all.min.js"></script>
<html>
<head>
    <title>Title</title>
</head>
<body>


<%--
    <c:forEach items="${resourceList}" var="resource">
         <a href="<%=request.getContextPath()%>${resource.url}" target="right">${resource.resourceName}</a><br/>
     </c:forEach>

--%>

    <%--ztree显示容器--%>
    <div id = "tre" class = "ztree"></div>
</body>
<script>

    //ztree设置
    var setting = {
        //数据格式设置
        data: {
            //简单JSON数据格式默认是关闭状态
            simpleData: {
                //开启简单数据格式
                enable: true,
                idKey:"id",
                //实体类父级id
                pIdKey:"pId"
            },
            key:{
                //实体类中名字展示
                name:"resourceName",
                //写一个不存在的url名称实体类中没有的
                url:"resourceUrl"
            }
        },
        //事件
        callback: {
            //点击事件
            onClick: function(event, treeId, treeNode){
                //如果不是父节点不跳转
                if(!treeNode.isParent){
                    //parent是回到index页面中,right是target之前链接的页面,也就是在右边显示
                    parent.right.location.href  = "<%=request.getContextPath()%>"+treeNode.url;
                }
            }
        }
    };

    $(function(){

        $.post("<%=request.getContextPath()%>/index/getMenu",

            {},

            function(data){
                $.fn.zTree.init($("#tre"), setting, data.data);
            });

    })

</script>
</html>

