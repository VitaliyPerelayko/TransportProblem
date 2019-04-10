<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>successPoint</title>
    <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<%--Подключаем JSTL--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>
<h1>Point added successfully</h1>
<table style="width:100%">
    <tr>
        <th>Id</th>
        <th>Name</th>
    </tr>
    <%--Берём информацию из БД у запроса по имени атрибута--%>
    <c:set var="point" value="${point}">
        <tr>
            <c:forEach var="fild" items="${point}">
                <td>
                    <c:out value="${fild}"/>
                </td>
            </c:forEach>
        </tr>
    </c:set>
</table>

</body>
</html>