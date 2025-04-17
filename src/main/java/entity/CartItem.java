package entity;

public class CartItem {
    private Products product;
    private int quantity;
    private int totalPrice;

    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = product.getPrice() * quantity;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
        updateTotalPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTotalPrice();
    }
    public int getProductId() {
        return product.getId(); // Giả sử Products có phương thức getId()
    }


    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }


    private void updateTotalPrice() {
        this.totalPrice = product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
