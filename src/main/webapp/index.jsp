<%--
  Created by IntelliJ IDEA.
  User: student
  Date: 3/28/22
  Time: 11:15 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Access the web</h1>
<form name = "submitForm" action="AuthServlet" method="get">
    <table>
        <tbody>
        <tr>
            <td> IP addess </td>
            <td> <input type="text" name="H" value="" size="20" /></td>
        </tr>
        <tr>
            <td> Password:</td>
            <td> <input type="text" name="P" value="" size="20" /></td>
        </tr>
        </tbody>
    </table>
    <input type = "submit" value = "Log in" name="login" />

</form>
</body>
</html>
