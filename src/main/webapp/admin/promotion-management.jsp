<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.sql.*" %>
<h2>Quản lý khuyến mãi</h2>

<form method="post" action="PromotionServlet">
    <label for="code">Mã giảm giá:</label>
    <input type="text" id="code" name="code" required>

    <label for="discount">Phần trăm giảm:</label>
    <input type="number" id="discount" name="discount" required>

    <label for="startDate">Ngày bắt đầu:</label>
    <input type="date" id="startDate" name="startDate" required>

    <label for="endDate">Ngày kết thúc:</label>
    <input type="date" id="endDate" name="endDate" required>

    <button type="submit">Thêm khuyến mãi</button>
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Mã giảm giá</th>
        <th>Phần trăm giảm</th>
        <th>Ngày bắt đầu</th>
        <th>Ngày kết thúc</th>
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

            // Truy vấn danh sách khuyến mãi
            String query = "SELECT Promotion_ID, Code, Discount, StartDate, EndDate FROM promotion";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
    %>
    <tr>
        <td><%= resultSet.getInt("Promotion_ID") %></td>
        <td><%= resultSet.getString("Code") %></td>
        <td><%= resultSet.getInt("Discount") %> %</td>
        <td><%= resultSet.getDate("StartDate") %></td>
        <td><%= resultSet.getDate("EndDate") %></td>
        <td>
            <a href="editPromotion?id=<%= resultSet.getInt("Promotion_ID") %>">Sửa</a> |
            <a href="deletePromotion?id=<%= resultSet.getInt("Promotion_ID") %>">Xóa</a>
        </td>
    </tr>
    <%
        }
    } catch (Exception e) {
        e.printStackTrace();
    %>
    <tr>
        <td colspan="6">Đã xảy ra lỗi khi lấy dữ liệu khuyến mãi.</td>
    </tr>
    <%
        } finally {
            if (resultSet != null) try { resultSet.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (statement != null) try { statement.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (connection != null) try { connection.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    %>
</table>
