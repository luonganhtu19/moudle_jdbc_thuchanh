<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: luong
  Date: 1/6/2021
  Time: 2:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create User</title>
</head>
<body>
<h1>Information User</h1>
<p>
    <a href="/users"> Back</a>
</p>
<c:choose>
    <c:when test="${requestScope['message']!=null}">
        <p><c:out value="${requestScope['message']}"/> </p>
    </c:when>
    <c:otherwise>
<form method="post">
<fieldset>
    <legend> Create User</legend>
    <table>
        <tr>
            <td>Name: </td>
            <td><input type="text" name="name" placeholder="Input value "/></td>
        </tr>
        <tr>
            <td>Email: </td>
            <td><input type="text" name="email" placeholder="Input value "/></td>
        </tr>
        <tr>
            <td>Country: </td>
            <td><input type="text" name="country" placeholder="Input value"></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" name="submit"></td>
        </tr>
    </table>
</fieldset>
</form>
    </c:otherwise>
</c:choose>
</body>
</html>
