<%--
  Created by IntelliJ IDEA.
  User: andrew_wannasesh
  Date: 15.05.21
  Time: 20:33
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Rooms</title>
</head>
<body>
<a href="http://localhost:8080/controller?command=LOGOUT">Log Out</a>
<a href="http://localhost:8080/usersView/client/home/home.jsp">Home</a>
<a href="http://localhost:8080/controller?command=ACTSHOWROOMS">Rooms</a>
<a href="http://localhost:8080/controller?command=ACTSHOWPROFILE">Profile</a>
<a href="http://localhost:8080/controller?command=ACTCREATEREQUEST">Create request</a>

  <c:forEach var="room" items="${list}">
    <p><b> Id: </b><c:out value="${room.id}"/><br>
      <b>Room status: </b><c:out value="${room.roomStatus}"/> <br>
      <b>Room class:  </b><c:out value="${room.roomClass}"/><br>
      <b>Number of seats: </b><c:out value="${room.numberOfSeats}"/><br>
      <b>Price: </b><c:out value="${room.price}"/> BYN for day.<br></p>
  </c:forEach>
</body>
</html>