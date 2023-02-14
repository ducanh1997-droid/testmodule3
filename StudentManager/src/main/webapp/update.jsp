<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 02/01/2023
  Time: 8:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Title</title>
</head>
<body>
<%--form chỉnh sửa thông tin sản phẩm, action tương ứng có kèm id--%>
<h1>Update form</h1>
<form action="students?action=update&id=${student.id}" method="post">
  <table>
    <tr>
      <td><label for="name">Name:</label></td>
      <td><input type="text" name="name" id="name" value="${student.name}"></td>
    </tr>
    <tr>
      <td><label for="dateOfBirth">DateOfBirth:</label></td>
      <td><input type="text" name="dateOfBirth" id="dateOfBirth" value="${student.dateOfBirth}"></td>
    </tr>
    <tr>
      <td><label for="address">Address:</label></td>
      <td><input type="text" name="address" id="address" value="${student.address}"></td>
    </tr>
    <tr>
      <td><label for="phoneNumber">PhoneNumber:</label></td>
      <td><input type="text" name="phoneNumber" id="phoneNumber" value="${student.phoneNumber}"></td>
    </tr>
    <tr>
      <td><label for="email">Email:</label></td>
      <td><input type="text" name="email" id="email" value="${student.email}"></td>
    </tr>
    <tr>
      <td><label for="classroom">Classroom:</label></td>
      <td><select name="classroom" id="classroom">
        <c:forEach items="${classrooms}" var="c">
          <option value="${c.id}">${c.name}</option>
        </c:forEach>
      </select></td>
    </tr>
    <tr>
      <td colspan="2">
        <button type="submit">Update</button>
        <a href="/students" style="text-decoration: none">
          <button>Back to home</button>
        </a>
      </td>
    </tr>
  </table>
</form>
</body>
</html>
