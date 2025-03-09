package controll;

import dao.dao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleLogout(request, response);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isLoggedIn(request)) {
            // Xóa thông tin người dùng khỏi session
            HttpSession session = request.getSession();
            session.invalidate();  // Hủy session hiện tại

            // Chuyển hướng người dùng về trang đăng nhập
            response.sendRedirect("/login");
        } else {
            // Nếu người dùng chưa đăng nhập, chuyển hướng về trang chủ hoặc đăng nhập
            response.sendRedirect("/login");
        }
    }

    // Hàm kiểm tra đã đăng nhập hay chưa
    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false: không tạo session mới
        return session != null && session.getAttribute("user") != null;
    }
}
