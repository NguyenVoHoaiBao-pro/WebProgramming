package entity;

public class Categories {
    private int id;
    private String name;

    // Constructor mặc định
    public Categories() {
    }

    // Constructor có tham số
    public Categories(int id, String name) {
        this.id = id;
        this.name = name;
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

    // Override toString() để dễ debug
    @Override
    public String toString() {
        return String.format("Category {id=%d, name='%s'}", id, name);
    }
}
