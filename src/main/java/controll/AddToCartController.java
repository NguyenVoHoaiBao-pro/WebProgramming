package controll;

import dao.dao;
import dao.CartDao;
import entity.CartItem;
import entity.Products;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CartDao cartDao;
    private final dao dao;

    public AddToCartController() {
        super();
        cartDao = new CartDao();
        dao = new dao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("/login");
            return;
        }

        String productIdParam = request.getParameter("id");
        String action = request.getParameter("action");
        String quantityParam = request.getParameter("quantity");

        if (productIdParam == null || productIdParam.isEmpty()) {
            redirectToErrorPage(response);
            return;
        }

        int productId;
        int quantity = 1;

        try {
            productId = Integer.parseInt(productIdParam);
            if (quantityParam != null && !quantityParam.isEmpty()) {
                quantity = Integer.parseInt(quantityParam);
                if (quantity <= 0) {
                    throw new NumberFormatException("Số lượng phải là số dương.");
                }
            }
        } catch (NumberFormatException e) {
            redirectToErrorPage(response);
            return;
        }

        Users user = (Users) session.getAttribute("user");
        int userId = user.getId();

        switch (action) {
            case "remove":
                cartDao.removeFromCart(userId, productId);
                break;

            case "add-cart":
                // Kiểm tra số lượng sản phẩm yêu cầu
                int stock = 0;
                try {
                    stock = cartDao.getProductStock(productId); // Lấy thông tin tồn kho
                } catch (SQLException e) {
                    e.printStackTrace();
                    request.setAttribute("error", "Lỗi khi lấy thông tin tồn kho.");
                    request.getRequestDispatcher("/detail?id=" + productId).forward(request, response);
                    return;
                }

                // Lấy số lượng sản phẩm đã có trong giỏ hàng
                int existingQuantity = 0;
                try {
                    existingQuantity = cartDao.getQuantityByUserAndProduct(userId, productId); // Số lượng đã có trong giỏ
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Kiểm tra nếu số lượng yêu cầu vượt quá tồn kho
                if (quantity + existingQuantity > stock) {
                    request.setAttribute("error", "Số lượng sản phẩm yêu cầu vượt quá tồn kho.");
                    request.getRequestDispatcher("/detail?id=" + productId).forward(request, response);
                    return;
                }

                // Nếu không vượt quá, thêm vào giỏ hàng
                cartDao.AddToCart(userId, productId, quantity);
                request.setAttribute("message", "Sản phẩm đã được thêm vào giỏ hàng!");
                request.getRequestDispatcher("/detail?id=" + productId).forward(request, response);
                return;


            case "add-cart-shop":
                // Kiểm tra số lượng sản phẩm yêu cầu
                int stockShop = 0;
                try {
                    stockShop = cartDao.getProductStock(productId); // Lấy thông tin tồn kho
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Không hiển thị thông báo lỗi, nhưng có thể xử lý nếu cần
                    response.sendRedirect("/shop");  // Chuyển hướng về trang shop nếu gặp lỗi
                    return;
                }

                // Lấy số lượng sản phẩm đã có trong giỏ hàng
                int existingQuantityShop = 0;
                try {
                    existingQuantityShop = cartDao.getQuantityByUserAndProduct(userId, productId); // Số lượng đã có trong giỏ
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                // Kiểm tra nếu số lượng yêu cầu vượt quá tồn kho
                if (quantity + existingQuantityShop > stockShop) {
                    // Nếu vượt quá tồn kho, không hiển thị thông báo, chỉ chuyển hướng về shop
                    response.sendRedirect("/shop");
                    return;
                }

                // Nếu không vượt quá, thêm vào giỏ hàng
                cartDao.AddToCart(userId, productId, quantity);

                // Chuyển hướng về trang shop mà không hiển thị thông báo
                response.sendRedirect("/shop");
                return;


            case "buy-now":
                cartDao.AddToCart(userId, productId, quantity);
                response.sendRedirect("/cart");
                return;

            default:
                redirectToErrorPage(response);
                return;
        }

        List<CartItem> cart = cartDao.getCartByUserId(userId);
        updateCartSession(session, cart);
        handleRandomProducts(request);

        response.sendRedirect("/cart");
    }

    private void redirectToErrorPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("error.jsp");
    }

    private void updateCartSession(HttpSession session, List<CartItem> cart) {
        int totalItems = cart.stream().mapToInt(CartItem::getQuantity).sum();
        double totalPrice = cart.stream()
                .mapToDouble(cartItem -> dao.getProductPriceById(cartItem.getProductId()) * cartItem.getQuantity())
                .sum();

        session.setAttribute("totalItems", totalItems);
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("cart", cart);
    }

    private void handleRandomProducts(HttpServletRequest request) {
        List<Products> randomProducts = dao.getRandomProducts();
        request.setAttribute("randomProductList", randomProducts);
    }
}
