package controll;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.dao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import dao.CartDao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
    private static final String RECAPTCHA_SECRET_KEY = "6LfvZxYrAAAAAP9GQcgG20yE2ZlzObqfE4TydHNA";

    private static final String LOGIN_ERROR_MESSAGE = "Tài khoản hoặc mật khẩu không đúng";
    private static final String PROCESS_ERROR_MESSAGE = "Đã xảy ra lỗi trong quá trình xử lý đăng nhập";
    private static final String RECAPTCHA_ERROR_MESSAGE = "Vui lòng xác minh bạn không phải là robot";

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
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");

        try {
            // ✅ Xác minh reCAPTCHA
            boolean isCaptchaVerified = verifyRecaptcha(gRecaptchaResponse);

            if (!isCaptchaVerified) {
                request.setAttribute("mess", RECAPTCHA_ERROR_MESSAGE);
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
                return;
            }
            Users user = daoInstance.login(username, password);

            if (user == null) {
                request.setAttribute("mess", LOGIN_ERROR_MESSAGE);
                request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("role", user.getRole());

                // 👉 Thêm đoạn này để khôi phục giỏ hàng từ DB
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
    private boolean verifyRecaptcha(String gRecaptchaResponse) throws IOException {
        if (gRecaptchaResponse == null || gRecaptchaResponse.isEmpty()) {
            return false;
        }

        String url = "https://www.google.com/recaptcha/api/siteverify";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Gửi POST
        con.setRequestMethod("POST");
        con.setDoOutput(true);

        String postParams = "secret=" + RECAPTCHA_SECRET_KEY + "&response=" + gRecaptchaResponse;

        try (OutputStream os = con.getOutputStream()) {
            os.write(postParams.getBytes());
            os.flush();
        }

        // Đọc kết quả trả về
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response.toString(), JsonObject.class);
        return json.get("success").getAsBoolean();
    }

}
