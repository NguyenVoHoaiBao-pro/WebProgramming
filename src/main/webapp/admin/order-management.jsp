<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Orders" %>
<%@ page import="dao.OrderDao" %>

<h2>Quản lý đơn hàng</h2>

<table border="1" style="width: 100%; text-align: center; border-collapse: collapse;">
    <tr>
        <th>ID Đơn hàng</th>
        <th>Khách hàng</th>
        <th>Ngày đặt</th>
        <th>Trạng thái</th>
        <th>Tổng tiền</th>
        <th>Hành động</th>
    </tr>

    <%
        OrderDao orderDao = new OrderDao();
        List<Orders> ordersList = orderDao.getAllOrders();

        if (ordersList != null && !ordersList.isEmpty()) {
            for (Orders order : ordersList) {
    %>
    <tr>
        <td><%= order.getOrderId() %></td>
        <td><%= order.getUserId() %></td>
        <td><%= order.getOrderDate() %></td>
        <td><%= order.getStatus() %></td>
        <td><%= String.format("%,.2f", order.getTotalAmount()) %> VND</td>
        <td>
            <a href="<%= request.getContextPath() %>/editOrder?id=<%= order.getOrderId() %>">Sửa</a> |
            <a href="<%= request.getContextPath() %>/deleteOrder?id=<%= order.getOrderId() %>"
               onclick="return confirm('Bạn có chắc chắn muốn xóa đơn hàng này?')">Xóa</a>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="6">Không có đơn hàng nào.</td>
    </tr>
    <%
        }
    %>
</table>
