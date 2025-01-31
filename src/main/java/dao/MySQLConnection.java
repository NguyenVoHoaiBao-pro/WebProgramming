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

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // Cấu hình các thông số bổ sung nếu cần
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(600000);

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