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

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@WebServlet("/shop") // Đường dẫn để gọi servlet
public class ShopController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private dao productDAO;  // Đổi tên dao thành productDAO để rõ ràng hơn

    @Override
    public void init() throws ServletException {
        productDAO = new dao();  // Khởi tạo đối tượng ProductDAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String categoryId = request.getParameter("categoryId");

        // Nếu categoryId không có hoặc rỗng, chuyển hướng đến URL mặc định
        if (categoryId == null || categoryId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/shop?categoryId=1");
            return; // Dừng xử lý tiếp
        }

        // Lấy danh sách sản phẩm từ DAO
        List<Products> p = productDAO.getAllProducts();
        if (p != null && !p.isEmpty()) {
            request.setAttribute("productList", p);
        } else {
            request.setAttribute("message", "Không có sản phẩm nào.");
        }

        List<Categories> c = productDAO.getAllCategories();
        if (c != null && !c.isEmpty()) {
            request.setAttribute("categoriesList", c);
        } else {
            request.setAttribute("message", "Không có sản phẩm nào.");
        }
        Products l = productDAO.getLatestProduct();
        request.setAttribute("lastp", l);

// loc sp
        List<Products> filteredProducts;
        if (categoryId != null) {
            filteredProducts = productDAO.getProductsByCategory(Integer.parseInt(categoryId));
            request.setAttribute("categoryId", categoryId); // Lưu categoryId để đánh dấu danh mục đang chọn
        } else {
            filteredProducts = productDAO.getAllProducts();
        }


        String sortOrder = request.getParameter("sort");
        if (sortOrder != null) {
            filteredProducts = sortProducts(filteredProducts, sortOrder);
        }

        // Cập nhật danh sách sản phẩm đã lọc và sắp xếp vào request
        request.setAttribute("productList", filteredProducts);

        // Chuyển tiếp yêu cầu tới trang JSP để hiển thị
        RequestDispatcher dispatcher = request.getRequestDispatcher("doanweb/html/shop.jsp");
        dispatcher.forward(request, response);

    }

    // Hàm sắp xếp sản phẩm theo giá
    private List<Products> sortProducts(List<Products> products, String sortOrder) {
        if ("asc".equals(sortOrder)) {
            products.sort(Comparator.comparingDouble(Products::getPrice)); // Giá thấp đến cao
        } else if ("desc".equals(sortOrder)) {
            products.sort((p1, p2) -> Double.compare(p2.getPrice(), p1.getPrice())); // Giá cao đến thấp
        }
        return products;
    }
}