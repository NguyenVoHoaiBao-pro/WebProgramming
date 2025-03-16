package controll;

import dao.dao;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private dao daoInstance;

    // Đường dẫn tới trang đăng nhập và trang chủ
    private static final String LOGIN_PAGE = "/doanweb/html/Login.jsp";
    private static final String HOME_PAGE = "/home";

    // Thông báo lỗi
    private static final String LOGIN_ERROR_MESSAGE = "Tài khoản hoặc mật khẩu không đúng";
    private static final String PROCESS_ERROR_MESSAGE = "Đã xảy ra lỗi trong quá trình xử lý đăng nhập";

    @Override
    public void init() {
        daoInstance = new dao(); // Khởi tạo đối tượng DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Chuyển hướng tới trang đăng nhập
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Thiết lập mã hóa để xử lý tiếng Việt
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy thông tin tên người dùng và mật khẩu từ form
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Kiểm tra đăng nhập thông qua DAO
            Users user = daoInstance.login(username, password);

            if (user == null) {
                // Nếu thông tin đăng nhập không đúng, trả về thông báo lỗi
                request.setAttribute("mess", LOGIN_ERROR_MESSAGE);
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            } else {
                // Nếu thông tin đăng nhập đúng, lưu thông tin người dùng vào session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                // Chuyển hướng tới trang chủ
                response.sendRedirect(request.getContextPath() + HOME_PAGE);
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ và ghi log lỗi
            LOGGER.log(Level.SEVERE, "Lỗi trong quá trình đăng nhập cho người dùng: " + username, e);
            request.setAttribute("mess", PROCESS_ERROR_MESSAGE);
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }
}
