package controll;

import dao.GHNShippingCalculator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/api/shipping-fee")
public class ShippingFeeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int fromDistrict = 1450; // mã cố định (ví dụ kho ở Quận 1, HCM)
        int toDistrict = Integer.parseInt(request.getParameter("toDistrict"));
        String toWardCode = request.getParameter("toWardCode");
        int weight = Integer.parseInt(request.getParameter("weight")); // gram

        String result = GHNShippingCalculator.getShippingFee(fromDistrict, toDistrict, toWardCode, weight);

        response.setContentType("application/json");
        response.getWriter().write(result);
    }
}

