package dao;

import entity.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static dao.MySQLConnection.getConnection;

public class OrderDao {
    public List<Orders> getAllOrders() {
        List<Orders> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {


            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getBigDecimal("total_amount"),
                        rs.getTimestamp("order_date")
                );
                orderList.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn danh sách đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }

        return orderList;
    }
}
