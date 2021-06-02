<%--
  Created by IntelliJ IDEA.
  User: andrew_wannasesh
  Date: 15.05.21
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <style>
        table {
            border: 1px solid grey;
            border-collapse: collapse;
            margin: 20px;
        }
        th {
            border: 1px solid grey;
        }
        td {
            border: 1px solid grey;
        }
        caption{
            font-family: annabelle, cursive;
            font-weight: bold;
            font-size: 2em;
            padding: 10px;
            color: #F3CD26;
            text-shadow: 1px 1px 0 rgba(0,0,0,.3);
        }
    </style>
    <link rel="stylesheet" href="<c:url value="/resources/css/auth.css"/> ">
</head>
<body>
<nav class="one">
    <ul>
        <li><a href="http://localhost:8080/controller?command=ACTSHOWHOME"><i class="fa fa-home fa-fw"></i>Home</a></li>
        <li><a href="http://localhost:8080/controller?command=ACTSHOWROOMS">Rooms</a></li>
        <li><a href="http://localhost:8080/controller?command=ACTSHOWPROFILE">Profile</a></li>
        <li><a href="http://localhost:8080/controller?command=ACTSHOWREQUESTS">Requests</a></li>
        <li><a href="http://localhost:8080/controller?command=ACTSHOWUSERS">Users</a></li>
        <li><a href="http://localhost:8080/controller?command=LOGOUT">Log Out</a></li>
    </ul>
</nav>

    <c:set var="user" value="${user}" />
<form action="controller?command=ACTUPDATEPROFILE" method = "post">
    <p>
    <table>
        <colgroup>
            <col span="2" style="background: Khaki">
        </colgroup>
        <caption>Profile</caption>
       <input type = "hidden" name = "id" value="${user.id}" >
        <tr>
            <th>Login</th><td><c:out value="${user.login}"/></td>
        </tr>
        <tr>
            <th>Name</th><td><input type="text" name="name" value="${user.name}"></td>
        </tr>
        <tr>
            <th>Email</th><td><input type="email" name="email" value="${user.email}"></td>
        </tr>
        <tr>
            <th>Role</th><td><c:out value="${user.userRole}"/></td>
        </tr>
        <tr>
            <th>Action</th><td><input type="submit" value="Submit"></td>
        </tr>
    </table>
    </p>
</form>
</body>
</html>
