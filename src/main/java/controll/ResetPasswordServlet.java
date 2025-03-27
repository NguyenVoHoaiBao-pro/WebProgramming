package controll;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ResetPasswordServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        String newPassword = request.getParameter("newPassword");

        // Kiểm tra token và cập nhật mật khẩu mới
        if (isTokenValid(token)) {
            // Cập nhật mật khẩu mới vào cơ sở dữ liệu
            updatePassword(token, newPassword);
            response.getWriter().println("Mật khẩu đã được cập nhật.");
        } else {
            response.getWriter().println("Token không hợp lệ.");
        }
    }

    // Kiểm tra token hợp lệ
    private boolean isTokenValid(String token) {
        // Kiểm tra token trong cơ sở dữ liệu
        return true;
    }

    // Cập nhật mật khẩu trong cơ sở dữ liệu
    private void updatePassword(String token, String newPassword) {
        // Cập nhật mật khẩu mới vào CSDL sau khi xác minh token hợp lệ
        // Ví dụ: `UPDATE users SET password = ? WHERE reset_token = ?`
    }
}
