<%--
  Created by IntelliJ IDEA.
  User: Kho may tinh HN
  Date: 2/14/2023
  Time: 9:19 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>List Students</h1>
<a href="/students?action=create">
  <button>Create new product</button>
</a>
<%--<a href="/categories">--%>
<%--  <button>List category</button>--%>
<%--</a>--%>

<form action="/students?action=search" method="post">
  <input type="text" name="search" id="search">
  <input type="submit" value="search">
</form>
<table>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>DateOfBirth</th>
    <th>Address</th>
    <th>PhoneNumber</th>
    <th>Email</th>
    <th>Classroom</th>
    <th colspan="2">Action</th>
  </tr>
  <%--    dùng c:forEach để render dữ liệu của list--%>
  <c:forEach var="s" items="${students}" varStatus="status">
    <tr>
      <td>${status.index + 1}</td>
      <td>${s.name}</td>
      <td>${s.dateOfBirth}</td>
      <td>${s.address}</td>
      <td>${s.phoneNumber}</td>
      <td>${s.email}</td>
      <td>${s.classroom.name}</td>
      <td><a href="/students?action=update&id=${s.id}">
        <button>Update</button>
      </a></td>
      <td><a href="/students?action=delete&id=${s.id}">
        <button>Delete</button>
      </a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
