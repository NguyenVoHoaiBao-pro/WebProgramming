package controll;

import dao.MySQLConnection;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Properties;

@WebServlet("/forgotpassword")
public class ForgotPasswordController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("doanweb/html/forgotpassword.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        System.out.println("Email nhận được: " + email);


        try (Connection conn = MySQLConnection.getConnection()) {
            if (conn == null) {
                System.out.println("❌ LỖI: Không thể kết nối đến MySQL!");
                request.setAttribute("error", "Lỗi hệ thống! Vui lòng thử lại sau.");
                request.getRequestDispatcher("doanweb/html/forgotpassword.jsp").forward(request, response);
                return;
            }
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Email tồn tại trong database!");

                // Tạo token đặt lại mật khẩu
                String token = UUID.randomUUID().toString();
                System.out.println("📌 Token mới tạo: " + token);
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE user SET reset_token = ? WHERE email = ?");
                updateStmt.setString(1, token);
                updateStmt.setString(2, email);
                int rowsUpdated = updateStmt.executeUpdate();
                System.out.println("✅ Token cập nhật thành công, số dòng ảnh hưởng: " + rowsUpdated);

                // Gửi email chứa liên kết đặt lại mật khẩu
                boolean emailSent = sendResetEmail(email, token);
                if (emailSent) {
                    System.out.println("📩 Email đặt lại mật khẩu đã được gửi thành công đến: " + email);
                    request.setAttribute("message", "Email đặt lại mật khẩu đã được gửi!");
                }else {
                    System.out.println("❌ Không thể gửi email.");
                    request.setAttribute("error", "Không thể gửi email. Vui lòng thử lại.");
                }
            } else {
                System.out.println("❌ Email không tồn tại trong database!");
                request.setAttribute("error", "Email không tồn tại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Lỗi hệ thống! Vui lòng thử lại sau.");
        }
        request.getRequestDispatcher("doanweb/html/forgotpassword.jsp").forward(request, response);
    }

    private boolean sendResetEmail(String email, String token) throws UnsupportedEncodingException {
        String encodedToken = URLEncoder.encode(token, "UTF-8");
        String resetLink = "http://localhost:8080/doanweb/html/resetpassword.jsp?token=" + encodedToken;

        String host = "smtp.gmail.com";
        String from = "phamthaibao57@gmail.com";
        String password = "ektk qrsq oaky tgib";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Đặt lại mật khẩu");
            message.setText("Nhấn vào liên kết sau để đặt lại mật khẩu: " + resetLink);
            Transport.send(message);

            System.out.println("✅ Email đặt lại mật khẩu đã được gửi!");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("❌ LỖI: Không thể gửi email!");
            return false;

        }
    }
}