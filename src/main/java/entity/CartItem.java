package entity;

import java.sql.Timestamp;
import dao.dao;

public class CartItem {
    private Cart cart;
    private int quantity;

    public CartItem(Cart cart, int quantity) {
        this.cart = cart;
        this.quantity = quantity;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCartId() {
        return cart.getC_id();
    }

    public int getUserId() {
        return cart.getUser_id();
    }

    public int getProductId() {
        return cart.getProduct_id();
    }

    public Timestamp getCreatedAt() {
        return cart.getCreated_at();
    }


    public void setCartAttributes(int c_id, int user_id, int product_id, Timestamp created_at, int quantity) {
        this.cart.setC_id(c_id);
        this.cart.setUser_id(user_id);
        this.cart.setProduct_id(product_id);
        this.cart.setCreated_at(created_at);
        this.setQuantity(quantity);
    }
    public Products getProduct() {
        return dao.getProductById(cart.getProduct_id());
    }


}
