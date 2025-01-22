<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lien he</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/doanweb/styles/style.css">
    <link rel="icon" href="<%= request.getContextPath() %>/doanweb/images/Page1/LoadWeb.png" type="image/png">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <script src="<%= request.getContextPath() %>/doanweb/js/index.js"></script>
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

    <!-- jquery cdn -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <style>
        .ContactUs-section h2 {
            color: #BC1F23;
            margin-top: 40px;
        }

        .ContactUs-section p {
            font-size: 18px;
            color: #343a40;
        }

        #about-img {
            width: 100%;
            height: 500px;
            object-fit: cover;
        }

        .ContactUs-section input, .ContactUs-section textarea {
            border: 1px solid #343a40;
        }

        .ContactUs-section button {
            background-color: #BC1F23;
            border: none;
            color: aliceblue;
        }

        .ContactUs-section button:hover {
            background-color: #343a40;
            border: none;
        }
    </style>
</head>
<body>
<!-- Nav section -->
<nav class="navbar navbar-expand-lg navbar-light bg-dark py-4 fixed-top">
    <div class="container-fluid mr-5">
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
                    <a class="nav-link" href="<%= request.getContextPath() %>/shop">Cửa Hàng</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<%= request.getContextPath() %>/about">Thông tin</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" href="<%= request.getContextPath() %>/contact">Liên hệ</a>

                </li>
                <div class="nav-item">
                    <li class="nav-item">
                        <a href="<%= (session.getAttribute("user") != null) ? request.getContextPath() + "/profile" : request.getContextPath() + "/doanweb/html/Login.jsp" %>">
                            <i class="bi bi-person-fill" id="user-icon"></i>
                        </a>
                        <!-- Biểu tượng giỏ hàng với số lượng sản phẩm -->
                        <a href="<%= request.getContextPath() %>/cart" class="position-relative">
                            <i class="bi bi-bag-heart-fill" style="font-size: 1.3rem; color: #BC1F23;"></i>
                            <!-- Biểu tượng giỏ hàng -->
                            <!-- Số lượng sản phẩm trong giỏ -->
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
                    <h4><a href="<%= request.getContextPath() %>/shop">GIỎ HÀNG</a></h4>
                </div>
                <div class="menu-section">
                    <h4><a href="/<%= request.getContextPath() %>/about">VỀ MFS</a></h4>

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
</nav>

<!-- ContactUs section -->
<section class="ContactUs-section mt-5 py-5">
    <div class="container">
        <div class="row mt-5 pt-5">
            <div class="col-lg-6 col-md-6 col-12">
                <h2 class="text-uppercase mt-5">Liên Hệ</h2>
                <p class="text-muted">Chúng tôi luôn sẵn sàng giúp đỡ bạn. Nếu bạn có bất kỳ câu hỏi hoặc gợi ý nào, hãy
                    liên hệ với chúng tôi.</p>
                <form action="" onsubmit="contactUs()">
                    <div class="form-group">
                        <label for="name">Tên</label>
                        <input type="text" class="form-control" id="name" name="name" placeholder="Nhập tên của bạn">
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email"
                               placeholder="Nhập email của bạn">
                    </div>
                    <div class="form-group">
                        <label for="message">Tin nhắn</label>
                        <textarea class="form-control" id="message" name="message" rows="3"
                                  placeholder="Nhập nội dung tin nhắn"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Submit</button>
                </form>
            </div>
            <div class="col-lg-6 col-md-6 col-12">
                <img id="about-img" src="<%= request.getContextPath() %>/doanweb/images/Page1/contact.png"
                     class="img-fluid mt-5 py-5" alt="about-img">
            </div>
        </div>
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
                <!-- <div class="col-lg-3 col-md-6 col-12 mb-4">
                  <img src="img/payment.png" alt="payment..logo">
                </div> -->

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
<!-- bootstarp cdn -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>