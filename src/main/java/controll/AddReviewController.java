package controll;

import dao.dao;
import entity.Products;
import dao.ReviewDao;
import entity.Review;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/addReview")
public class AddReviewController extends HttpServlet {
    private ReviewDao reviewDao;

    @Override
    public void init() throws ServletException {
        reviewDao = new ReviewDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy thông tin từ form
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int reviewRating = Integer.parseInt(request.getParameter("review-rating"));
        String reviewComment = request.getParameter("review-comment");

        // Lấy user_id từ session
        HttpSession session = request.getSession(false);  // Không tạo session mới nếu chưa có
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }
        int userId = ((Users) session.getAttribute("user")).getId();

        // Tạo đối tượng Review
        Review review = new Review(0, productId, userId, reviewRating, reviewComment, new Timestamp(System.currentTimeMillis()));

        // Thêm review vào cơ sở dữ liệu
        boolean isSuccess = reviewDao.addReview(review);

        if (isSuccess) {
            // Chuyển hướng về trang chi tiết sản phẩm sau khi thêm thành công
            response.sendRedirect("detail?id=" + productId);
        } else {
            response.getWriter().write("Thêm đánh giá thất bại!");
        }
    }
}
