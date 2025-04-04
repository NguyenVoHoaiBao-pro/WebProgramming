package entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.List;

public class Products  {
    private int id;
    private String name;
    private String description;
    private int price;
    private int stock;
    private List<String> images; // Danh sách ảnh
    private int categoryId; // Tham chiếu đến Category

    public Products (int id, String name, String description, int price, int stock, List<String> images, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.images = images;
        this.categoryId = categoryId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", images=" + images +
                ", categoryId=" + categoryId +
                '}';
    }
}
