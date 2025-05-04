package controll;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/google-login")
public class GoogleLoginServlet extends HttpServlet {
    private static final String CLIENT_ID = "YOUR_CLIENT_ID";
    private static final String REDIRECT_URI = "http://localhost:8080/demo/google-login";
    private static final String SCOPE = "profile email";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String encodedScope = URLEncoder.encode(SCOPE, StandardCharsets.UTF_8);
        String encodedRedirectUri = URLEncoder.encode(REDIRECT_URI, StandardCharsets.UTF_8);

        String authUrl = "https://accounts.google.com/o/oauth2/v2/auth"
                + "?client_id=" + CLIENT_ID
                + "&redirect_uri=" + encodedRedirectUri
                + "&response_type=code"
                + "&scope=" + encodedScope
                + "&access_type=offline"
                + "&prompt=consent";

        response.sendRedirect(authUrl);
    }
}
