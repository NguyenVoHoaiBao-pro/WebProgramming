<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Báo cáo doanh thu</h2>
<form method="get" action="ReportServlet">
    <label for="dateRange">Chọn thời gian:</label>
    <input type="date" id="startDate" name="startDate">
    <input type="date" id="endDate" name="endDate">
    <button type="submit">Xem báo cáo</button>
</form>

<h3>Doanh thu theo ngày</h3>
<table border="1">
    <tr>
        <th>Ngày</th>
        <th>Doanh thu</th>
    </tr>
    <!-- Dữ liệu báo cáo sẽ được hiển thị ở đây -->
</table>
