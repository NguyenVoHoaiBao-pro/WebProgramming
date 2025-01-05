import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {
    private final String serverName = "localhost"; // Địa chỉ server
    private final String dbName = "your_database_name"; // Tên database
    private final String portNumber = "3306"; // Port MySQL mặc định
    private final String userID = "your_username"; // Tên người dùng
    private final String password = "your_password"; // Mật khẩu

    public Connection getConnection() throws Exception {
        // URL để kết nối với database
        String url = "jdbc:mysql://" + serverName + ":" + portNumber + "/" + dbName;
        // Tải driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Tạo kết nối
        return DriverManager.getConnection(url, userID, password);
    }
    private final String serverName = "localhost";
    private final String dbName = "";
}
