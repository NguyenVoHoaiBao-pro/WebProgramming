package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        try {
            // Sử dụng kết nối từ connection pool
            Connection connection = MySQLConnection.getConnection();
            // Thực hiện các thao tác với database

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng connection pool khi không sử dụng
            MySQLConnection.shutdown();
        }
    }
}
