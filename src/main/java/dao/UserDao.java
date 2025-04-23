package dao;

import entity.Users;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import static dao.MySQLConnection.getConnection;

public class UserDao {
    public Users loginUser(String email, String password) {
        return null;
    }


    public Users getUserByUsername(String username) {
        Users user = null;
        String query = "SELECT * FROM User WHERE username = ?";  // Câu lệnh SQL để lấy người dùng theo username

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho PreparedStatement
            statement.setString(1, username);

            // Thực thi câu lệnh và lấy kết quả
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user = new Users(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("role"),
                            rs.getString("address")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        boolean isUpdated = false;
        String queryCheck = "SELECT password FROM User WHERE username = ?";
        String queryUpdate = "UPDATE User SET password = ? WHERE username = ?";

        try (Connection connection = getConnection();
             PreparedStatement checkStatement = connection.prepareStatement(queryCheck)) {

            // Thiết lập tham số cho PreparedStatement kiểm tra mật khẩu cũ
            checkStatement.setString(1, username);

            // Thực thi câu lệnh kiểm tra và lấy kết quả
            try (ResultSet rs = checkStatement.executeQuery()) {
                if (rs.next()) {
                    String currentPassword = rs.getString("password");

                    // Kiểm tra mật khẩu cũ có đúng không
                    if (currentPassword.equals(oldPassword)) {
                        // Mật khẩu cũ đúng, cập nhật mật khẩu mới
                        try (PreparedStatement updateStatement = connection.prepareStatement(queryUpdate)) {
                            updateStatement.setString(1, newPassword);
                            updateStatement.setString(2, username);

                            int rowsAffected = updateStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                isUpdated = true;  // Đổi mật khẩu thành công
                            }
                        }
                    } else {
                        System.out.println("Mật khẩu cũ không đúng.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        return isUpdated;  // Trả về true nếu đổi mật khẩu thành công, ngược lại là false
    }

    public boolean deleteAccount(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String storedPassword = getPasswordByUsername(username);
        if (storedPassword == null) {
            return false;
        }

        // Kiểm tra mật khẩu nhập vào có khớp không
        boolean isValid = PasswordUtils.verifyPassword(password, storedPassword);
        if (!isValid) {
            return false;
        }

        // Nếu đúng thì xóa
        String query = "DELETE FROM User WHERE username = ?";
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean checkCurrentPassword(String username, String currentPassword) {
        String sql = "SELECT password FROM User WHERE username = ?"; // Câu lệnh SQL để lấy mật khẩu từ DB
        try (Connection conn = getConnection(); // Mở kết nối tới DB
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username); // Gán giá trị username vào câu truy vấn
            try (ResultSet rs = stmt.executeQuery()) { // Thực thi truy vấn
                if (rs.next()) { // Nếu tồn tại người dùng
                    String storedPassword = rs.getString("password"); // Lấy mật khẩu từ DB
                    return storedPassword.equals(currentPassword); // So sánh mật khẩu từ DB và mật khẩu nhập vào
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ghi log lỗi nếu xảy ra vấn đề với DB
        }
        return false; // Trả về false nếu không tìm thấy hoặc có lỗi xảy ra
    }

    public boolean editUser(Users user) {
        boolean isUpdated = false;
        String query = "UPDATE User SET email = ?, phone = ?, address = ? WHERE username = ?"; // Câu lệnh SQL cập nhật thông tin người dùng

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho câu lệnh PreparedStatement
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getAddress());
            statement.setString(4, user.getUsername());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                isUpdated = true; // Cập nhật thành công
            }

        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu có
        }
        return isUpdated; // Trả về true nếu thành công, false nếu không
    }
    public String getPasswordByUsername(String username) {
        String query = "SELECT password FROM User WHERE username = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("password"); // Trả về chuỗi salt:hash
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean updatePassword(String username, String hashedPasswordWithSalt, String salt) {
        String query = "UPDATE User SET password = ? WHERE username = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, hashedPasswordWithSalt);
            statement.setString(2, username);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }





}
