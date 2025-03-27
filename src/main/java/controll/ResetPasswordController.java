package controll;

import java.io.IOException;
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

@WebServlet("/ResetPassword")
public class ResetPasswordController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("password");
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try (Connection conn = MySQLConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE reset_token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET password = ?, reset_token = NULL WHERE reset_token = ?");
                updateStmt.setString(1, hashedPassword);
                updateStmt.setString(2, token);
                updateStmt.executeUpdate();

                request.setAttribute("message", "Mật khẩu đã được cập nhật!");
            } else {
                request.setAttribute("error", "Token không hợp lệ!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
