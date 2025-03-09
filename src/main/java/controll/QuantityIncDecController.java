package controll;

import dao.CartDao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/quantity-inc-dec")
public class QuantityIncDecController extends HttpServlet {
    private final CartDao cartDao = new CartDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int productId = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        int userId = ((Users) session.getAttribute("user")).getId();

        try {
            List<CartItem> cartItems = cartDao.getCartByUserId(userId);
            int cartId = -1;

            for (CartItem item : cartItems) {
                if (item.getProduct().getId() == productId) {
                    cartId = item.getCartId();
                    break;
                }
            }

            if (cartId == -1) {
                response.sendRedirect("/cart?error=Sản phẩm không tồn tại trong giỏ hàng");
                return;
            }

            int currentQuantity = cartDao.getQuantity(cartId, productId);
            int productStock = cartDao.getProductStock(productId); // Lấy stock của sản phẩm

            if ("inc".equals(action) && currentQuantity < productStock) {
                cartDao.updateQuantity(cartId, productId, currentQuantity + 1);
            } else if ("dec".equals(action) && currentQuantity > 1) {
                cartDao.updateQuantity(cartId, productId, currentQuantity - 1);
            } else if ("dec".equals(action) && currentQuantity == 1) {
                cartDao.removeFromCart(userId, productId);
            }

            response.sendRedirect("/cart");

        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thông tin sản phẩm không hợp lệ.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi cập nhật giỏ hàng.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
