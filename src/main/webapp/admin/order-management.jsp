<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="entity.Orders" %>
<%@ page import="dao.OrderDao" %>

<h2>Quản lý đơn hàng</h2>
<table border="1">
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
        for (Orders order : ordersList) {
    %>
    <tr>
        <td><%= order.getOrderId() %>
        </td>
        <td><%= order.getUserId() %>
        </td>
        <td><%= order.getOrderDate() %>
        </td>
        <td><%= order.getStatus() %>
        </td>
        <td><%= order.getTotalAmount() %>
        </td>
        <td>
            <a href="<%= request.getContextPath() %>/editOrder?id=<%= order.getOrderId() %>">Edit</a> |
            <a href="<%= request.getContextPath() %>/deleteOrder?id=<%= order.getOrderId() %>"
               onclick="return confirm('Are you sure?')">Delete</a>
        </td>
    </tr>
    <%
        }
    %>
</table>
