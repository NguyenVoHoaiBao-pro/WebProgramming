package controll;

import dao.CartDao;
import dao.dao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(LoginController.class.getName());
    private dao daoInstance;

    private static final String LOGIN_PAGE = "/doanweb/html/Login.jsp";
    private static final String HOME_PAGE = "/home";
    private static final String LOGIN_ERROR_MESSAGE = "Tài khoản hoặc mật khẩu không đúng";
    private static final String PROCESS_ERROR_MESSAGE = "Đã xảy ra lỗi trong quá trình xử lý đăng nhập";

    @Override
    public void init() {
        daoInstance = new dao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
    }

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
                request.setAttribute("mess", LOGIN_ERROR_MESSAGE);
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                restoreCartFromDatabase(user.getId(), request);

                response.sendRedirect(request.getContextPath() + HOME_PAGE);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi trong quá trình đăng nhập cho người dùng: " + username, e);
            request.setAttribute("mess", PROCESS_ERROR_MESSAGE);
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }

    private void restoreCartFromDatabase(int userId, HttpServletRequest request) {
        CartDao cartDao = new CartDao();
        List<CartItem> cartItems = cartDao.getCartItemsByUserId(userId);
        HttpSession session = request.getSession();
        session.setAttribute("cartItems", cartItems);
    }
}
