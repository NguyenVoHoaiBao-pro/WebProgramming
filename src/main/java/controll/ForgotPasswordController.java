package controll;

import dao.MySQLConnection;
import jakarta.servlet.http.HttpServlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;
import javax.mail.*;
import javax.mail.internet.*;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        try (Connection conn = MySQLConnection.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Tạo token đặt lại mật khẩu
                String token = UUID.randomUUID().toString();
                PreparedStatement updateStmt = conn.prepareStatement("UPDATE users SET reset_token = ? WHERE email = ?");
                updateStmt.setString(1, token);
                updateStmt.setString(2, email);
                updateStmt.executeUpdate();

                // Gửi email chứa liên kết đặt lại mật khẩu
                sendResetEmail(email, token);
                request.setAttribute("message", "Email đặt lại mật khẩu đã được gửi!");
            } else {
                request.setAttribute("error", "Email không tồn tại!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("forgotpassword.jsp").forward(request, response);
    }

    private void sendResetEmail(String email, String token) {
        String resetLink = "http://localhost:8080/doanweb/resetpassword.jsp?token=" + token;

        String host = "smtp.gmail.com";
        String from = "phamthaibao57@gmail.com";
        String password = "rjxnslafxlvkbnxz";

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
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
