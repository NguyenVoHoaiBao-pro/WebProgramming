package dao;

import entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dao.MySQLConnection.getConnection;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
public class dao {
    // Phương thức lấy danh sách tất cả sản phẩm
    public List<Products> getAllProducts() {
        List<Products> l = new ArrayList<>();
        String query = "SELECT * FROM product";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("p_id"), // Sử dụng 'p_id' thay vì 'id'
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("img")
                );
                l.add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public List<Categories> getAllCategories() {
        List<Categories> l = new ArrayList<>();
        String query = "SELECT * FROM category";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Categories product = new Categories(
                        rs.getInt("c_id"),
                        rs.getString("name")
                );
                l.add(product);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static Products getLatestProduct() {
        String query = "SELECT * FROM product ORDER BY p_id DESC LIMIT 1";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            if (rs.next()) {
                return new Products(
                        rs.getInt("p_id"), // Sử dụng 'p_id' thay vì 'id'
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("img")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Products> getProductsByCategory(int category_id) {
        List<Products> l = new ArrayList<>();
        String query = "SELECT * FROM product WHERE category_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Gán giá trị của category_id vào câu truy vấn tại vị trí tham số ?
            statement.setInt(1, category_id);
            // Thực thi câu truy vấn
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("p_id"), // Sử dụng 'p_id' thay vì 'id'
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("img")
                );
                l.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }

    public static Products getProductById(int productId) {
        Products product = null;
        String query = "SELECT * FROM product WHERE p_id = ?";  // Câu lệnh SQL để lấy sản phẩm theo ID

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho PreparedStatement
            statement.setInt(1, productId);

            // Thực thi câu lệnh và lấy kết quả
            try (ResultSet rs = statement.executeQuery()) {
                // Nếu có sản phẩm với ID tương ứng
                if (rs.next()) {
                    product = new Products(
                            rs.getInt("p_id"), // Sử dụng 'p_id' thay vì 'id'
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getString("description"),
                            rs.getInt("category_id"),
                            rs.getString("img")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        return product;  // Trả về sản phẩm nếu tìm thấy, nếu không trả về null
    }

    public Products getProductByName(String productName) {
        Products product = null;
        String query = "SELECT * FROM product WHERE name like ?";  // Câu lệnh SQL để lấy sản phẩm theo ID

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập tham số cho PreparedStatement
            statement.setString(1, "%" + productName + "%");

            // Thực thi câu lệnh và lấy kết quả
            try (ResultSet rs = statement.executeQuery()) {
                // Nếu có sản phẩm với ID tương ứng
                if (rs.next()) {
                    product = new Products(
                            rs.getInt("p_id"), // Sử dụng 'p_id' thay vì 'id'
                            rs.getString("name"),
                            rs.getInt("price"),
                            rs.getInt("stock"),
                            rs.getString("description"),
                            rs.getInt("category_id"),
                            rs.getString("img")
                    );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        return product;  // Trả về sản phẩm nếu tìm thấy, nếu không trả về null
    }

    /// ///////////////////
    public void addProduct(String name, double price, int stock, String description, int category_id, String image) {
        String sql = "INSERT INTO product (name, price, stock, description, category_id, image) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setString(4, description);
            stmt.setInt(5, category_id);
            stmt.setString(6, image);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCategories(String name) {
        String sql = "INSERT INTO category (name) VALUES (?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Sửa sản phẩm
    public void updateProduct(int id, String name, int price, int stock, String description, int category_id, String image) {
        String sql = "UPDATE product SET name=?, description=?, price=?, stock=?, image=?, category_id=? WHERE p_id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setString(4, description);
            stmt.setInt(5, category_id);
            stmt.setString(6, image);
            stmt.setInt(7, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Xóa sản phẩm
    public void deleteProduct(int id) {
        String sql = "DELETE FROM product WHERE p_id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCategory(int id) {
        String sql = "DELETE FROM category  WHERE c_id=?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật số lượng tồn kho
    public int updateStock(int productId, int stock) {
        String query = "UPDATE product SET stock = ? WHERE p_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, stock);
            statement.setInt(2, productId);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Tổng giá tiền của giỏ hàng
    public double getTotalCartPrice(List<CartItem> cartList) {
        double sum = 0;

        // Kiểm tra nếu giỏ hàng không rỗng
        if (cartList != null && !cartList.isEmpty()) {
            // Xây dựng danh sách ID sản phẩm từ giỏ hàng
            StringBuilder queryBuilder = new StringBuilder("SELECT p_id, price FROM product WHERE p_id IN (");
            for (int i = 0; i < cartList.size(); i++) {
                queryBuilder.append("?");
                if (i < cartList.size() - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");

            String query = queryBuilder.toString();

            try (Connection connection = getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {

                // Gán giá trị cho từng tham số ID trong truy vấn
                for (int i = 0; i < cartList.size(); i++) {
                    statement.setInt(i + 1, cartList.get(i).getProductId());
                }

                // Thực thi truy vấn và tính tổng giá trị giỏ hàng
                try (ResultSet rs = statement.executeQuery()) {
                    Map<Integer, Double> productPriceMap = new HashMap<>();

                    // Lưu trữ giá sản phẩm trong Map (product_id -> price)
                    while (rs.next()) {
                        productPriceMap.put(rs.getInt("p_id"), rs.getDouble("price"));
                    }

                    // Tính tổng giá trị giỏ hàng dựa trên Map
                    for (CartItem cartItem : cartList) {
                        int productId = cartItem.getProductId(); // Lấy ID sản phẩm
                        Double price = productPriceMap.get(productId); // Lấy giá từ Map

                        if (price != null) {
                            int quantity = cartItem.getQuantity(); // Lấy số lượng từ CartItem
                            double itemTotal = price * quantity; // Tính giá trị sản phẩm (giá * số lượng)
                            sum += itemTotal; // Cộng giá trị sản phẩm vào tổng
                            System.out.println("Product ID: " + productId + ", Quantity: " + quantity + ", Item Total: " + itemTotal);
                        } else {
                            // Trường hợp không tìm thấy giá của sản phẩm trong Map
                            System.err.println("Price not found for Product ID: " + productId);
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace(); // In lỗi nếu có sự cố
            }
        }

        return sum; // Trả về tổng giá trị giỏ hàng
    }



    public Users login(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String storedPassword = rs.getString("password"); // Mật khẩu đã hash lưu trong DB
                    String[] parts = storedPassword.split(":");

                    if (parts.length != 2) {
                        System.out.println("Lỗi dữ liệu mật khẩu trong database.");
                        return null;
                    }

                    byte[] salt = hexToBytes(parts[0]);
                    String hashedPasswordFromDB = parts[1];

                    // Kiểm tra mật khẩu nhập vào
                    String hashedInputPassword = hashPassword(password, salt);

                    if (hashedInputPassword.equals(storedPassword)) {
                        Users user = new Users(
                                rs.getInt("user_id"),
                                rs.getString("username"),
                                rs.getString("email"),
                                rs.getString("password"),
                                rs.getString("phone"),
                                rs.getString("role"),
                                rs.getString("address")
                        );
                        System.out.println("Đăng nhập thành công! Người dùng: " + user);
                        return user;
                    } else {
                        System.out.println("Sai mật khẩu.");
                    }
                } else {
                    System.out.println("Không tìm thấy người dùng.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;
        int keyLength = 256;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return bytesToHex(salt) + ":" + bytesToHex(hash); // Ghép salt và hash lại giống format trong DB
    }

    private byte[] hexToBytes(String hex) {
        int length = hex.length();
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return bytes;
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }



    public Users checkExist(String username) {
        String query = "SELECT * FROM user WHERE username = ?";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    // Trả về đối tượng người dùng nếu tìm thấy
                    Users user = new Users(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password"),
                            rs.getString("phone"),
                            rs.getString("role"),
                            rs.getString("address")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace(); // In lỗi chi tiết ra để dễ dàng debug
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(); // In lỗi nếu có lỗi không phải từ database
        }
        return null; // Trả về null nếu không tìm thấy người dùng
    }

    public void Register(String username, String email, String hashedPassword, String phone, String address) {
        String query = "INSERT INTO user (username, email, password, role, phone, address) VALUES (?, ?, ?, 0, ?, ?)";
        try (Connection connection = MySQLConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Thiết lập các tham số theo thứ tự đúng
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, hashedPassword); // Dùng mật khẩu đã băm
            statement.setString(4, phone);
            statement.setString(5, address);

            // Thực thi câu lệnh và kiểm tra kết quả
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User registered successfully.");
            } else {
                System.out.println("Failed to register user.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Bắt lỗi nếu username hoặc email đã tồn tại (giả sử có ràng buộc UNIQUE trong DB)
            System.err.println("Lỗi: Tên người dùng hoặc email đã tồn tại.");
        } catch (SQLException e) {
            System.err.println("Lỗi cơ sở dữ liệu: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Lỗi không xác định: " + e.getMessage());
        }
    }

    public void addProductWithImages(Products product) {
        String productSql = "INSERT INTO product (name, description, price, stock, image, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        String imageSql = "INSERT INTO product_images (product_id, image_url) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement productStmt = conn.prepareStatement(productSql, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement imageStmt = conn.prepareStatement(imageSql)) {

            conn.setAutoCommit(false);

            // Thêm sản phẩm
            productStmt.setString(1, product.getName());
            productStmt.setString(2, product.getDescription());
            productStmt.setInt(3, product.getPrice());
            productStmt.setInt(4, product.getStock());
            productStmt.setString(5, product.getImage());
            productStmt.setInt(6, product.getCategory_id());
            productStmt.executeUpdate();

            // Lấy ID của sản phẩm vừa thêm
            ResultSet generatedKeys = productStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int productId = generatedKeys.getInt(1);

                // Thêm các ảnh chi tiết
                for (String imageUrl : product.getImages()) {
                    imageStmt.setInt(1, productId);
                    imageStmt.setString(2, imageUrl);
                    imageStmt.addBatch();
                }
                imageStmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý rollback nếu cần thiết
        }
    }
    public void updateProductWithImages(Products product) {
        String productSql = "UPDATE product SET name=?, description=?, price=?, stock=?, image=?, category_id=? WHERE p_id=?";
        String deleteImagesSql = "DELETE FROM product_images WHERE product_id=?";
        String insertImageSql = "INSERT INTO product_images (product_id, image_url) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement productStmt = conn.prepareStatement(productSql);
             PreparedStatement deleteImagesStmt = conn.prepareStatement(deleteImagesSql);
             PreparedStatement insertImageStmt = conn.prepareStatement(insertImageSql)) {

            conn.setAutoCommit(false);

            // Cập nhật thông tin sản phẩm
            productStmt.setString(1, product.getName());
            productStmt.setString(2, product.getDescription());
            productStmt.setInt(3, product.getPrice());
            productStmt.setInt(4, product.getStock());
            productStmt.setString(5, product.getImage());
            productStmt.setInt(6, product.getCategory_id());
            productStmt.setInt(7, product.getId());
            productStmt.executeUpdate();

            // Xóa các ảnh chi tiết cũ
            deleteImagesStmt.setInt(1, product.getId());
            deleteImagesStmt.executeUpdate();

            // Thêm các ảnh chi tiết mới
            for (String imageUrl : product.getImages()) {
                insertImageStmt.setInt(1, product.getId());
                insertImageStmt.setString(2, imageUrl);
                insertImageStmt.addBatch();
            }
            insertImageStmt.executeBatch();

            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            // Xử lý rollback nếu cần thiết
        }
    }

    public List<Products> getRandomProducts() {
        List<Products> products = new ArrayList<>();
        String query = "SELECT * FROM product ORDER BY RAND() LIMIT 4"; // Lấy 4 sản phẩm ngẫu nhiên

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet rs = statement.executeQuery()) {

            // Duyệt qua kết quả trả về và tạo đối tượng Products
            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("p_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("img")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static void main(String[] args) {
        dao d = new dao();
        List<Products> l = d.getAllProducts();
        List<Categories> l1 = d.getAllCategories();
        Products latestProduct = dao.getLatestProduct();
        // Kiểm tra kết quả
        if (latestProduct != null) {
            System.out.println("Sản phẩm mới nhất:");
            System.out.println("ID: " + latestProduct.getId());
            System.out.println("Tên: " + latestProduct.getName());
            System.out.println("Mô tả: " + latestProduct.getDescription());
            System.out.println("Giá: " + latestProduct.getPrice());
            System.out.println("Số lượng: " + latestProduct.getStock());
            System.out.println("Hình ảnh: " + latestProduct.getImage());
            System.out.println("ID Danh mục: " + latestProduct.getCategory_Id());
        } else {
            System.out.println("Không có sản phẩm nào trong cơ sở dữ liệu.");
        }
        for (Products p : l) {
            System.out.println(p);
        }
        for (Categories c : l1) {
            System.out.println(c);
        }
    }
    public double getProductPriceById(int productId) {
        String query = "SELECT price FROM product WHERE p_id = ?";
        double price = 0.0;

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, productId);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    price = rs.getDouble("price");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return price;
    }

    public static List<Products> searchProducts(String keyword) {
        List<Products> list_products = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name LIKE ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, "%" + keyword + "%");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Products product = new Products(
                        rs.getInt("p_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("stock"),
                        rs.getString("description"),
                        rs.getInt("category_id"),
                        rs.getString("img")
                );
                list_products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list_products;
    }

}
