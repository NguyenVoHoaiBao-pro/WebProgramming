package controll;


import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.google.api.client.auth.oauth2.AuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfo;

@WebServlet("/googlecallback")
public class GoogleCallbackServlet extends HttpServlet {
    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String CLIENT_SECRET = "YOUR_CLIENT_SECRET";
    private static final String REDIRECT_URI = "http://localhost:8080/WebApp/oauth2callback";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(), JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, code, REDIRECT_URI)
                    .execute();

            GoogleCredential credential = new GoogleCredential().setAccessToken(tokenResponse.getAccessToken());
            Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), JSON_FACTORY, credential).setApplicationName("WebApp").build();
            Userinfo userinfo = oauth2.userinfo().get().execute();

            HttpSession session = request.getSession();
            session.setAttribute("user", userinfo.getEmail());

            response.sendRedirect("dashboard.jsp");
        } else {
            response.sendRedirect("login.jsp?error=Login failed");
        }
    }
}
