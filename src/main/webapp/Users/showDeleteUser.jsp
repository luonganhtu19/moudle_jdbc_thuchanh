<%--
  Created by IntelliJ IDEA.
  User: luong
  Date: 1/6/2021
  Time: 5:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Information user delete</title>
</head>
<body>
<p>
    <a href="/users"> Back</a>
</p>
<c:choose>
    <c:when test="${requestScope['message']!=null}">
        <c:out value="${requestScope['message']}"/>
    </c:when>
    <c:otherwise>
        <form method="post">
    <fieldset>
        <legend>Edit information </legend>
        <table>
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" value="${user.name}"></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="email" value="${user.email}"></td>
            </tr>
            <tr>
                <td>Country</td>
                <td><input type="text" name="country" value="${user.country}"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Delete"></td>
            </tr>
        </table>
    </fieldset>
</form>
    </c:otherwise>
</c:choose>
</body>
</html>
