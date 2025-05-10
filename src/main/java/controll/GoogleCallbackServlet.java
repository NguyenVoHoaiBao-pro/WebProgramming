package controll;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

@WebServlet("/googlecallback")
public class GoogleCallbackServlet extends HttpServlet {
    // Lấy từ biến môi trường
    private static final String CLIENT_ID = System.getenv("GOOGLE_CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("GOOGLE_CLIENT_SECRET");
    private static final String REDIRECT_URI = "http://localhost:8080/demo/googlecallback";
    private static final JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
            response.sendRedirect("html/Login.jsp");
            return;
        }

        // Lấy access token từ Google
        GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                new NetHttpTransport(),
                JSON_FACTORY,
                "https://oauth2.googleapis.com/token",
                CLIENT_ID,
                CLIENT_SECRET,
                code,
                REDIRECT_URI)
                .execute();

        // Lấy thông tin người dùng
        GoogleCredential credential = new GoogleCredential().setAccessToken(tokenResponse.getAccessToken());
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), JSON_FACTORY, credential)
                .setApplicationName("Your App Name")
                .build();
        Userinfo userInfo = oauth2.userinfo().get().execute();

        // Tạo session
        HttpSession session = request.getSession();
        session.setAttribute("user_email", userInfo.getEmail());
        session.setAttribute("user_name", userInfo.getName());
        session.setAttribute("role", "google_user");

        response.sendRedirect("home");
    }
}
