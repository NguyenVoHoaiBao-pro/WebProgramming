package controll;

import dao.UserDao;
import entity.Users;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

@WebServlet("/profile")
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao dao;

    @Override
    public void init() {
        dao = new UserDao(); // Khởi tạo đối tượng DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            response.sendRedirect("/login");
            return;
        }
        Object successMsg = request.getSession().getAttribute("successMessage");
        Object errorMsg = request.getSession().getAttribute("errorMessage");

        if (successMsg != null) {
            request.setAttribute("successMessage", successMsg);
            request.getSession().removeAttribute("successMessage");
        }
        if (errorMsg != null) {
            request.setAttribute("errorMessage", errorMsg);
            request.getSession().removeAttribute("errorMessage");
        }

        // Lấy action từ request (nếu có)
        String action = request.getParameter("action");
        if (action != null) {
            action = action.trim();
        }
        request.setAttribute("action", action); // Truyền action sang JSP

        // Lấy thông tin người dùng để hiển thị
        Users user = dao.getUserByUsername(loggedInUser.getUsername());
        request.setAttribute("user", user);

        // Chuyển đến profile.jsp
        forwardToPage(request, response, "doanweb/html/profile.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users loggedInUser = getLoggedInUser(request);

        if (loggedInUser == null) {
            response.sendRedirect("/login");
            return;
        }

        String action = request.getParameter("action");
        if (action != null) {
            action = action.trim();
        }
        if (action == null) {
            response.sendRedirect("/profile");
            return;
        }

        switch (action) {
            case "changeInfo":
                handleEditProfile(request, response, loggedInUser);
                break;

            case "change-pass":
                try {
                    handleChangePassword(request, response, loggedInUser);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeySpecException e) {
                    throw new RuntimeException(e);
                }
                break;

            case "deleteAccount":
                handleDeleteAccount(request, response, loggedInUser);
                break;

            default:
                response.sendRedirect("/profile");
        }
    }

    private Users getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false); // Không tạo session mới nếu chưa tồn tại
        if (session != null) {
            return (Users) session.getAttribute("user");
        }
        return null;
    }

    private void forwardToPage(HttpServletRequest request, HttpServletResponse response, String page)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void handleEditProfile(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws IOException, ServletException {
        // Lấy các tham số từ request
        String newEmail = request.getParameter("newEmail");
        String newPhone = request.getParameter("newPhone");
        String newAddress = request.getParameter("newAddress");

        // Kiểm tra tính hợp lệ
        if (newEmail == null || newEmail.isEmpty() || !newEmail.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            request.getSession().setAttribute("errorMessage", "Email không hợp lệ.");
            response.sendRedirect("/profile?action=changeInfo");
            return;
        }

        if (newPhone == null || newPhone.isEmpty() || !newPhone.matches("^\\d{10,15}$")) {
            request.getSession().setAttribute("errorMessage", "Số điện thoại không hợp lệ.");
            response.sendRedirect("/profile?action=changeInfo");
            return;
        }

        // Cập nhật thông tin đối tượng
        loggedInUser.setEmail(newEmail);
        loggedInUser.setPhone(newPhone);
        loggedInUser.setAddress(newAddress);

        // Gọi DAO để cập nhật DB
        boolean isUpdated = dao.editUser(loggedInUser);

        if (isUpdated) {
            // ✅ LẤY LẠI USER MỚI NHẤT TỪ DATABASE
            Users updatedUser = dao.getUserByUsername(loggedInUser.getUsername());

            // Cập nhật session
            request.getSession().setAttribute("user", updatedUser);

            // Thông báo thành công
            request.getSession().setAttribute("successMessage", "Cập nhật thông tin thành công!");
        } else {
            request.getSession().setAttribute("errorMessage", "Cập nhật thông tin thất bại. Vui lòng thử lại!");
        }

        // Redirect lại trang profile
        response.sendRedirect("/profile?action=changeInfo");
    }


    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws IOException, ServletException, NoSuchAlgorithmException, InvalidKeySpecException {
        // Lấy thông tin từ form nhập
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        // Kiểm tra mật khẩu xác nhận có khớp với mật khẩu mới không
        if (!newPassword.equals(confirmPassword)) {
            request.getSession().setAttribute("errorMessage", "Mật khẩu xác nhận không khớp!");
            response.sendRedirect("/profile?action=change-pass");
            return;
        }

        // Kiểm tra mật khẩu cũ có đúng không
        boolean isPasswordCorrect = dao.checkOldPassword(loggedInUser.getUsername(), oldPassword);
        if (!isPasswordCorrect) {
            request.getSession().setAttribute("errorMessage", "Mật khẩu cũ không đúng!");
            response.sendRedirect("/profile?action=change-pass");
            return;
        }

        // Mã hóa mật khẩu mới
        String salt = generateSalt();
        String hashedNewPassword = hashPassword(newPassword, salt.getBytes());

        // Cập nhật mật khẩu mới vào cơ sở dữ liệu
        boolean isPasswordChanged = dao.updatePassword(loggedInUser.getUsername(), hashedNewPassword, salt);
        if (isPasswordChanged) {
            request.getSession().setAttribute("successMessage", "Mật khẩu đã được thay đổi thành công!");
        } else {
            request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra trong quá trình thay đổi mật khẩu.");
        }

        response.sendRedirect("/profile?action=change-pass");
    }

    private String hashPassword(String password, byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 65536;  // Số lần lặp lại để tăng cường bảo mật
        int keyLength = 256;  // Độ dài hash (256-bit)

        // Tạo PBEKeySpec với mật khẩu, salt, số lần lặp lại, và độ dài hash
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);

        // Tạo đối tượng SecretKeyFactory sử dụng thuật toán PBKDF2WithHmacSHA256
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

        // Tạo hash mật khẩu
        byte[] hash = skf.generateSecret(spec).getEncoded();

        // Trả về kết quả kết hợp salt và hash dưới dạng chuỗi hex
        return bytesToHex(salt) + ":" + bytesToHex(hash);  // Salt và hash được ghép lại theo định dạng salt:hash
    }


    private String generateSalt() {
        // Tạo một salt ngẫu nhiên với độ dài 16 bytes
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return bytesToHex(salt); // Chuyển đổi salt thành chuỗi hex
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }



    private void handleDeleteAccount(HttpServletRequest request, HttpServletResponse response, Users loggedInUser)
            throws IOException, ServletException {
        String password = request.getParameter("confirmDelete");

        boolean isAccountDeleted = dao.deleteAccount(loggedInUser.getUsername(), password);

        if (isAccountDeleted) {
            request.getSession().invalidate();
            response.sendRedirect("/login");
        } else {
            request.setAttribute("errorMessage", "Mật khẩu không đúng hoặc có lỗi xảy ra!");
            response.sendRedirect("/profile?action=deleteAccount");
        }
    }
}
