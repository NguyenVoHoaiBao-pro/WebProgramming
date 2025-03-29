package entity;

import java.sql.Timestamp;

public class Review {
    private int review_id;
    private int user_id;
    private int product_id;
    private int rating;
    private String comment;
    private Timestamp review_date;
    private String username;


    public Review(int review_id, int product_id ,int user_id, int rating, String comment, Timestamp review_date) {
        this.review_id = review_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.rating = rating;
        this.comment = comment;
        this.review_date = review_date;
    }

    public int getReview_id() {
        return review_id;
    }

    public void setReview_id(int review_id) {
        this.review_id = review_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getReview_date() {
        return review_date;
    }

    public void setReview_date(Timestamp review_date) {
        this.review_date = review_date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
