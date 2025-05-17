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
        cartDao = new CartDao(); // Khởi tạo DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Users user = (Users) session.getAttribute("auth"); // Đặt chung là "auth"

        ArrayList<CartItem> cartList = new ArrayList<>();
        if (user != null) {
            List<CartItem> sessionCart = (List<CartItem>) session.getAttribute("cartItems");
            if (sessionCart != null) {
                cartList.addAll(sessionCart);
            } else {
                cartList = (ArrayList<CartItem>) cartDao.getCartByUserId(user.getId());
                session.setAttribute("cartItems", cartList);
            }
        } else {
            List<CartItem> guestCart = (List<CartItem>) session.getAttribute("cartItems");
            if (guestCart != null) {
                cartList.addAll(guestCart);
            }
        }

        double totalPrice = cartDao.getTotalCartPrice(cartList);
        int totalItems = cartDao.getTotalItemCount(cartList);

        // Tính giảm giá tự động
        double autoDiscount = 0;
        String discountNote = "Không đủ điều kiện áp dụng giảm tự động";
        if (totalPrice > 500) {
            autoDiscount = totalPrice * 0.20;
            discountNote = "Giảm 20% cho đơn trên 500K";
        } else if (totalPrice > 300) {
            autoDiscount = totalPrice * 0.15;
            discountNote = "Giảm 15% cho đơn trên 300K";
        } else if (totalPrice > 100) {
            autoDiscount = totalPrice * 0.10;
            discountNote = "Giảm 10% cho đơn trên 100K";
        }

        // Tính giảm giá từ voucher (nếu có)
        double voucherDiscount = 0;
        String voucherParam = request.getParameter("voucher");
        if (voucherParam != null && voucherParam.equalsIgnoreCase("VOUCHER")) {
            if (totalPrice > 500) {
                voucherDiscount = totalPrice * 0.20;
            } else if (totalPrice > 300) {
                voucherDiscount = totalPrice * 0.15;
            } else if (totalPrice > 100) {
                voucherDiscount = totalPrice * 0.10;
            }
        }

        double totalDiscount = autoDiscount + voucherDiscount;

        // Tính phí vận chuyển
        String province = request.getParameter("province");
        String district = request.getParameter("district");
        double shippingFee = calculateShippingCost(province, district);

        // Tính tổng thanh toán
        double finalAmount = totalPrice + shippingFee - totalDiscount;

        // Gửi dữ liệu về JSP
        request.setAttribute("cartList", cartList);
        request.setAttribute("totalPrice", totalPrice);
        request.setAttribute("totalItems", totalItems);
        request.setAttribute("autoDiscount", autoDiscount);
        request.setAttribute("voucherDiscount", voucherDiscount);
        request.setAttribute("discount", totalDiscount);
        request.setAttribute("discountNote", discountNote);
        request.setAttribute("shippingFee", shippingFee);
        request.setAttribute("finalAmount", finalAmount);

        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/cart.jsp");
        dispatcher.forward(request, response);
    }

    private double calculateShippingCost(String province, String district) {
        if (province == null || district == null) return 0;

        if ("Hanoi".equals(province)) {
            if ("HoanKiem".equals(district)) return 30000;
            if ("BaDinh".equals(district)) return 25000;
        } else if ("HoChiMinh".equals(province)) {
            if ("TanBinh".equals(district)) return 35000;
            if ("Quan1".equals(district)) return 40000;
        } else if ("DaNang".equals(province)) {
            if ("HaiChau".equals(district)) return 20000;
        }

        return 20000; // phí mặc định
    }
}
