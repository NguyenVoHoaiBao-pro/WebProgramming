package entity;

import java.sql.Timestamp;

public class Wishlist {
    private int wl_id;
    private int user_id;
    private int product_id;
    private Timestamp date_added;

    public Wishlist(int wl_id, int user_id, int product_id, Timestamp date_added) {
        this.wl_id = wl_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.date_added = date_added;
    }

    public int getWl_id() {
        return wl_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public Timestamp getDate_added() {
        return date_added;
    }

    public void setWl_id(int wl_id) {
        this.wl_id = wl_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public void setDate_added(Timestamp date_added) {
        this.date_added = date_added;
    }
}
