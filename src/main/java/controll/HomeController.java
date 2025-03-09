package controll;

import dao.dao;
import entity.Categories;
import entity.Products;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private dao dao;

    @Override
    public void init() throws ServletException {
        dao = new dao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Đảm bảo sử dụng mã hóa UTF-8 khi nhận tham số từ client
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");


        // Lấy các sản phẩm khác (ngẫu nhiên, theo danh mục...)
        List<Products> randomProducts = dao.getRandomProducts();
        List<Products> category1Products = dao.getProductsByCategory(1); // Đùi Gà
        List<Products> category2Products = dao.getProductsByCategory(2); // Cá
        List<Products> category3Products = dao.getProductsByCategory(3); // Thịt

        // Truyền các danh sách sản phẩm vào request
        request.setAttribute("randomProductList", randomProducts);
        request.setAttribute("category1ProductList", category1Products);
        request.setAttribute("category2ProductList", category2Products);
        request.setAttribute("category3ProductList", category3Products);

        // Chuyển tiếp tới JSP để hiển thị
        request.getRequestDispatcher("/doanweb/html/index.jsp").forward(request, response);
    }
}

