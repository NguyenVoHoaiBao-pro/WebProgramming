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
import java.util.logging.Logger;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutController.class.getName());

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
            // Ghi log sự kiện đăng xuất
            LOGGER.info("User logged out successfully.");

            // Xóa thông tin người dùng khỏi session
            HttpSession session = request.getSession();
            session.invalidate();  // Hủy session hiện tại

            // Chuyển hướng người dùng về trang đăng nhập
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            // Ghi log nếu người dùng chưa đăng nhập
            LOGGER.warning("Attempt to log out without being logged in.");

            // Nếu người dùng chưa đăng nhập, chuyển hướng về trang đăng nhập
            response.setStatus(HttpServletResponse.SC_MOVED_TEMPORARILY);
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    // Hàm kiểm tra đã đăng nhập hay chưa
    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false: không tạo session mới
        return session != null && session.getAttribute("user") != null;
    }
}
