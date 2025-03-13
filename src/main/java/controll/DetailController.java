package controll;

import entity.Products;
import dao.dao;
import entity.Review;
import dao.ReviewDao;
import dao.UserDao;
import entity.Users;
import dao.MysqlConnection;
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
        String productId = request.getParameter("id");
        Products product = d.getProductById(Integer.parseInt(productId));
        request.setAttribute("product", product);

        List<Review> reviews;
        String ratingParam = request.getParameter("rating");

        if (ratingParam != null && !ratingParam.isEmpty() && !ratingParam.equals("null")) {
            try {
                int rating = Integer.parseInt(ratingParam);
                reviews = rd.getReviewsByRatingAndProductId(rating, Integer.parseInt(productId));
            } catch (NumberFormatException e) {
                reviews = rd.getReviewsByProductId(Integer.parseInt(productId));
            }
        } else {
            reviews = rd.getReviewsByProductId(Integer.parseInt(productId));
        }

        double averageRating = 0;
        int totalReviews = reviews.size();
        int[] ratingCounts = new int[5];

        if (totalReviews > 0) {
            int totalRating = 0;
            for (Review review : reviews) {
                totalRating += review.getRating();
                ratingCounts[review.getRating() - 1]++;
            }
            averageRating = (double) totalRating / totalReviews;
        }
        List<Products> randomProductList = d.getRandomProducts();

        request.setAttribute("averageRating", averageRating);
        request.setAttribute("totalReviews", totalReviews);
        request.setAttribute("ratingCounts", ratingCounts);
        request.setAttribute("reviews", reviews);
        request.setAttribute("randomProductList", randomProductList);

        request.getRequestDispatcher("/doanweb/html/detail.jsp").include(request, response);
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

        try (Connection conn = MysqlConnection.getConnection()) {
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
