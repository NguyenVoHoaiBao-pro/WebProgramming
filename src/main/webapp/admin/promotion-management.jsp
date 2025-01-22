<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <!-- Dữ liệu khuyến mãi sẽ được hiển thị ở đây -->
</table>
