<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 3/27/2025
  Time: 8:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
    <title>Đặt lại mật khẩu</title>

  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
    }

    .container {
      background: white;
      padding: 20px;
      border-radius: 10px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      width: 350px;
      text-align: center;
    }

    h2 {
      margin-bottom: 20px;
      color: #333;
    }

    input {
      width: 100%;
      padding: 10px;
      margin: 10px 0;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    button {
      width: 100%;
      padding: 10px;
      background: #007bff;
      color: white;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 16px;
    }

    button:hover {
      background: #0056b3;
    }

    .message {
      color: red;
      font-size: 14px;
    }

  </style>
</head>
<body>
<div class="container">
  <h3>Đặt lại mật khẩu</h3>
  <form action="${pageContext.request.contextPath}/resetpassword" method="post">
  <input type="hidden" name="token" value="<%= request.getParameter("token") %>">
    <div class="form-group">
      <label for="password">Mật khẩu mới:</label>
      <input type="password" class="form-control" id="password" name="password" required>
    </div>
    <button type="submit" class="btn btn-success">Đặt lại mật khẩu</button>
  </form>
</div>

</body>
</html>
