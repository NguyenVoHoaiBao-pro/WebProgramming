package dao;

import entity.Review;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.MySQLConnection.getConnection;

public class ReviewDao {
    public List<Review> getAllReviews() {
        List<Review> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date")
                );
                reviewList.add(review);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviewList;
    }

    public List<Review> getReviewsByProductId(int productId) {
        List<Review> reviewList = new ArrayList<>();
        String query = "SELECT * FROM review WHERE product_id = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    Review review = new Review(
                            rs.getInt("review_id"),
                            rs.getInt("product_id"),
                            rs.getInt("user_id"),
                            rs.getInt("rating"),
                            rs.getString("comment"),
                            rs.getTimestamp("review_date")
                    );
                    reviewList.add(review);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviewList;
    }

    public List<Review> getReviewsByRating(int rating) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM review WHERE rating = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, rating); // Đặt tham số rating
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                reviews.add(new Review(
                        rs.getInt("review_id"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    public List<Review> getReviewsByRatingAndProductId(int rating, int productId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE product_id = ? AND rating = ?"; // Câu truy vấn SQL

        // Thực thi câu truy vấn và xử lý kết quả
        try (Connection connection = getConnection(); // Lấy kết nối cơ sở dữ liệu
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Thiết lập các tham số cho câu truy vấn
            statement.setInt(1, productId);
            statement.setInt(2, rating);

            // Thực thi câu truy vấn
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    // Đọc kết quả và thêm vào danh sách reviews
                    reviews.add(new Review(
                            resultSet.getInt("review_id"),
                            resultSet.getInt("product_id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("rating"),
                            resultSet.getString("comment"),
                            resultSet.getTimestamp("review_date")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý lỗi nếu cần
        }

        return reviews;
    }



    public boolean addReview(Review review) {
        String query = "INSERT INTO review (product_id, user_id, rating, comment, review_date) VALUES (?, ?, ?, ?, ?)";
        System.out.println("Adding review with Product ID: " + review.getProduct_id() +
                ", User ID: " + review.getUser_id() + ", Rating: " + review.getRating() +
                ", Comment: " + review.getComment()); // In ra thông tin review

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, review.getProduct_id());
            stmt.setInt(2, review.getUser_id());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Review successfully added!");
                return true; // Trả về true nếu review đã được thêm thành công
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Review> getReviewByProductIdAndRating(int productId, int rating) {
        List<Review> reviews = new ArrayList<>();
        String query = "SELECT * FROM review WHERE product_id = ? AND rating = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, rating);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                        rs.getInt("review_id"),
                        rs.getInt("product_id"),
                        rs.getInt("user_id"),
                        rs.getInt("rating"),
                        rs.getString("comment"),
                        rs.getTimestamp("review_date")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }



}
