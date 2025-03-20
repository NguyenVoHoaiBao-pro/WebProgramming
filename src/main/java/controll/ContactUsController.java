package controll;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

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

        // Giả sử lưu tin nhắn vào cơ sở dữ liệu hoặc xử lý khác (ở đây chỉ giả lập)
        System.out.println("Tên: " + name);
        System.out.println("Email: " + email);
        System.out.println("Tin nhắn: " + message);

        // Gửi phản hồi thành công
        request.setAttribute("successMessage", "Cảm ơn bạn đã liên hệ. Chúng tôi sẽ phản hồi sớm!");
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/contact.jsp");
        dispatcher.forward(request, response);
    }
}