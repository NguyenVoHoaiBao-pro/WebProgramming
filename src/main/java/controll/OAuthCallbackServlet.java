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

    private String getAccessToken(String code) throws IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                transport, jsonFactory, CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI)
                .execute();

        return tokenResponse.getAccessToken();
    }

    private GoogleUser getUserInfo(String accessToken) throws IOException {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        Oauth2 oauth2 = new Oauth2.Builder(transport, jsonFactory, new Credential(BearerToken.authorizationHeaderAccessMethod())
                .setAccessToken(accessToken))
                .setApplicationName("WebApp")
                .build();

        Userinfo userinfo = oauth2.userinfo().get().execute();

        return new GoogleUser(userinfo.getId(), userinfo.getName(), userinfo.getEmail());
    }
}
