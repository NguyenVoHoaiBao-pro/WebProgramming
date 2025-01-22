<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/doanweb/styles/style.css">
    <link rel="icon" href="<%= request.getContextPath() %>/doanweb/images/Page1/LoadWeb.png" type="image/png">

    <script src="<%= request.getContextPath() %>/doanweb/js/index.js"></script>
    <script
            src="https://kit.fontawesome.com/cc9450bd42.js"
            crossorigin="anonymous"
    ></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>

    <!-- bootstarp stackpath cdn -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
          integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <!-- Bootstrap icons cdn-->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
    <style>
        .small-img-group {
            display: flex;
            justify-content: space-between;
        }

        .small-img-col {
            flex-basis: 24%;
            cursor: pointer;
        }

        .product-image-container {
            width: 400px; /* Kích thước hình vuông */
            height: 400px; /* Kích thước hình vuông */
            display: flex; /* Dùng flexbox để căn giữa nội dung */
            justify-content: center; /* Căn giữa ngang */
            align-items: center; /* Căn giữa dọc */
            background-color: #f8f8f8; /* Nền xám nhạt nếu ảnh không lấp đầy */
            overflow: hidden; /* Cắt phần ảnh thừa */
            margin-top: 50px;
        }

        .product-image {
            width: 100%; /* Chiều rộng đầy đủ container */
            height: 100%; /* Chiều cao đầy đủ container */
            object-fit: cover; /* Đảm bảo ảnh lấp đầy và giữ tỷ lệ */
        }

        /* CSS cho phần hình ảnh sản phẩm */
        .product img {
            margin-right: 50px;
            margin-left: 20px;
            width: 90%;
            max-width: 350px;
            height: 350px;
            object-fit: cover;
        }


        .sproduct input {
            width: 50px;
            height: 40px;
            padding-left: 10px;
            font-size: 16px;
            margin-right: 10px;
        }

        .sproduct input:focus {
            outline: none;
        }

        .cart-btn {
            background-color: #BC1F23;
            font-size: 0.8rem;
            font-weight: 700;
            outline: none;
            border-radius: 2px;
            border: none;
            color: aliceblue;
            padding: 13px 30px;
            cursor: pointer;
            text-transform: uppercase;
            transition: 0.5s ease-in-out;
        }

        .cart-btn:hover {
            background-color: black;
        }

        .ShopMore {
            background-color: black;
            font-size: 18px;
            font-weight: 700;
            outline: none;
            border-radius: 2px;
            border: none;
            color: aliceblue;
            padding: 13px 30px;
            cursor: pointer;
            text-transform: uppercase;
            transition: 0.5s ease-in-out;
            padding: 20px;
            width: 250px;
        }

        .buy-now-btn {
            background-color: black !important;
            font-size: 0.8rem;
            font-weight: 700;
            outline: none;
            border-radius: 2px;
            border: none;
            color: white !important;
            padding: 13px 30px;
            cursor: pointer;
            text-transform: uppercase;
            transition: 0.5s ease-in-out;
        }

        .ShopMore:hover, .buy-now-btn {
            background-color: #BC1F23;
            color: black;
        }

        .alert-success {
            width: 61%; /* Điều chỉnh chiều rộng của thông báo */
            text-align: center;
            margin-top: 10px;
        }

        .product-name {
            color: black;
            font-weight: bold;
        }
    </style>
</head>
<body>
<!-- Nav section -->
<nav class="navbar navbar-expand-lg navbar-light bg-dark py-4 fixed-top">
    <div class="container-fluid mr-5">
        <!-- <div class="col-1"></div> -->
        <img src="<%= request.getContextPath() %>/doanweb/images/Page1/LogoWeb.png" onclick="location.reload();"
             id="logo-img" alt="logo..">

        <button class="navbar-toggler" onclick="toggleMenu()">
            <span><i id="nav-bar-icon" class="bi bi-list"></i></span>
        </button>


        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav ml-auto">

                <li class="nav-item">
                    <a class="nav-link " href="<%= request.getContextPath() %>/home">Trang Chủ</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="#">Cửa Hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/about">Thông tin</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/contact">Liên hệ</a>

                </li>
                <div class="nav-item">
                    <li class="nav-item">
                        <a href="<%= (session.getAttribute("user") != null) ? request.getContextPath() + "/profile" : request.getContextPath() + "/doanweb/html/Login.jsp" %>">
                            <i class="bi bi-person-fill" id="user-icon"></i>
                        </a>
                        <a href="<%= request.getContextPath() %>/cart" class="position-relative">
                            <i class="bi bi-bag-heart-fill" style="font-size: 1.3rem; color: #BC1F23;"></i>
                            <span class="position-absolute top-0 start-90 translate-middle badge rounded-circle bg-danger"
                                  id="cart-count"
                                  style="width: 22px; height: 22px; line-height: 22px; font-size: 14px; text-align: center; display: flex; align-items: center; justify-content: center;">
                                ${sessionScope.totalItems != null ? sessionScope.totalItems : 0}
                            </span>
                        </a>
                    </li>
                </div>

            </ul>
        </div>


        <!-- Biểu tượng menu để mở/đóng sidebar
        <i class="bi bi-list menu-icon" onclick="toggleMenu()" id="menu-btn"></i>

        <-- Sidebar menu -->
        <div id="mySideBar" class="sidebar">
            <div class="sidebar-header">
                <img src="<%= request.getContextPath() %>/doanweb/images/Page1/LogoWeb.png" alt="Logo" class="logo">
                <span id="closeBtn" class="close-btn" onclick="toggleMenu()">&times;</span>
            </div>
            <div class="sidebar-content">
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/home">TRANG CHỦ</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/cart">GIỎ HÀNG</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/about">VỀ MFS</a></h4>

                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/chinhsach">CHÍNH SÁCH</a></h4>

                </div>
                <div class="menu-section">
                    <h4><a href="javascript:void(0);" class="toggle-menu">THỰC ĐƠN</a></h4>
                    <ul class="submenu">
                        <li><a href="<%= request.getContextPath() %>/shop">Các món đùi gà nổi bật</a></li>
                        <li><a href="<%= request.getContextPath() %>/shop">Các món cánh gà nổi bật</a></li>
                        <li><a href="#">Combo gà phải thử</a></li>
                    </ul>
                </div>
                <div class="menu-section">
                    <h4><a href="#">DỊCH VỤ</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="<%= request.getContextPath() %>/contact">LIÊN HỆ</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="#">TUYỂN DỤNG</a></h4>
                </div>

                <div class="menu-section user" id="user-sidebar">
                    <h4><i class="bi bi-person"></i><a href="<%= request.getContextPath() %>/login">ĐĂNG NHẬP</a> / <a
                            href="<%= request.getContextPath() %>/register">ĐĂNG KÝ</a></h4>

                </div>
                <div class="menu-section user-logged-in" id="user-logged-in" style="display: none;">
                    <span id="greeting-menu"></span>
                    <h4><a href="<%= request.getContextPath() %>/profile">TÀI KHOẢN CỦA TÔI / </a></h4>
                    <h4><i class="bi bi-person"></i><a href="javascript:void(0);" onclick="logout()">ĐĂNG XUẤT</a></h4>
                </div>
            </div>
        </div>
    </div>
</nav>


<!-- Product details section -->
<section class="container sproduct my-5 pt-5">
    <div class="row mt-5">
        <div class="col-lg-5 col-md-12 col-12 d-flex justify-content-center align-items-center">
            <div class="product-image-container">
                <img
                        class="product-image"
                        src="<%= request.getContextPath() %>/${product.image}"
                        alt="${product.name}">
            </div>
        </div>
        <div class="col-lg-6 col-md-12 col-12">
            <h6
                    class="text-secondary mt-5"
                    onclick="window.location.href = 'doanweb/html/shop.jsp';">
                Shop / Sản phẩm
            </h6>
            <h3 class="pt-3 pb-2">${product.name}</h3>
            <h2 class="p">${product.price}k</h2>

            <span class="bold">Số lượng: </span>
            <input
                    id="quantity"
                    class="my-3"
                    type="number"
                    value="1"
                    min="1"
                    max="${product.stock}"
                    onchange="validateQuantity(${product.stock})">

            <span class="bold">${product.stock} sản phẩm có sẵn</span>
            <br>

            <button
                    class="buy-now-btn"
                    onclick="window.location.href='add-to-cart?id=${product.id}&action=buy-now'">
                Mua ngay
            </button>
            <button class="cart-btn" onclick="window.location.href='add-to-cart?id=${product.id}'">Thêm vào giỏ hàng
            </button>
            <%
                String message = (String) request.getAttribute("message");
                if (message != null) {
            %>
            <div class="alert alert-success" id="successMessage">
                <%= message %>
            </div>
            <% } %>
            <script>
                // Kiểm tra xem có thông báo hay không
                window.onload = function () {
                    var messageElement = document.getElementById("successMessage");
                    if (messageElement) {
                        messageElement.style.display = "block";  // Hiển thị thông báo
                        setTimeout(function () {
                            messageElement.style.display = "none";  // Ẩn thông báo sau 2 giây
                        }, 2000);  // 2000ms = 2s
                    }
                };
            </script>

            <h4 class="mt-5 pb-3">Chi tiết sản phẩm</h4>
            <span class="text-secondary">${product.description}</span>
        </div>
    </div>

</section>


<!-- Product Review Section -->
<section class="container my-5">
    <h3 class="text-center pb-3">Đánh giá sản phẩm</h3>
    <hr class="border border-danger border-2 opacity-75 mx-auto mb-4">
    <div class="rating-summary">
        <div class="rating-score">5 trên 5</div>
        <div class="stars">★★★★★</div>
        <div class="filter-buttons">
            <button class="active">Tất Cả</button>
            <button>5 Sao (3)</button>
            <button>4 Sao (0)</button>
            <button>3 Sao (0)</button>
            <button>2 Sao (0)</button>
            <button>1 Sao (0)</button>
        </div>
    </div>

    <div class="reviews">
        <div class="review">
            <div class="user-avatar">A</div>
            <div class="review-content">
                <p class="username">anhtruonguyenhong6879</p>
                <div class="stars">★★★★★</div>
                <p class="review-date">2024-02-19 23:14</p>
                <p>Chất lượng sản phẩm: ok</p>
            </div>
        </div>

        <div class="review">
            <div class="user-avatar">T</div>
            <div class="review-content">
                <p class="username">trngiahuy159</p>
                <div class="stars">★★★★★</div>
                <p class="review-date">2024-02-16 16:18</p>
                <p>Tuyệt vời</p>
            </div>
        </div>

        <div class="review">
            <div class="user-avatar">N</div>
            <div class="review-content">
                <p class="username">nambuiohoang857</p>
                <div class="stars">★★★★★</div>
                <p class="review-date">2023-11-24 19:18</p>
            </div>
        </div>
    </div>


    <!-- Add a new review form -->
    <div class="mt-5">
        <h5>Để lại đánh giá của bạn</h5>
        <form id="review-form">
            <div class="form-group mt-3">
                <label for="review-comment">Nhận xét của bạn</label>
                <textarea class="form-control" id="review-comment" rows="4"
                          placeholder="Chia sẻ cảm nhận của bạn về sản phẩm" required></textarea>
            </div>
            <div class="form-group mt-3">
                <label for="review-rating">Đánh giá:</label>
                <select class="form-control" id="review-rating" required>
                    <option value="5">★★★★★</option>
                    <option value="4">★★★★☆</option>
                    <option value="3">★★★☆☆</option>
                    <option value="2">★★☆☆☆</option>
                    <option value="1">★☆☆☆☆</option>
                </select>
            </div>
            <button type="submit" class="btn-review">Gửi đánh giá</button>
        </form>
    </div>
</section>

<section class="my-5 pb-5">
    <div class="container text-center mt-5 py-5">
        <h3>Những sản phẩm tương tự</h3>
        <hr class="border border-danger border-2 opacity-75 mx-auto">
    </div>
    <div class="row mx-auto container-fluid">
        <c:if test="${not empty randomProductList}">
            <c:forEach var="product" items="${randomProductList}" varStatus="status">
                <!-- Chỉ hiển thị 4 sản phẩm đầu tiên -->
                <c:if test="${status.index < 4}">
                    <div class="product text-center col-lg-3 col-md-4 col-12 mb-4">
                        <a href="detail?id=${product.id}">
                            <img class="img-fluid" src="<%= request.getContextPath() %>/${product.image}"
                                 alt="${product.name}">
                            <h5 class="product-name">${product.name}</h5>
                            <h4 class="product-price">${product.price} VND</h4>
                        </a>
                    </div>
                </c:if>
            </c:forEach>
        </c:if>
    </div>
    <div class="text-center">
        <button class="ShopMore my-5 mx-auto" onclick="window.location.href = '/shop';">Tiếp tục mua sắm</button>
    </div>
</section>


<footer class="mt-5 p-5 bg-dark">
    <div class="row conatiner mx-auto pt-5">
        <div class="footer-one col-lg-3 col-md-6 col-12">

            <img id="logo-img-footer" src="<%= request.getContextPath() %>/doanweb/images/Page1/LogoWeb.png" alt="logo">
            <p class="py-3 pl-2 ml-4 mr-5">Tiệm Gà Sao Hỏa là một quán ăn hiện đại với phong cách thiết kế đậm chất
                không gian. Thực đơn của quán không chỉ có các món gà nổi tiếng, mà còn kèm theo những món ăn độc lạ lấy
                cảm hứng từ vũ trụ mang lại cảm giác mới mẻ cho thực khách.</p>

        </div>

        <div class="footer-one col-lg-3 col-md-6 col-12 mb-3">
            <h5 class="pb-2">Liên kết nhanh</h5>
            <ul class="text-uppercase list-unstyled">
                <li><a href="<%= request.getContextPath() %>/home">Trang chủ</a></li>
                <li><a href="<%= request.getContextPath() %>/shop">Cửa hàng</a></li>
                <li><a href="<%= request.getContextPath() %>/about">Thông tin</a></li>
                <li><a href="<%= request.getContextPath() %>/contact">Liên hệ</a></li>
                <li><a href="<%= request.getContextPath() %>/cart">Giỏ hàng</a></li>
            </ul>
        </div>
        <div class="footer-one col-lg-3 col-md-6 col-12 mb-3">
            <h5 class="pb-2">Liên hệ với chúng tôi</h5>
            <div>
                <h6 class="text-uppercase">Địa chỉ</h6>
                <p>Khu phố 6, Phường Linh Trung, TP. Thủ Đức, TP. Hồ Chí Minh</p>
            </div>
            <div>
                <h6 class="text-uppercase">Điện thoại</h6>
                <p>0849294483</p>
            </div>
            <div>
                <h6 class="text-uppercase">Email</h6>
                <p>MarsStore@gmail.com</p>
            </div>
        </div>
        <div class="Photos col-lg-3 col-md-6 col-12">
            <h5 class="pb-2">Các đơn vị tài trợ</h5>
            <div class="row">
                <img class="footer-img img-fluid mb-2"
                     src="<%= request.getContextPath() %>/doanweb/images/Page1/image copy 3.png" alt="leather-img">
                <img class="footer-img img-fluid mb-2"
                     src="<%= request.getContextPath() %>/doanweb/images/Page1/image copy 2.png" alt="leather-img">
                <img class="footer-img img-fluid mb-2"
                     src="<%= request.getContextPath() %>/doanweb/images/Page1/image copy.png" alt="leather-img">
                <img class="footer-img img-fluid mb-2"
                     src="<%= request.getContextPath() %>/doanweb/images/Page1/image.png" alt="leather-img">
            </div>
        </div>
        <div class="copyright mt-5">
            <div class="row container mx-auto">
                <div class="col-lg-6 col-md-8 col-12 mb-2 mx-auto">
                    <p>MARSSTORE WEBSITE &copy; DESIGN 2024</p>
                </div>

                <div class="col-lg-3 col-md-6 col-12">
                    <a href="https://www.facebook.com/"><i class="bi bi-facebook"></i></a>
                    <a href="https://x.com/home?lang=vi"><i class="fa-brands fa-x-twitter"></i></a>
                    <a href="https://www.linkedin.com/feed/"><i class="bi bi-linkedin"></i></a>
                    <a href="https://www.instagram.com/"><i class="bi bi-instagram"></i></a>
                </div>
            </div>
        </div>
    </div>

</footer>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>