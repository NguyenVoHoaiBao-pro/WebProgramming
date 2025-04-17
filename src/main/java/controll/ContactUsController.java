
package controll;

import dao.MySQLConnection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

@WebServlet("/contact") // Đường dẫn để gọi servlet
public class ContactUsController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/contact.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        // Lấy dữ liệu từ form
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        // Kiểm tra dữ liệu hợp lệ
        if (name == null || name.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                message == null || message.trim().isEmpty()) {

            request.setAttribute("errorMessage", "Vui lòng nhập đầy đủ thông tin.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/contact.jsp");
            dispatcher.forward(request, response);
            return;
        }
        // Lưu tin nhắn vào MySQL
        boolean isSaved = saveToDatabase(name, email, message);

        if (isSaved) {
            // Gửi email thông báo
            sendEmail(name, email, message);
            request.setAttribute("successMessage", "Cảm ơn bạn đã liên hệ. Chúng tôi sẽ phản hồi sớm!");
        } else {
            request.setAttribute("errorMessage", "Có lỗi xảy ra, vui lòng thử lại sau.");
        }

        // Điều hướng về trang contact.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/contact.jsp");
        dispatcher.forward(request, response);
    }

    private boolean saveToDatabase(String name, String email, String message) {
        try (Connection conn = MySQLConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO contact_messages (name, email, message) VALUES (?, ?, ?)")) {

            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.setString(3, message);
            stmt.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendEmail(String name, String email, String message) {
        final String senderEmail = "phamthaibao57@@gmail.com";
        final String senderPassword = "rjxnslafxlvkbnxz";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(senderEmail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("admin@example.com")); // Email nhận
            msg.setSubject("Liên hệ mới từ " + name);
            msg.setText("Tên: " + name + "\nEmail: " + email + "\nTin nhắn: " + message);

            Transport.send(msg);
            System.out.println("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}

