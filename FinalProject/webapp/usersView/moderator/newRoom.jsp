<%--
  Created by IntelliJ IDEA.
  User: andrew_wannasesh
  Date: 16.05.21
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
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

<p><form action="controller?command=ACTADDNEWROOM" method = "post">
    <b>Room number: </b><input type="number" name ="roomNumber" min="1" value="100"><br>
    <b>Number of seats: </b><input type="number" name="numberOfSeats" min="1" max="5" value="1">
    <b>Room class: </b><select name="roomClass">
        <option selected = "selected">BUSINESS</option>
        <option>ECONOM</option>
        <option>LUXE</option>
        <option>PREMIUM</option>
    </select><br>
    <b>Price: </b><input type="text" name="price" min="1" max="10000" value="100"><br>
    <input type = "submit" value="Submit" />
</form></p>

</body>
</html>
