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
//@WebServlet("/wishlist") // Đường dẫn để gọi servlet
//public class WishlistController extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//    private WishListDao wishListDao = new WishListDao(); // Khởi tạo đối tượng wishListDao
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Thiết lập mã hóa cho response và request
//        response.setContentType("text/html; charset=UTF-8");
//        request.setCharacterEncoding("UTF-8");
//
//        // Kiểm tra session để lấy userId
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("user") == null) {
//            // Nếu không có session hoặc người dùng chưa đăng nhập, chuyển hướng đến trang login
//            response.sendRedirect("/login");
//            return;
//        }
//
//        // Lấy userId từ session
//        int userId = ((Users) session.getAttribute("user")).getId();
//        System.out.println("User ID: " + userId);
//
//
//        // Lấy danh sách sản phẩm trong wishlist của người dùng
//        List<Products> wishlist = wishListDao.getWishlistByUserId(userId);
//
//        for (Products p : wishlist) {
//            System.out.println("Product Name: " + p.getName());
//            System.out.println("Product Price: " + p.getPrice());
//            System.out.println("Product Image: " + p.getImage());
//            System.out.println("Product ID: " + p.getId());
//        }
//
//        // Lưu wishlist vào request attribute để chuyển đến JSP
//        System.out.println("Total products in wishlist: " + wishlist.size());
//        request.setAttribute("wishlist", wishlist);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/wishlist.jsp");
//        dispatcher.forward(request, response);
//    }
//}
