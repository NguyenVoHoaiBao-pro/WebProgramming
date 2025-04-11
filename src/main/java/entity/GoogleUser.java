package entity;

import jakarta.persistence.*;

@Entity
@Table(name = "google_users")
public class GoogleUser {
    @Id
    @Column(unique = true, nullable = false)
    private String id;

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    // Constructor mặc định
    public GoogleUser() {}

    // Constructor đầy đủ
    public GoogleUser(String id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
