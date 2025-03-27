package controll;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SendResetLinkServlet extends HttpServlet {

    // Đây là phương thức xử lý yêu cầu từ form người dùng nhập email
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        // Kiểm tra xem email có hợp lệ hay không (ví dụ: kiểm tra email trong CSDL)
        if (isEmailValid(email)) {
            String token = generateToken();

            // Lưu token vào CSDL (hoặc bộ nhớ tạm thời) với email của người dùng
            saveTokenToDatabase(email, token);

            // Gửi email với token
            sendResetEmail(email, token);

            // Thông báo thành công và hướng dẫn người dùng
            response.getWriter().println("Đã gửi email để đặt lại mật khẩu.");
        } else {
            response.getWriter().println("Email không hợp lệ hoặc không tồn tại.");
        }
    }

    // Kiểm tra xem email có hợp lệ không (ví dụ: kiểm tra trong cơ sở dữ liệu)
    private boolean isEmailValid(String email) {
        // Kiểm tra email có tồn tại trong cơ sở dữ liệu không
        return true;
    }

    // Tạo một token ngẫu nhiên để gửi email
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Lưu token vào cơ sở dữ liệu
    private void saveTokenToDatabase(String email, String token) {
        // Lưu token và email vào cơ sở dữ liệu
        // Ví dụ: `UPDATE users SET reset_token = ? WHERE email = ?`
    }

    // Gửi email với token
    private void sendResetEmail(String email, String token) {
        String host = "smtp.gmail.com";
        String user = "your-email@gmail.com";
        String password = "your-email-password";

        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject("Đặt lại mật khẩu của bạn");

            // Cấu trúc email với link chứa token
            String resetLink = "http://localhost:8080/reset-password?token=" + token;
            message.setText("Nhấn vào đường dẫn sau để đặt lại mật khẩu của bạn: " + resetLink);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
