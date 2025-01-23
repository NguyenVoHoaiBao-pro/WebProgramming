<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Quản lý danh mục</h2>
<form method="post" action="CategoryServlet">
    <label for="categoryName">Tên danh mục:</label>
    <input type="text" id="categoryName" name="categoryName" required>
    <button type="submit">Thêm danh mục</button>
</form>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Tên</th>
        <th>Mô tả</th>
        <th>Hành động</th>
    </tr>

</table>
