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
        String query = "SELECT * FROM orders"; // Thay đổi nếu bảng có tên đặc biệt hoặc dùng dấu nháy ngược

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            // Duyệt qua kết quả và tạo danh sách Orders
            while (rs.next()) {
                Orders order = new Orders(
                        rs.getInt("order_id"),
                        rs.getInt("user_id"),
                        rs.getBigDecimal("total_amount"), // Sửa đúng tên cột
                        rs.getTimestamp("order_date")
                );
                orderList.add(order);
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi truy vấn danh sách đơn hàng: " + e.getMessage());
            e.printStackTrace(); // Chỉ dùng trong quá trình phát triển
        }

        return orderList;
    }
    public boolean createOrderFromCart(int userId, double totalPrice) {
        String query = "INSERT INTO orders (user_id, total_amount, order_date) VALUES (?, ?, NOW())";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setDouble(2, totalPrice);
            int rowsInserted = statement.executeUpdate();

            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            System.err.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
            e.printStackTrace();
        }
        return false; // Trả về false nếu có lỗi
    }

}
