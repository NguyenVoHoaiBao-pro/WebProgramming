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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Users auth = (Users) session.getAttribute("auth");

        // Lấy thông tin giỏ hàng
        ArrayList<CartItem> cartList = new ArrayList<>();
        double totalPrice = 0;
        int totalItems = 0;

        if (auth != null) {
            cartList = (ArrayList<CartItem>) cartDao.getCartByUserId(auth.getId());
            totalPrice = cartDao.getTotalCartPrice(cartList);
            totalItems = cartDao.getTotalItemCount(cartList);
        }

        // Lấy thông tin tỉnh/quận từ form (do người dùng nhập vào)
        String province = request.getParameter("province");
        String district = request.getParameter("district");

        // Tính phí vận chuyển dựa trên tỉnh/quận
        double shippingCost = calculateShippingCost(province, district);

        // Thêm thông tin vào request
        request.setAttribute("cartList", cartList);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("shippingCost", shippingCost);

        // Chuyển tiếp đến trang cart.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/cart.jsp");
        dispatcher.forward(request, response);
    }

    private double calculateShippingCost(String province, String district) {
        double cost = 0;

        // Tính phí vận chuyển dựa trên Tỉnh/Thành phố và Quận/Huyện
        if ("Hanoi".equals(province)) {
            if ("HoanKiem".equals(district)) {
                cost = 30000;  // Hà Nội - Hoàn Kiếm
            } else if ("BaDinh".equals(district)) {
                cost = 25000;  // Hà Nội - Ba Đình
            }
        } else if ("HoChiMinh".equals(province)) {
            if ("TanBinh".equals(district)) {
                cost = 35000;  // TP. Hồ Chí Minh - Tân Bình
            } else if ("Quan1".equals(district)) {
                cost = 40000;  // TP. Hồ Chí Minh - Quận 1
            }
        } else if ("DaNang".equals(province)) {
            if ("HaiChau".equals(district)) {
                cost = 20000;  // Đà Nẵng - Hải Châu
            }
        }

        return cost; // Trả về phí vận chuyển tính được
    }
}
