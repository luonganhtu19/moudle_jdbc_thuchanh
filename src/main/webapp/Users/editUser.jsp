<%--
  Created by IntelliJ IDEA.
  User: luong
  Date: 1/6/2021
  Time: 2:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Information User</title>
</head>
<body>
    <p>
        <a href="/users">Back </a>
    </p>
    <p>
        <c:if test="${requestScope['message']!=null}">
        <c:out value="${requestScope['message']}"/>
        </c:if>
    </p>
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
                    <td><input type="submit" value="Save"></td>
                </tr>
            </table>
        </fieldset>
    </form>
</body>
</html>
