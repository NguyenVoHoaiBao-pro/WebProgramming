
package controll;

import entity.Products;
import dao.dao;
import entity.Review;
import dao.ReviewDao;
import dao.UserDao;
import entity.Users;
import dao.MySQLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/detail")
public class DetailController extends HttpServlet {
    private dao d;
    private ReviewDao rd;
    private UserDao ud;

    @Override
    public void init() throws ServletException {
        d = new dao();
        rd = new ReviewDao();
        ud = new UserDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lấy ID sản phẩm từ tham số trong URL
        String productId = request.getParameter("id");
        if (productId != null) {
            Products product = d.getProductById(Integer.parseInt(productId)); // Lấy thông tin sản phẩm theo ID
            request.setAttribute("product", product); // Truyền sản phẩm vào JSP

            // Lấy tất cả đánh giá của sản phẩm từ DAO
            List<Review> reviews = rd.getReviewsByProductId(Integer.parseInt(productId));
            request.setAttribute("reviews", reviews); // Truyền danh sách đánh giá vào JSP

            // Tính toán trung bình rating
            double averageRating = calculateAverageRating(reviews);
            request.setAttribute("averageRating", averageRating); // Truyền trung bình rating vào JSP

            // Đếm số lượng đánh giá cho mỗi mức rating (1 đến 5)
            int[] ratingCounts = new int[5];
            for (Review review : reviews) {
                ratingCounts[review.getRating() - 1]++;
            }
            request.setAttribute("ratingCounts", ratingCounts); // Truyền số lượng đánh giá cho từng mức rating vào JSP

            // Chuyển hướng đến trang JSP để hiển thị sản phẩm và đánh giá
            request.getRequestDispatcher("/doanweb/html/detail.jsp").forward(request, response);
        } else {
            response.sendRedirect("error.jsp"); // Nếu không có ID sản phẩm, chuyển hướng đến trang lỗi
        }
    }

    // Tính toán trung bình rating từ các đánh giá
    private double calculateAverageRating(List<Review> reviews) {
        int totalRating = 0;
        for (Review review : reviews) {
            totalRating += review.getRating();
        }
        return reviews.size() > 0 ? (double) totalRating / reviews.size() : 0;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("user_id");
        int productId = Integer.parseInt(request.getParameter("product_id"));
        int rating = Integer.parseInt(request.getParameter("review-rating"));
        String comment = request.getParameter("review-comment");

        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try (Connection conn = MySQLConnection.getConnection()) {
            String sql = "INSERT INTO reviews (user_id, product_id, rating, comment, review_date) VALUES (?, ?, ?, ?, NOW())";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setInt(2, productId);
                stmt.setInt(3, rating);
                stmt.setString(4, comment);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("detail?id=" + productId);
    }
}
