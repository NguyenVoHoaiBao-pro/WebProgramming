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

    private final CartDao cartDao;

    public OrderDao() {
        cartDao = new CartDao();
    }

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
                        rs.getBigDecimal("order_amount"),
                        rs.getTimestamp("order_date")
                );
                orderList.add(order);
            }

            System.out.println("Lấy danh sách đơn hàng thành công. Số đơn hàng: " + orderList.size());

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách đơn hàng: " + e.getMessage());
        }

        return orderList;
    }

    public int getLastOrderId() {
        String query = "SELECT order_id FROM orders ORDER BY order_id DESC LIMIT 1";
        int orderId = 0;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            System.out.println("Lấy order_id cuối cùng thành công: " + orderId);

        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy order_id cuối cùng: " + e.getMessage());
        }

        return orderId;
    }

    private void createOrderItemsFromCart(int orderId, int userId) {
        String query = "INSERT INTO order_item (order_id, product_id, quantity, unit_price) " +
                "SELECT ?, ci.product_id, ci.quantity, p.price " +
                "FROM cart_item ci " +
                "JOIN product p ON ci.product_id = p.product_id " +
                "WHERE ci.cart_id = (SELECT cart_id FROM cart WHERE user_id = ?);";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, orderId);
            statement.setInt(2, userId);
            int rowsInserted = statement.executeUpdate();

            System.out.println("Thêm " + rowsInserted + " sản phẩm vào order_item thành công cho order_id: " + orderId);

        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo order_item từ giỏ hàng: " + e.getMessage());
        }
    }

    public void createOrderFromCart(int userId, double orderAmount) {
        String query = "INSERT INTO orders (user_id, order_amount, order_date) VALUES (?, ?, NOW());";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setDouble(2, orderAmount);
            statement.executeUpdate();

            int orderId = getLastOrderId();
            System.out.println("Tạo đơn hàng thành công với order_id: " + orderId);

            // Gọi phương thức chuyển sản phẩm từ cart sang order_item
            createOrderItemsFromCart(orderId, userId);

            // Xóa giỏ hàng sau khi thanh toán
            cartDao.clearCart(userId);

            System.out.println("Thanh toán thành công và đã xóa giỏ hàng cho user_id: " + userId);

        } catch (SQLException e) {
            System.out.println("Lỗi khi tạo đơn hàng: " + e.getMessage());
        }
    }
}
