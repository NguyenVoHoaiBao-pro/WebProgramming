package controll;

import entity.Products;
import dao.dao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/search")
public class SearchController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        dao daoInstance = new dao();
        List<Products> products = daoInstance.searchProducts(name);

        // Set response type to HTML
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Start building HTML content for search results
        StringBuilder htmlResponse = new StringBuilder();

        if (products.isEmpty()) {
            htmlResponse.append("<p>No products found</p>");
        } else {
            for (Products p : products) {
                htmlResponse.append("<div class='search-result'>");

                // Create a link to product detail page
                htmlResponse.append("<a href='detail?id=").append(p.getId()).append("'>");

                // Add image (Assume getImage() returns the image URL)
                htmlResponse.append("<img class='img-fluid' src='")
                        .append(request.getContextPath()).append("/").append(p.getImage())
                        .append("' alt='").append(p.getName()).append("' />");

                // Add product name and price in one line
                htmlResponse.append("<p>").append(p.getName()).append(" - <span class='product-price'>")
                        .append(p.getPrice()).append("K VND</span></p>");

                // Close the link
                htmlResponse.append("</a>");

                htmlResponse.append("</div>");
            }
        }

        // Print the HTML response
        out.print(htmlResponse.toString());
        out.flush();
    }
}
