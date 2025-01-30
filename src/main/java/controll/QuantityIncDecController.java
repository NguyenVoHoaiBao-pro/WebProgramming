package controll;

import entity.CartItem;
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
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (action == null || (!"inc".equals(action) && !"dec".equals(action))) {
            response.sendRedirect("cart.jsp");
            return;
        }

        int productId;
        try {
            productId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("cart.jsp");
            return;
        }
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    if ("inc".equals(action)) {
                        item.setQuantity(item.getQuantity() + 1); // Tăng số lượng
                    } else if ("dec".equals(action) && item.getQuantity() > 1) {
                        item.setQuantity(item.getQuantity() - 1); // Giảm số lượng (nhưng không dưới 1)
                    }
                    // Cập nhật tổng tiền
                    item.setTotalPrice(item.getQuantity() * item.getProduct().getPrice());
                    break;
                }
            }

            session.setAttribute("cart", cart);
            // Tính tổng giá tạm tính
            int totalPrice = 0;
            for (CartItem item : cart) {
                totalPrice += item.getTotalPrice();
            }

            session.setAttribute("totalPrice", totalPrice);
            // Chuyển hướng về giỏ hàng
            response.sendRedirect("cart");
        }
    }
}

