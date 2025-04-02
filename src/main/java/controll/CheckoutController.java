//package controll;
//
//import dao.CartDao;
//import dao.OrderDao;
//import dao.dao;
//import entity.CartItem;
//import entity.Users;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/checkout")
//public class CheckoutController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    private final CartDao cartDao;
//    private final OrderDao orderDao;
//    private final dao dao;
//
//    public CheckoutController() {
//        super();
//        cartDao = new CartDao();
//        orderDao = new OrderDao();
//        dao = new dao();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//
//        if (session == null || session.getAttribute("user") == null) {
//            response.sendRedirect("/login");
//            return;
//        }
//
//        Users user = (Users) session.getAttribute("user");
//        int userId = user.getId();
//
//        // Lấy giỏ hàng của người dùng từ CartDao
//        List<CartItem> cart = cartDao.getCartByUserId(userId);
//
//        // Tính toán tổng số lượng và tổng giá trị của giỏ hàng
//        int totalItems = cart.stream().mapToInt(CartItem::getQuantity).sum();
//        double totalPrice = cart.stream()
//                .mapToDouble(cartItem -> dao.getProductPriceById(cartItem.getProductId()) * cartItem.getQuantity())
//                .sum();
//
//        // Lưu vào session để hiển thị trong checkout.jsp
//        session.setAttribute("cart", cart);
//        session.setAttribute("totalItems", totalItems);
//        session.setAttribute("totalPrice", totalPrice);
//
//        request.getRequestDispatcher("doanweb/html/checkout.jsp").forward(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//
//        if (session == null || session.getAttribute("user") == null) {
//            response.sendRedirect("/login");
//            return;
//        }
//
//        String action = request.getParameter("action");
//        Users user = (Users) session.getAttribute("user");
//        int userId = user.getId();
//
//        if ("directPayment".equals(action)) {
//            // Xử lý thanh toán trực tiếp
//            double totalPrice = Double.parseDouble(request.getParameter("orderAmount"));
//            orderDao.createOrderFromCart(userId, totalPrice);
//            cartDao.clearCart(userId);
//            response.setStatus(HttpServletResponse.SC_OK);
//        } else if ("cardPayment".equals(action)) {
//            // Điều hướng đến trang checkout
//            response.sendRedirect("/checkout");
//        }
//    }
//}
