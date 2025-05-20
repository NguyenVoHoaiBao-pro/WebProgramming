package controll;

import dao.MySQLConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@WebServlet("/resetpassword")
public class ResetPasswordController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String token = URLDecoder.decode(request.getParameter("token"), "UTF-8");
        String newPassword = request.getParameter("password");

        try (Connection conn = MySQLConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE reset_token = ?");
            stmt.setString(1, token);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Sinh salt và hash mật khẩu
                byte[] salt = generateSalt();
                String hashedPassword = hashPassword(newPassword, salt);

                // Cập nhật mật khẩu đã hash vào database
                PreparedStatement updateStmt = conn.prepareStatement(
                        "UPDATE user SET password = ?, reset_token = NULL WHERE reset_token = ?");
                updateStmt.setString(1, hashedPassword);
                updateStmt.setString(2, token);
                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("✅ Mật khẩu đã được cập nhật thành công!");
                    request.setAttribute("message", "Mật khẩu đã được cập nhật!");
                } else {
                    System.out.println("❌ Không cập nhật được mật khẩu!");
                    request.setAttribute("error", "Lỗi cập nhật mật khẩu!");
                }
            } else {
                System.out.println("❌ Token không hợp lệ hoặc đã hết hạn!");
                request.setAttribute("error", "Token không hợp lệ!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống!");
        }

        request.getRequestDispatcher("doanweb/html/Login.jsp").forward(request, response);
    }

    // 🔐 PBKDF2 Hashing
    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;
        int keyLength = 256;
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return bytesToHex(salt) + ":" + bytesToHex(hash);
    }

    // 🔄 Generate salt
    private byte[] generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }

    // 🔄 Convert bytes to hex string
    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
