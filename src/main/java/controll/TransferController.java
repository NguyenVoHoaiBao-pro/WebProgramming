package controll;

import dao.PaymentDAO;
import entity.CardPayment;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Transfer")
public class TransferController extends HttpServlet {
    private PaymentDAO dao = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/doanweb/html/Transfer.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        CardPayment cp = new CardPayment();
        cp.setCardName(request.getParameter("cardName"));
        cp.setCardNumber(request.getParameter("cardNumber"));

        // Chuyển đổi expiryDate từ yyyy-MM -> yyyy-MM-01 -> java.sql.Date
        String expiryDateStr = request.getParameter("expiryDate");
        try {
            cp.setExpiryDate(java.sql.Date.valueOf(expiryDateStr + "-01"));
        } catch (IllegalArgumentException e) {
            request.setAttribute("message", "Lỗi định dạng ngày hết hạn.");
            request.getRequestDispatcher("/doanweb/html/Transfer.jsp").forward(request, response);
            return;
        }

        cp.setCvv(request.getParameter("cvv"));

        dao.insertCardPayment(cp);

        request.setAttribute("message", "Thanh toán thẻ thành công!");
        request.getRequestDispatcher("/doanweb/html/Transfer.jsp").forward(request, response);
    }
}