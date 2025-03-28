package controll;

import entity.CartItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import dao.dao;


import java.io.IOException;
import java.util.ArrayList;

@WebServlet({
        "/AddProduct", "/AddCategory",
        "/UpdateProduct", "/DeleteProduct",
        "/DeleteCategory", "/UpdateStock",
        "/GetTotalCartPrice"
})
public class CRUD extends HttpServlet {
    private static final long serialVersionUID = 1L;
    dao daoInstance = new dao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/AddProduct":
                addProduct(request, response);
                break;

            case "/AddCategory":
                addCategory(request, response);
                break;

            case "/UpdateProduct":
                updateProduct(request, response);
                break;

            case "/UpdateStock":
                updateStock(request, response);
                break;

            case "/GetTotalCartPrice":
                getTotalCartPrice(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action không được hỗ trợ");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/DeleteProduct":
                deleteProduct(request, response);
                break;

            case "/DeleteCategory":
                deleteCategory(request, response);
                break;

            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Action không được hỗ trợ");
        }
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        String image = request.getParameter("image");
        int category_id = Integer.parseInt(request.getParameter("category_id"));

        daoInstance.addProduct(name, price, stock, description, category_id, image);
        response.getWriter().write("Thêm sản phẩm thành công!");
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("categoryName");
        daoInstance.addCategories(name);
        response.getWriter().write("Thêm sản phẩm thành công!");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Lấy các tham số từ request
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String price = request.getParameter("price");
        String stock = request.getParameter("stock");
        String image = request.getParameter("image");
        String categoryId = request.getParameter("category_id");

        // Chuyển đổi các tham số sang kiểu dữ liệu phù hợp
        int productId = Integer.parseInt(id); // id phải là số nguyên
        double productPrice = Double.parseDouble(price); // price phải là số thực
        int productStock = Integer.parseInt(stock); // stock phải là số nguyên
        int productCategoryId = Integer.parseInt(categoryId); // categoryId phải là số nguyên


        daoInstance.updateProduct(productId, name, (int) productPrice, productStock, description, productCategoryId, image);

        // Kiểm tra và phản hồi cho người dùng
        response.getWriter().write("Cập nhật sản phẩm thành công!");
        response.sendRedirect("doanweb/html/ad.jsp");
    }


    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        daoInstance.deleteProduct(id);
        response.getWriter().write("Xóa sản phẩm thành công!");
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        daoInstance.deleteCategory(id);
        response.getWriter().write("Xóa danh mục thành công!");
    }

    private void updateStock(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        int result = daoInstance.updateStock(productId, stock);

        if (result > 0) {
            response.getWriter().write("Cập nhật số lượng thành công!");
        } else {
            response.getWriter().write("Cập nhật thất bại!");
        }
    }

    private void getTotalCartPrice(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ArrayList<CartItem> cartList = (ArrayList<CartItem>) request.getAttribute("cartList");

        double total = daoInstance.getTotalCartPrice(cartList);
        response.getWriter().write("Tổng giá trị giỏ hàng: " + total);
    }
}
