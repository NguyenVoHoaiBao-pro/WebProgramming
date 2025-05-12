package controll;

import dao.CartDao;
import entity.CartItem;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CartDao cartDao;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Users auth = (Users) session.getAttribute("auth");

        if (auth != null) {
            ArrayList<CartItem> cartList = (ArrayList<CartItem>) cartDao.getCartByUserId(auth.getId());

            double totalPrice = cartDao.getTotalCartPrice(cartList);
            int totalItems = cartDao.getTotalItemCount(cartList);

            request.setAttribute("cartList", cartList);
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("totalItems", totalItems);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/cart.jsp");
        dispatcher.forward(request, response);

    }

}