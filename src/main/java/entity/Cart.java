package entity;

import java.sql.Timestamp;

public class Cart {
    private int c_id;
    private int user_id;
    private int product_id;
    private Timestamp created_at;

    // Constructor
    public Cart(int c_id, int user_id, int product_id,Timestamp created_at) {
        this.c_id = c_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.created_at = created_at;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "Cart" +
                "c_id=" + c_id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", created_at=" + created_at +
                '}';
    }
}
