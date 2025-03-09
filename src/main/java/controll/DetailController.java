package controll;

import entity.Products;
import dao.dao;  // Đảm bảo import lớp DAO đúng
import entity.Review;
import dao.ReviewDao;
import dao.UserDao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

        String ratingParam = request.getParameter("rating");
        List<Review> reviews;

        if (ratingParam != null && !ratingParam.isEmpty() && !ratingParam.equals("null")) {
            try {
                int rating = Integer.parseInt(ratingParam);
                reviews = rd.getReviewsByRatingAndProductId(rating, Integer.parseInt(productId));
            } catch (NumberFormatException e) {
                // Nếu xảy ra lỗi khi chuyển đổi, lấy tất cả reviews
                reviews = rd.getReviewsByProductId(Integer.parseInt(productId));
            }
        } else {
            reviews = rd.getReviewsByProductId(Integer.parseInt(productId)); // Nếu không có rating, lấy tất cả reviews
        }



        // Tính toán điểm trung bình và số lượng đánh giá
        double averageRating = 0;
        int totalReviews = reviews.size();
        int[] ratingCounts = new int[5]; // Đếm số lượng đánh giá cho từng rating từ 1-5 sao

        if (totalReviews > 0) {
            int totalRating = 0;
            for (Review review : reviews) {
                totalRating += review.getRating();
                ratingCounts[review.getRating() - 1]++;
            }
            averageRating = (double) totalRating / totalReviews;
        }
        List<Products> randomProductList = d.getRandomProducts();
        // Trả về dữ liệu cần thiết để hiển thị trong JSP
        request.setAttribute("averageRating", averageRating);
        request.setAttribute("totalReviews", totalReviews);
        request.setAttribute("ratingCounts", ratingCounts);
        request.setAttribute("reviews", reviews); // Gửi danh sách review cho JSP
        request.setAttribute("randomProductList", randomProductList);

        // Chuyển hướng tới trang chi tiết sản phẩm
        request.getRequestDispatcher("/doanweb/html/detail.jsp").include(request, response);
    }



}