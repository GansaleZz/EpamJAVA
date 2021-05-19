<%--
  Created by IntelliJ IDEA.
  User: andrew_wannasesh
  Date: 15.05.21
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<a href="http://localhost:8080/controller?command=LOGOUT">Log Out</a>
<a href="http://localhost:8080/usersView/moderator/home/home.jsp">Home</a>
<a href="http://localhost:8080/controller?command=ACTSHOWROOMS">Rooms</a>
<a href="http://localhost:8080/controller?command=ACTSHOWPROFILE">Profile</a>
<a href="http://localhost:8080/controller?command=ACTSSHOWREQUESTS">Requests</a>
<a href="http://localhost:8080/controller?command=ACTSHOWUSERS">Users</a>

    <c:forEach var="user" items="${list}">
        <b>Id:</b><c:out value="${user.id}"></c:out><br>
        <b>Name: </b><c:out value="${user.name}"></c:out> <br>
        <b>Email: </b><c:out value="${user.email}"></c:out><br>
        <b>Role: </b><c:out value ="${user.userRole}"></c:out><br>
        <b>Status: </b><c:out value ="${user.status}"></c:out><br><br>
    </c:forEach>

</body>
</html>
