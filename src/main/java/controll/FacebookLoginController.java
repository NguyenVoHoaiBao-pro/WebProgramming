package controll;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

@WebServlet("/facebookLogin")
public class FacebookLoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger.getLogger(FacebookLoginController.class.getName());

    private static final String LOGIN_PAGE = "/doanweb/html/Login.jsp"; // Trang đăng nhập
    private static final String HOME_PAGE = "/home"; // Trang chủ sau khi đăng nhập

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        // Lấy accessToken từ request
        String accessToken = request.getParameter("accessToken");

        // Kiểm tra nếu accessToken không hợp lệ
        if (accessToken == null || accessToken.isEmpty()) {
            response.getWriter().write("Access token không hợp lệ.");
            return;
        }

        try {
            // Gửi yêu cầu tới Facebook Graph API để lấy thông tin người dùng
            String url = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");

            // Đọc dữ liệu phản hồi từ Facebook API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            reader.close();

            // Phân tích JSON từ Facebook
            JSONObject fbUser = new JSONObject(responseBuilder.toString());
            String fbId = fbUser.getString("id");
            String name = fbUser.getString("name");
            String email = fbUser.optString("email", ""); // Sử dụng optString để tránh lỗi nếu email không có

            // Tạo session và lưu thông tin người dùng vào session
            HttpSession session = request.getSession();
            session.setAttribute("fbId", fbId);
            session.setAttribute("name", name);
            session.setAttribute("email", email);
            session.setAttribute("role", "facebook_user"); // Có thể dùng để phân quyền nếu cần

            // Chuyển hướng người dùng về trang chủ
            response.sendRedirect(request.getContextPath() + HOME_PAGE);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đăng nhập bằng Facebook", e);
            request.setAttribute("mess", "Đăng nhập bằng Facebook thất bại. Vui lòng thử lại.");
            request.getRequestDispatcher(LOGIN_PAGE).forward(request, response);
        }
    }
}
