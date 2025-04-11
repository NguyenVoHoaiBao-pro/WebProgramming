package controll;

import entity.CartItem;
import dao.dao;
import entity.Products;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private dao dao = new dao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        String productIdParam = request.getParameter("id");
        String action = request.getParameter("action");

        if (productIdParam == null || productIdParam.isEmpty()) {
            response.sendRedirect("error.jsp");
            return;
        }

        int productId = Integer.parseInt(productIdParam);
        Products product = dao.getProductById(productId);
        if (product == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        Users user = (Users) session.getAttribute("user");

        if ("remove".equals(action)) {
            cart.removeIf(item -> item.getProduct().getId() == productId);
            dao.removeFromCartInDB(user.getId(), productId);
        } else {
            boolean found = false;
            for (CartItem item : cart) {
                if (item.getProduct().getId() == productId) {
                    item.setQuantity(item.getQuantity() + 1);
                    found = true;
                    break;
                }
            }

            if (!found) {
                cart.add(new CartItem(product, 1));
            }

            // Luôn cập nhật DB dù là thêm mới hay tăng số lượng
            dao.addToCartInDB(user.getId(), productId, 1);
        }

        // Gợi ý sản phẩm
        List<Products> randomProducts = dao.getRandomProducts();
        request.setAttribute("randomProductList", randomProducts);

        // Cập nhật session: tổng số lượng và giá
        int totalItems = 0;
        for (CartItem item : cart) {
            totalItems += item.getQuantity();
        }
        session.setAttribute("totalItems", totalItems);

        double totalPrice = dao.getTotalCartPrice((ArrayList<CartItem>) cart);
        session.setAttribute("totalPrice", totalPrice);

        // Điều hướng
        if ("buy-now".equals(action) || "remove".equals(action)) {
            response.sendRedirect("/cart");
        } else if ("add-cart".equals(action)) {
            response.sendRedirect("/shop");
        } else {
            request.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/detail");
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
