package controll;

import dao.dao;

import entity.Products;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

import java.util.List;

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

        // Lấy tất cả các sản phẩm (có thể thêm lọc theo danh mục sau)
        List<Products> randomProducts = dao.getRandomProducts();

        String productName = request.getParameter("productName");
        List<Products> searchProducts = (List<Products>) dao.getProductByName(productName);

        // Lấy các sản phẩm theo từng danh mục (ví dụ: category_id = 1, 2, 3, ...)
        List<Products> category1Products = dao.getProductsByCategory(1); // Đùi Gà
        List<Products> category2Products = dao.getProductsByCategory(2); // Cá
        List<Products> category3Products = dao.getProductsByCategory(3); // Thịt

        // Đưa danh sách sản phẩm vào request
        request.setAttribute("randomProductList", randomProducts);
        request.setAttribute("searchProductList", searchProducts);
        request.setAttribute("category1ProductList", category1Products);
        request.setAttribute("category2ProductList", category2Products);
        request.setAttribute("category3ProductList", category3Products);

        // Chuyển tiếp tới JSP để hiển thị
        request.getRequestDispatcher("doanweb/html/index.jsp").forward(request, response);
    }
}