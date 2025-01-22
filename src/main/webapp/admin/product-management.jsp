<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Quản lý sản phẩm</title>
</head>
<body>
<h2>Quản lý sản phẩm</h2>
<form method="post" action="ProductServlet">
    <label for="name">Tên sản phẩm:</label>
    <input type="text" id="name" name="name" required>

    <label for="description">Mô tả:</label>
    <textarea id="description" name="description"></textarea>

    <label for="price">Giá:</label>
    <input type="number" id="price" name="price" required>

    <label for="category">Danh mục:</label>
    <select id="category" name="category">
        <option value="1">Thời trang</option>
        <option value="2">Điện tử</option>
        <option value="3">Gia dụng</option>
    </select>

    <label for="brand">Thương hiệu:</label>
    <input type="text" id="brand" name="brand">

    <label for="quantity">Số lượng tồn kho:</label>
    <input type="number" id="quantity" name="quantity">

    <button type="submit">Thêm sản phẩm</button>
</form>

<h3>Danh sách sản phẩm</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Mô tả</th>
        <th>Giá</th>
        <th>Danh mục</th>
        <th>Thương hiệu</th>
        <th>Số lượng</th>
        <th>Hành động</th>
    </tr>
    <%
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/tmdt", "root", "password");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Products");

            while (rs.next()) {
    %>
    <tr>
        <td><%= rs.getInt("id") %>
        </td>
        <td><%= rs.getString("name") %>
        </td>
        <td><%= rs.getString("description") %>
        </td>
        <td><%= rs.getDouble("price") %>
        </td>
        <td><%= rs.getInt("category_id") %>
        </td>
        <td><%= rs.getString("brand") %>
        </td>
        <td><%= rs.getInt("stock") %>
        </td>
        <td>
            <a href="editProduct.jsp?id=<%= rs.getInt("id") %>">Sửa</a> |
            <a href="deleteProduct?id=<%= rs.getInt("id") %>"
               onclick="return confirm('Bạn có chắc muốn xóa sản phẩm này?');">Xóa</a>
        </td>
    </tr>
    <%
        }
        con.close();
    } catch (Exception e) {
        e.printStackTrace();
    %>
    <tr>
        <td colspan="8">Lỗi khi lấy dữ liệu từ cơ sở dữ liệu</td>
    </tr>
    <%
        }
    %>
</table>
</body>
</html>
