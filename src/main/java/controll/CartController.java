package controll;

import dao.CartDao;
import entity.CartItem;
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

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDao cartDao;

    @Override
    public void init() throws ServletException {
        cartDao = new CartDao(); // ✅ Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("user"); // ✅ Tên đúng với LoginController

        ArrayList<CartItem> cartList = new ArrayList<>();

        if (user != null) {
            // ✅ Người dùng đã đăng nhập → lấy từ session (đã được restore khi login)
            List<CartItem> sessionCart = (List<CartItem>) session.getAttribute("cartItems");
            if (sessionCart != null) {
                cartList.addAll(sessionCart);
            } else {
                // Trường hợp hiếm nếu chưa có trong session thì lấy từ DB
                cartList = (ArrayList<CartItem>) cartDao.getCartByUserId(user.getId());
                session.setAttribute("cartItems", cartList);
            }
        } else {
            // ✅ Người dùng chưa đăng nhập → có thể xử lý giỏ hàng tạm trong session (nếu muốn)
            List<CartItem> guestCart = (List<CartItem>) session.getAttribute("cartItems");
            if (guestCart != null) {
                cartList.addAll(guestCart);
            }
        }

        double totalPrice = cartDao.getTotalCartPrice(cartList);
        int totalItems = cartDao.getTotalItemCount(cartList);

        request.setAttribute("cartList", cartList);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalItems", totalItems);

        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/cart.jsp");
        dispatcher.forward(request, response);
    }
}
