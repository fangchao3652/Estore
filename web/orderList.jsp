<%--
  Created by IntelliJ IDEA.
  User: Fang
  Date: 2016/7/9
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>订单列表</h1>
<c:forEach items="${requestScope.list}" var="of">

</c:forEach>
</body>
</html>
