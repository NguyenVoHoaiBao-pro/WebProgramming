//package controll;
//
//import dao.WishListDao;
//import entity.Products;
//import entity.Users;
//import jakarta.servlet.RequestDispatcher;
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
//@WebServlet("/add-to-wishlist") // Đường dẫn để gọi servlet
//public class AddWishListController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private WishListDao wishListDao = new WishListDao(); // Sử dụng dao cho wishlist
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Kiểm tra xem người dùng đã đăng nhập chưa
//        HttpSession session = request.getSession(false); // false để không tạo session mới nếu không có
//        if (session == null || session.getAttribute("user") == null) {
//            // Nếu chưa đăng nhập, chuyển hướng người dùng đến trang đăng nhập
//            response.sendRedirect("/login"); // Hoặc URL trang đăng nhập của bạn
//            return; // Dừng xử lý tiếp theo
//        }
//
//        // Lấy ID sản phẩm từ tham số truy vấn
//        String productId = request.getParameter("id");
//        String action = request.getParameter("action"); // Lấy hành động (thêm vào danh sách yêu thích, hoặc remove)
//
//        // Kiểm tra nếu ID sản phẩm không hợp lệ
//        if (productId == null || productId.isEmpty()) {
//            response.sendRedirect("error.jsp"); // Chuyển hướng đến trang lỗi nếu không có ID
//            return;
//        }
//
//        // Lấy userId từ session
//        int userId = ((Users) session.getAttribute("user")).getId();
//
//        // Xử lý hành động xóa sản phẩm khỏi danh sách yêu thích
//        if ("remove".equals(action)) {
//            // Xóa sản phẩm khỏi danh sách yêu thích
//            boolean success = wishListDao.removeFromWishlist(userId, Integer.parseInt(productId));
//            if (!success) {
//                response.sendRedirect("error.jsp"); // Nếu có lỗi khi xóa
//                return;
//            }
//        } else {
//            // Thêm sản phẩm vào danh sách yêu thích
//            boolean success = wishListDao.addToWishlist(userId, Integer.parseInt(productId));
//            if (!success) {
//                response.sendRedirect("error.jsp"); // Nếu có lỗi khi thêm sản phẩm
//                return;
//            }
//        }
//
//        // Cập nhật lại danh sách yêu thích sau khi thêm hoặc xóa sản phẩm
//        List<Products> wishlist = wishListDao.getWishlistByUserId(userId);
//        request.setAttribute("wishlist", wishlist);
//
//        // Gửi lại danh sách yêu thích vào request và chuyển hướng đến trang wishlist
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/wishlist");
//        dispatcher.forward(request, response);
//    }
//
//    // Xử lý yêu cầu POST (dùng chung với doGet)
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}
