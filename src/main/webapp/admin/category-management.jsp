<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<h2>Quản lý danh mục</h2>

<!-- Form thêm danh mục -->
<form method="post" action="CategoryServlet">
    <label for="categoryName">Tên danh mục:</label>
    <input type="text" id="categoryName" name="categoryName" required>

    <label for="categoryDescription">Mô tả:</label>
    <input type="text" id="categoryDescription" name="categoryDescription">

    <button type="submit">Thêm danh mục</button>
</form>

<!-- Bảng hiển thị danh sách danh mục -->
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Mô tả</th>
        <th>Hành động</th>
    </tr>
    <%
        // Kết nối cơ sở dữ liệu
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

            // Truy vấn danh sách danh mục
            String query = "SELECT Category_ID, CategoryName, Description FROM category";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
    %>
    <tr>
        <td><%= resultSet.getInt("Category_ID") %></td>
        <td><%= resultSet.getString("CategoryName") %></td>
        <td><%= resultSet.getString("Description") %></td>
        <td>
            <a href="editCategory?id=<%= resultSet.getInt("Category_ID") %>">Sửa</a> |
            <a href="deleteCategory?id=<%= resultSet.getInt("Category_ID") %>">Xóa</a>
        </td>
    </tr>
    <%
        }
    } catch (Exception e) {
        e.printStackTrace();
    %>
    <tr>
        <td colspan="4">Đã xảy ra lỗi khi lấy dữ liệu danh mục.</td>
    </tr>
    <%
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    %>
</table>
