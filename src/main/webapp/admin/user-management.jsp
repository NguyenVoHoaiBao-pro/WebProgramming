<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<h2>Quản lý người dùng</h2>

<form method="post" action="UserServlet">
    <label for="username">Tên người dùng:</label>
    <input type="text" id="username" name="username" required>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <label for="password">Mật khẩu:</label>
    <input type="password" id="password" name="password" required>

    <label for="role">Vai trò:</label>
    <select id="role" name="role">
        <option value="1">Admin</option>
        <option value="2">Người dùng</option>
    </select>

    <label for="phone">Số điện thoại:</label>
    <input type="text" id="phone" name="phone">

    <button type="submit">Thêm người dùng</button>
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên người dùng</th>
        <th>Email</th>
        <th>Số điện thoại</th>
        <th>Vai trò</th>
        <th>Hành động</th>
    </tr>
    <%
        String url = "jdbc:mysql://localhost:3306/ten_cua_csdl";
        String username = "root";
        String password = "mat_khau";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();

            // Truy vấn danh sách người dùng
            String query = "SELECT User_ID, Username, Email, phone, Role FROM user";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int role = resultSet.getInt("Role");
                String roleName = (role == 1) ? "Admin" : "Người dùng";
    %>
    <tr>
        <td><%= resultSet.getInt("User_ID") %></td>
        <td><%= resultSet.getString("Username") %></td>
        <td><%= resultSet.getString("Email") %></td>
        <td><%= resultSet.getString("phone") %></td>
        <td><%= roleName %></td>
        <td>
            <a href="editUser?id=<%= resultSet.getInt("User_ID") %>">Sửa</a> |
            <a href="deleteUser?id=<%= resultSet.getInt("User_ID") %>">Xóa</a>
        </td>
    </tr>
    <%
        }
    } catch (Exception e) {
        e.printStackTrace();
    %>
    <tr>
        <td colspan="6">Đã xảy ra lỗi khi lấy dữ liệu người dùng.</td>
    </tr>
    <%
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    %>
</table>
