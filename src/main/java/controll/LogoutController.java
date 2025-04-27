package controll;

import dao.CartDao;
import entity.Cart;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(LogoutController.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleLogout(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        handleLogout(request, response);
    }

    private void handleLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (isLoggedIn(request)) {
            HttpSession session = request.getSession();
            Users user = (Users) session.getAttribute("user");

            // ✅ Lưu giỏ hàng vào CSDL trước khi đăng xuất
            saveCartToDatabase(request, user.getId(), session.getId());

            // Ghi log sự kiện đăng xuất
            LOGGER.info("User " + user.getUsername() + " logged out successfully.");

            // Hủy session
            session.invalidate();


            response.sendRedirect(request.getContextPath() + "/login");
        } else {

            LOGGER.warning("Attempt to log out without being logged in.");
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }


    private boolean isLoggedIn(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // false: không tạo mới nếu không có
        return session != null && session.getAttribute("user") != null;
    }


    private void saveCartToDatabase(HttpServletRequest request, int userId, String sessionId) {
        HttpSession session = request.getSession();
        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

        if (cartItems != null) {
            Cart cart = new Cart();
            cart.setUser_id(userId); // Đảm bảo Cart có setter này
            cart.setCartItems(cartItems); // Đảm bảo Cart có setter này

            CartDao cartDao = new CartDao();
            try {
                cartDao.saveCart(cart, sessionId, cartItems);
            } catch (SQLException e) {
                e.printStackTrace(); // Hoặc log lỗi đúng chuẩn
            }
        }
    }

}
