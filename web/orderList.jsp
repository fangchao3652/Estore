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
    <h3> 订单号：${of.order.id} </h3>
    用户名：${of.username}<br>
    订单金额：${of.order.money }<br>
    支付状态：
    <c:if test="${of.order.paystate==0}">
        <font color="red">未支付</font>
        <a href="${pageContext.request.contextPath}/DelOrderServlet?id=${of.order.id}">删除订单</a>
    </c:if>
    <c:if test="${of.order.paystate!=0}">
        <font color="GREEN">已支付</font>
    </c:if>
    <br>

    收获地址：${of.order.receiverinfo}<br>
    下单时间：${of.order.ordertime}<br>
    <table width="100%" border="1">
        <tr>
            <th>商品名称</th>
            <th>商品种类</th>
            <th>商品数量</th>
            <th>商品单价</th>
            <th>总金额</th>
        </tr>
        <c:forEach items="${of.prodmap}" var="entry">
            <tr>
                <td>${entry.key.name}</td>
                <td>${entry.key.category}</td>
                <td>${entry.value}</td>
                <td>${entry.key.price}</td>
                <td>${entry.key.price*entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</c:forEach>
</body>
</html>
