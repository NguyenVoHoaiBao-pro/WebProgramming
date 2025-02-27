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
        String query = "SELECT * FROM orders"; // Thêm dấu nháy ngược nếu tên bảng là Order

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getBigDecimal("order_amount"),
                        rs.getTimestamp("order_date")
                );
                orderList.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Bạn có thể in ra thêm e.getMessage() để rõ ràng hơn
        }

        return orderList;
    }
}
