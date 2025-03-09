package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db"; // Thay your_database bằng tên database của bạn
    private static final String USER = "root"; // Thay root bằng username MySQL của bạn
    private static final String PASSWORD = "12345"; // Thay password bằng mật khẩu MySQL của bạn

    private static HikariDataSource dataSource;

    static {
        // Cấu hình kết nối HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Cấu hình các thông số bổ sung nếu cần
        config.setMaximumPoolSize(10); // Số kết nối tối đa trong pool
        config.setMinimumIdle(5); // Số kết nối tối thiểu trong pool
        config.setIdleTimeout(30000); // Thời gian chờ tối đa khi kết nối không được sử dụng
        config.setMaxLifetime(600000); // Thời gian tối đa kết nối tồn tại

        // Tạo nguồn kết nối HikariCP
        dataSource = new HikariDataSource(config);
    }

    // Lấy kết nối từ connection pool
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // Đóng connection pool khi không còn sử dụng
    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
        }
    }

}