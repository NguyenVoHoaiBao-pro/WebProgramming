package controll;

import dao.dao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private dao daoInstance;

    @Override
    public void init() {
        daoInstance = new dao(); // Khởi tạo instance của DAO
    }

    // Xử lý phương thức GET (hiển thị trang login)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/doanweb/html/Login.jsp").forward(request, response);
    }

    // Xử lý phương thức POST (xử lý đăng nhập)
    @Override

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            Users user = daoInstance.login(username, password);

            if (user == null) {
                request.setAttribute("mess", "Tài khoản hoặc mật khẩu sai");
                request.getRequestDispatcher("/doanweb/html/Login.jsp").forward(request, response);
            } else {
                // Lưu thông tin người dùng vào session
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("mess", "Đã xảy ra lỗi trong quá trình xử lý đăng nhập");
            request.getRequestDispatcher("/doanweb/html/Login.jsp").forward(request, response);
        }
    }

}

