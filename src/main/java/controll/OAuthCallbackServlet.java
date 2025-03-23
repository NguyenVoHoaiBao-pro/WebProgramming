package controll;

import com.google.api.services.oauth2.model.Userinfo;

@WebServlet("/oauth2callback")
public class OAuthCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/WebApp/oauth2callback";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");

        if (code != null) {
            // Đổi code lấy access token
            String accessToken = getAccessToken(code);

            // Lấy thông tin người dùng từ Google
            GoogleUser user = getUserInfo(accessToken);

            // Lưu thông tin vào session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Chuyển hướng về trang chính
            response.sendRedirect("home.jsp");
        } else {
            response.getWriter().println("Error: No authorization code received.");
        }
    }


    }
}
