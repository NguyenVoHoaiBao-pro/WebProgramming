//package dao;
//
//import entity.Cart;
//import entity.CartItem;
//import entity.Products;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import static dao.MySQLConnection.getConnection;
//
//public class CartDao {
//    public boolean AddToCart(int userId, int productId, int quantity) {
//        try (Connection connection = MySQLConnection.getConnection()) {
//            // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
//            String checkQuery = "SELECT cart_id FROM cart WHERE user_id = ? AND product_id = ?";
//            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
//                checkStmt.setInt(1, userId);
//                checkStmt.setInt(2, productId);
//
//                try (ResultSet resultSet = checkStmt.executeQuery()) {
//                    if (resultSet.next()) {
//                        // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật quantity
//                        int cartId = resultSet.getInt("cart_id");
//
//                        String updateQuery = "UPDATE cart_item SET quantity = quantity + ? WHERE cart_id = ? AND product = ?";
//                        try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
//                            updateStmt.setInt(1, quantity);
//                            updateStmt.setInt(2, cartId);
//                            updateStmt.setInt(3, productId);
//                            int rowsUpdated = updateStmt.executeUpdate();
//                            return rowsUpdated > 0;
//                        }
//                    } else {
//                        // Nếu sản phẩm chưa tồn tại, thêm mới vào bảng `cart` và `cart_item`
//                        String insertCartQuery = "INSERT INTO cart (user_id, product_id, created_at) VALUES (?, ?, NOW())";
//                        try (PreparedStatement insertCartStmt = connection.prepareStatement(insertCartQuery, Statement.RETURN_GENERATED_KEYS)) {
//                            insertCartStmt.setInt(1, userId);
//                            insertCartStmt.setInt(2, productId);
//                            insertCartStmt.executeUpdate();
//
//                            // Lấy cart_id vừa được thêm
//                            try (ResultSet generatedKeys = insertCartStmt.getGeneratedKeys()) {
//                                if (generatedKeys.next()) {
//                                    int cartId = generatedKeys.getInt(1);
//
//                                    // Thêm chi tiết sản phẩm vào `cart_item`
//                                    String insertCartItemQuery = "INSERT INTO cart_item (cart_id, product, quantity) VALUES (?, ?, ?)";
//                                    try (PreparedStatement insertCartItemStmt = connection.prepareStatement(insertCartItemQuery)) {
//                                        insertCartItemStmt.setInt(1, cartId);
//                                        insertCartItemStmt.setInt(2, productId);
//                                        insertCartItemStmt.setInt(3, quantity);
//                                        int rowsInserted = insertCartItemStmt.executeUpdate();
//                                        return rowsInserted > 0;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//
//
//    public boolean removeFromCart(int userId, int productId) {
//        try (Connection connection = MySQLConnection.getConnection()) {
//            String checkQuery = "SELECT cart_id FROM cart WHERE user_id = ? AND product_id = ?";
//            try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
//                checkStmt.setInt(1, userId);
//                checkStmt.setInt(2, productId);
//
//                try (ResultSet resultSet = checkStmt.executeQuery()) {
//                    if (resultSet.next()) {
//                        int cartId = resultSet.getInt("cart_id");
//
//                        // Xóa sản phẩm khỏi bảng cart_item
//                        String deleteCartItemQuery = "DELETE FROM cart_item WHERE cart_id = ? AND product= ?";
//                        try (PreparedStatement deleteCartItemStmt = connection.prepareStatement(deleteCartItemQuery)) {
//                            deleteCartItemStmt.setInt(1, cartId);
//                            deleteCartItemStmt.setInt(2, productId);
//                            deleteCartItemStmt.executeUpdate();
//                        }
//
//                        // Kiểm tra xem còn sản phẩm nào trong cart_item không
//                        String countQuery = "SELECT COUNT(*) AS count FROM cart_item WHERE cart_id = ?";
//                        try (PreparedStatement countStmt = connection.prepareStatement(countQuery)) {
//                            countStmt.setInt(1, cartId);
//
//                            try (ResultSet countResult = countStmt.executeQuery()) {
//                                if (countResult.next() && countResult.getInt("count") == 0) {
//                                    // Nếu không còn sản phẩm nào, xóa cart
//                                    String deleteCartQuery = "DELETE FROM cart WHERE cart_id = ?";
//                                    try (PreparedStatement deleteCartStmt = connection.prepareStatement(deleteCartQuery)) {
//                                        deleteCartStmt.setInt(1, cartId);
//                                        deleteCartStmt.executeUpdate();
//                                    }
//                                }
//                            }
//                        }
//                        return true;
//                    }
//                }
//            }
//        } catch (SQLException e) {
//            System.err.println("Error while removing from cart: " + e.getMessage());
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//
//
//    public List<CartItem> getCartByUserId(int userId) {
//        List<CartItem> cartItems = new ArrayList<>();
//        String query = "SELECT c.cart_id, c.user_id, c.product_id, c.created_at, ci.quantity " +
//                "FROM cart c " +
//                "JOIN cart_item ci ON c.cart_id = ci.cart_id " +
//                "WHERE c.user_id = ?";
//
//        try (Connection connection = MySQLConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                int cartId = rs.getInt("cart_id");
//                int productId = rs.getInt("product_id");
//                int quantity = rs.getInt("quantity");
//                Timestamp createdAt = rs.getTimestamp("created_at");
//
//                // Tạo đối tượng Cart
//                Cart cart = new Cart(cartId, userId, productId, createdAt);
//
//                // Tạo đối tượng CartItem
//                CartItem cartItem = new CartItem(cart, quantity);
//                cartItems.add(cartItem);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("SQLException in getCartByUserId: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        return cartItems;
//    }
//
//
//
//
//    public int getQuantity(int cartId, int productId) throws SQLException {
//        String sql = "SELECT quantity FROM cart_item WHERE cart_id = ? AND product = ?";
//        try (Connection connection = MySQLConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//
//            // Log thông tin truy vấn và tham số
//            System.out.println("[getQuantity] Executing query: " + sql);
//            System.out.println("[getQuantity] Parameters - cartId: " + cartId + ", productId: " + productId);
//
//            stmt.setInt(1, cartId);
//            stmt.setInt(2, productId);
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    int quantity = rs.getInt("quantity");
//                    // Log kết quả nếu tìm thấy sản phẩm
//                    System.out.println("[getQuantity] Found quantity: " + quantity + " for productId: " + productId);
//                    return quantity;
//                } else {
//                    // Log nếu không tìm thấy sản phẩm
//                    System.out.println("[getQuantity] No product found for productId: " + productId + " in cartId: " + cartId);
//                    return -1;
//                }
//            }
//        } catch (SQLException e) {
//            // Log lỗi SQL
//            System.err.println("[getQuantity] SQL Error: " + e.getMessage());
//            throw e;
//        }
//    }
//
//    public void updateQuantity(int cartId, int productId, int newQuantity) throws SQLException {
//        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_id = ? AND product = ?";
//        try (Connection connection = MySQLConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//
//            // Log thông tin truy vấn và tham số
//            System.out.println("[updateQuantity] Executing query: " + sql);
//            System.out.println("[updateQuantity] Parameters - newQuantity: " + newQuantity + ", cartId: " + cartId + ", productId: " + productId);
//
//            stmt.setInt(1, newQuantity);
//            stmt.setInt(2, cartId);
//            stmt.setInt(3, productId);
//
//            int rowsUpdated = stmt.executeUpdate();
//            // Log số hàng được cập nhật
//            if (rowsUpdated > 0) {
//                System.out.println("[updateQuantity] Successfully updated quantity to " + newQuantity + " for productId: " + productId + " in cartId: " + cartId);
//            } else {
//                System.out.println("[updateQuantity] No rows updated for productId: " + productId + " in cartId: " + cartId);
//            }
//        } catch (SQLException e) {
//            // Log lỗi SQL
//            System.err.println("[updateQuantity] SQL Error: " + e.getMessage());
//            throw e;
//        }
//    }
//    // Lấy cart_id từ user_id
//    public int getCartIdByUserId(int userId) throws SQLException {
//        String query = "SELECT cart_id FROM cart WHERE user_id = ? ORDER BY created_at DESC LIMIT 1";
//        try (Connection connection = MySQLConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//            stmt.setInt(1, userId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("cart_id");
//            } else {
//                return -1; // Trả về -1 nếu không có cart_id
//            }
//        }
//    }
//
//    public int getProductStock(int productId) throws SQLException {
//        String sql = "SELECT stock FROM product WHERE p_id = ?";
//        try (Connection connection = MySQLConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, productId);
//            System.out.println("[getProductStock] Executing query: " + sql + " with productId: " + productId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    int stock = rs.getInt("stock");
//                    System.out.println("[getProductStock] Stock for productId " + productId + ": " + stock);
//                    return stock;
//                }
//            }
//        }
//        return 0;
//    }
//    public int getQuantityByUserAndProduct(int userId, int productId) throws SQLException {
//        String sql = "SELECT quantity FROM cart_item ci " +
//                "JOIN cart c ON ci.cart_id = c.cart_id " +
//                "WHERE c.user_id = ? AND ci.product = ?";
//        try (Connection connection = MySQLConnection.getConnection();
//             PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            stmt.setInt(2, productId);
//            try (ResultSet rs = stmt.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt("quantity");
//                }
//            }
//        }
//        return 0;
//    }
//
//    public void clearCart(int userId) {
//        String deleteCartItemsQuery = "DELETE FROM cart_item " +
//                "WHERE cart_id IN (SELECT cart_id FROM cart WHERE user_id = ?)";
//        String deleteCartQuery = "DELETE FROM cart WHERE user_id = ?";
//
//        try (Connection connection = getConnection()) {
//            // Xóa dữ liệu trong bảng cart_item
//            try (PreparedStatement statement = connection.prepareStatement(deleteCartItemsQuery)) {
//                statement.setInt(1, userId);
//                statement.executeUpdate();
//            }
//
//            // Xóa dữ liệu trong bảng cart
//            try (PreparedStatement statement = connection.prepareStatement(deleteCartQuery)) {
//                statement.setInt(1, userId);
//                statement.executeUpdate();
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//}
//
//
