package dao;

import entity.Products;
import dao.dao;
import entity.Wishlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishListDao {
    public boolean addToWishlist(int userId, int productId) {
        String query = "INSERT INTO wishlist (user_id, product_id, date_added) VALUES (?, ?, NOW())";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeFromWishlist(int userId, int productId) {
        String query = "DELETE FROM wishlist WHERE user_id = ? AND product_id = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            statement.setInt(2, productId);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;  // Nếu có ít nhất 1 dòng bị xóa
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Products> getWishlistByUserId(int userId) {
        List<Products> wishlist = new ArrayList<>();
        String query = "SELECT product_id FROM wishlist WHERE user_id = ?";

        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            stmt.setInt(1, userId);
            System.out.println("Executing query for user_id: " + userId);  // In ra user_id đang truy vấn
            ResultSet rs = stmt.executeQuery();

            // Lấy sản phẩm từ wishlist
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                System.out.println("Found product_id: " + productId);  // In ra product_id tìm thấy trong wishlist

                // Sử dụng phương thức getProductById để lấy thông tin sản phẩm
                Products product = dao.getProductById(productId);
                if (product != null) {
                    System.out.println("Product found - ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Image: " + product.getImage());
                    wishlist.add(product); // Thêm sản phẩm vào danh sách
                } else {
                    System.out.println("No product found for product_id: " + productId); // Trường hợp không tìm thấy sản phẩm
                }
            }

        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("Total products in wishlist: " + wishlist.size());  // In ra tổng số sản phẩm trong wishlist
        return wishlist;
    }

}
