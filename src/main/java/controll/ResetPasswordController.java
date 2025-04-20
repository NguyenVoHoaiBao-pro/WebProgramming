package controll;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dao.MySQLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/resetpassword")
public class ResetPasswordController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = URLDecoder.decode(request.getParameter("token"), "UTF-8");
        System.out.println("🔑 Token nhận từ request: " + token);
        String newPassword = request.getParameter("password");
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try (Connection conn = MySQLConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE reset_token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("🔑 Token nhận từ request: " + token);
                System.out.println("🔒 Mật khẩu mới (trước khi hash): " + newPassword);
                System.out.println("🔑 Mật khẩu đã hash: " + hashedPassword);
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE user SET password = ?, reset_token = NULL WHERE reset_token = ?");
                updateStmt.setString(1, hashedPassword);
                updateStmt.setString(2, token);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println(" Mật khẩu đã được cập nhật!");
                    request.setAttribute("message", "Mật khẩu đã được cập nhật!");
                } else {
                    System.out.println(" Không cập nhật được mật khẩu, có thể token không đúng!");
                    request.setAttribute("error", "Lỗi cập nhật mật khẩu!");
                }
            } else {
                System.out.println(" Token không hợp lệ hoặc đã hết hạn!");
                request.setAttribute("error", "Token không hợp lệ!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("doanweb/html/Login.jsp").forward(request, response);
    }
}