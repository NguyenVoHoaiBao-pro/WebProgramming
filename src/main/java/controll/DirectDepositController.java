package controll;

import dao.PaymentDAO;
import entity.CardPayment;
import entity.CashPayment;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/DirectDeposit")
public class DirectDepositController extends HttpServlet {
    private PaymentDAO dao = new PaymentDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/doanweb/html/DirectDeposit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        CashPayment cp = new CashPayment();
        cp.setPayerName(request.getParameter("payerName"));
        cp.setPhoneNumber(request.getParameter("phoneNumber"));
        cp.setAmount(request.getParameter("amount"));
        cp.setAddress(request.getParameter("address"));
        cp.setNotes(request.getParameter("notes"));
        cp.setAgreed(request.getParameter("agree") != null);
        dao.insertCashPayment(cp);
        request.setAttribute("message", "Thanh toán tiền mặt thành công!");
        request.getRequestDispatcher("/doanweb/html/DirectDeposit.jsp").forward(request, response);
    }
}
