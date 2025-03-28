package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class MySQLConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ecommerce_db"; // Thay bằng tên database của bạn
    private static final String USER = "root"; // Thay bằng username MySQL của bạn
    private static final String PASSWORD = "12345"; // Thay bằng mật khẩu MySQL của bạn

    private static HikariDataSource dataSource;

    static {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(URL);
            config.setUsername(USER);
            config.setPassword(PASSWORD);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            // Cấu hình hiệu suất
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setIdleTimeout(30000);
            config.setMaxLifetime(600000);
            config.setConnectionTimeout(20000); // Timeout khi lấy kết nối

            // Tạo nguồn kết nối HikariCP
            dataSource = new HikariDataSource(config);
            System.out.println("✅ Kết nối MySQL được thiết lập thành công!");
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi thiết lập kết nối MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Lấy kết nối từ connection pool
    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = dataSource.getConnection();
            System.out.println("🔗 Kết nối MySQL được lấy thành công!");
            return connection;
        } catch (SQLException e) {
            System.err.println("❌ Lỗi lấy kết nối từ MySQL: " + e.getMessage());
            throw e;
        }
    }

    // Kiểm tra kết nối có khả dụng không
    public static boolean isDatabaseConnected() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("⚠️ Không thể kết nối đến MySQL: " + e.getMessage());
            return false;
        }
    }

    // Đóng connection pool khi không còn sử dụng
    public static void shutdown() {
        if (dataSource != null) {
            dataSource.close();
            System.out.println("🔌 Đã đóng kết nối MySQL.");
        }
    }
}
