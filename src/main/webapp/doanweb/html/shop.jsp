<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop</title>
    <head>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/doanweb/styles/style.css">
        <title></title>
    </head>
    <link rel="icon" href="<%= request.getContextPath() %>/doanweb/images/Page1/LoadWeb.png" type="image/png">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

    <%--    <script src="/js/index.js"></script>--%>
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
        .product img {
            width: 100%;
            height: 400px;
            box-sizing: border-box;
            object-fit: contain;
        }

        .pagination a {
            color: #343a40;
            border-color: #343a40;
        }

        .product img {
            object-fit: cover; /* Cắt ảnh theo tỷ lệ vuông */
            display: block;

        }


    </style>
</head>
<body>
<button class="back-to-top" id="backToTop"><i class="fa-solid fa-angle-up"></i></button>
<script>
    // Lấy nút quay lại đầu trang
    const backToTopButton = document.getElementById("backToTop");

    // Hiển thị nút khi cuộn xuống 100px
    window.onscroll = function () {
        if (document.body.scrollTop > 100 || document.documentElement.scrollTop > 100) {
            backToTopButton.style.display = "block";
        } else {
            backToTopButton.style.display = "none";
        }
    };

    // Thêm sự kiện click để quay lại đầu trang
    backToTopButton.onclick = function () {
        window.scrollTo({top: 0, behavior: 'smooth'});
    };
</script>
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
                    <h4><a href="/">GIỎ HÀNG</a></h4>
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
                    <h4><a href="/html/Menu/Profile.html">TÀI KHOẢN CỦA TÔI / </a></h4>
                    <h4><i class="bi bi-person"></i><a href="javascript:void(0);" onclick="logout()">ĐĂNG XUẤT</a></h4>
                </div>
            </div>
        </div>
    </div>
</nav>

<!-- Featured Product section -->
<section id="featured" class="my-5 py-5">
    <div class="container ml-5 mt-5 px-5 pt-5">
        <h3>Các món nổi bật</h3>
        <hr class="border border-danger border-2 opacity-75">
        <p style="font-size: 18px">Tại đây, bạn có thể khám phá các món mới của chúng tôi trên <span
                style="color: #BC1F23">MarsStore.</span></p>
        <div style="display: flex; justify-content: center;">
        </div>
    </div>

    <style>
        .pagination .page-item .page-link {
            color: #fff; /* Màu chữ trắng */
            background-color: #dc3545; /* Màu nền đỏ */
            border: 1px solid #dc3545; /* Viền đỏ */
            padding: 8px 12px;
            border-radius: 4px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .pagination .page-item .page-link:hover {
            background-color: #a71d2a; /* Màu nền đỏ đậm hơn khi hover */
        }

        .pagination .page-item.active .page-link {
            background-color: #7b111a; /* Màu đỏ tối cho trang đang active */
            border-color: #7b111a;
        }

        .pagination {
            display: flex;
            list-style-type: none;
            padding: 0;
        }

        .pagination .page-item {
            margin: 0 5px;
        }
    </style>
    <div class="search-bar">
        <input type="text" class="search-input" id="searchInput" placeholder="Tìm kiếm...">
        <button class="search-button" id="searchButton">
            <i class="bi bi-search"></i>
        </button>
    </div>

    <script>
        // Hàm loại bỏ dấu tiếng Việt
        function removeVietnameseTones(str) {
            return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "").replace(/đ/g, "d").replace(/Đ/g, "D").toLowerCase();
        }

        function searchProduct() {
            // Lấy giá trị từ ô input
            const searchInput = document.getElementById("searchInput").value.trim().toLowerCase();
            const normalizedSearch = removeVietnameseTones(searchInput);

            // Lấy danh sách các sản phẩm
            const products = document.querySelectorAll(".product-card");

            // Nếu ô tìm kiếm trống, hiển thị tất cả sản phẩm
            if (normalizedSearch === "") {
                products.forEach(product => {
                    product.style.display = "block"; // Hiển thị lại tất cả sản phẩm
                });
                return;
            }

            let found = false; // Kiểm tra nếu tìm thấy sản phẩm

            products.forEach(product => {
                const productName = product.querySelector(".product-name").textContent.trim().toLowerCase();
                const normalizedName = removeVietnameseTones(productName);

                // Kiểm tra nếu sản phẩm phù hợp với từ khóa tìm kiếm
                if (normalizedName.includes(normalizedSearch)) {
                    product.style.display = "block"; // Hiển thị sản phẩm
                    found = true;
                } else {
                    product.style.display = "none"; // Ẩn sản phẩm không khớp
                }
            });

            // Thông báo nếu không tìm thấy sản phẩm nào
            if (!found) {
                alert("Không tìm thấy sản phẩm nào phù hợp.");
            }
        }

        // Thêm sự kiện click cho nút tìm kiếm
        document.getElementById("searchButton").addEventListener("click", searchProduct);

        // Thêm sự kiện cho phím Enter trên ô tìm kiếm
        document.getElementById("searchInput").addEventListener("keydown", function (event) {
            if (event.key === "Enter") {
                searchProduct(); // Gọi hàm tìm kiếm khi nhấn Enter
            }
        });

        // Tự động hiển thị lại tất cả sản phẩm khi xóa hết nội dung ô tìm kiếm
        document.getElementById("searchInput").addEventListener("input", function () {
            if (this.value.trim() === "") {
                const products = document.querySelectorAll(".product-card");
                products.forEach(product => {
                    product.style.display = "block"; // Hiển thị lại tất cả sản phẩm
                });
            }
        });
    </script>

    <style>

        .category-list {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            background-color: #f8f9fa;
        }

        .product-card img {
            width: 100%; /* Chiếm hết chiều rộng của thẻ chứa */
            height: 200px; /* Chiều cao cố định của hình ảnh */
            object-fit: cover; /* Hình ảnh sẽ bao phủ toàn bộ khu vực mà không bị méo, có thể bị cắt bớt */
        }

        .product-card {
            border: 1px solid #ddd;
            border-radius: 5px;
            overflow: hidden;
            text-align: center;
            padding: 10px;
            outline: none;
        }


        .product-card h5 {
            font-size: 1.2rem;
            margin-top: 10px;
            outline: none;
        }

        .product-card p {
            font-size: 0.9rem;
            color: #555;
            outline: none;
        }

        .product-card .price {
            font-size: 1.1rem;
            color: red;
            font-weight: bold;
            outline: none;
        }

        .last-product {
            background-color: #dff5e1;
            padding: 10px;
            border-radius: 5px;
        }

        .product.show {
            display: block; /* Hiện sản phẩm phù hợp */
        }

        .search-bar {
            width: 400px;
            overflow: hidden;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50px;
            overflow: hidden;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border: solid #BC1F23 2px;
            margin-left: 625px;

        }

        .search-input {
            width: 85%;
            padding: 10px 20px;
            border: none;
            outline: none;
            font-size: 16px;
            height: 40px;
        }

        .search-button {
            width: 15%;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            background-color: #BC1F23;
            border: solid #BC1F23 10px;
            height: 40px;
        }

        .search-button i {
            font-size: 20px;
            color: white;
        }

        .search-container input {
            width: 300px;
            padding: 8px;
        }

        /*sidebar  */
        .list-category {
            list-style-type: none; /* Xóa các dấu đầu dòng */
            padding: 0;
            margin: 0;
            border-collapse: collapse; /* Loại bỏ khoảng cách giữa các đường viền */
            width: 100%; /* Căn chỉnh chiều rộng 100% */
        }

        /* Định dạng các mục trong danh sách (li) */
        .list-category li {
            background-color: #f9f9f9; /* Màu nền sáng cho các mục */
            border: 1px solid #ddd; /* Đường viền */
            padding: 10px;
            text-align: left;
            transition: background-color 0.3s ease; /* Hiệu ứng chuyển đổi màu nền khi hover */
            list-style-type: none; /* Xóa dấu chấm đầu dòng */
        }

        /* Hiệu ứng hover cho các hàng */
        .list-category li:hover {
            background-color: #e0e0e0; /* Màu nền nhạt hơn khi hover */
        }

        /* Định dạng liên kết bên trong li */
        .list-category li a {
            text-decoration: none; /* Xóa gạch chân */
            color: #333; /* Màu chữ tối */
            display: block; /* Toàn bộ hàng có thể click */
            padding: 5px 0; /* Thêm khoảng cách trên/dưới cho liên kết */
            font-weight: normal; /* Làm cho chữ không đậm */
        }

        /* Hiệu ứng hover cho liên kết */
        .list-category li a:hover {
            color: #BC1F23; /* Đổi màu chữ thành màu đỏ khi hover */
            font-weight: bold; /* Làm đậm chữ khi hover */
        }

        /* Định dạng mục danh mục khi được chọn (active) */
        .list-category li.active a {
            color: #BC1F23; /* Đổi màu chữ thành màu đỏ khi mục được chọn */
            font-weight: bold; /* Làm đậm chữ khi mục được chọn */
        }


        .last-product {
            border: 1px solid #ddd; /* Viền mỏng */
            border-radius: 5px; /* Bo góc */
            padding: 1rem; /* Khoảng cách bên trong */
            background-color: #f9f9f9; /* Màu nền nhạt */
        }

        .last-product h5 {
            font-size: 1.2rem; /* Cỡ chữ tiêu đề */
            font-weight: bold; /* Chữ đậm */
            margin-bottom: 0.75rem; /* Khoảng cách dưới */
            color: #333; /* Màu chữ */
        }

        .last-product img {
            max-width: 100%; /* Đảm bảo ảnh không vượt quá kích thước khung */
            height: auto; /* Giữ tỉ lệ ảnh */
            margin-bottom: 0.75rem; /* Khoảng cách dưới ảnh */
            border-radius: 5px; /* Bo góc ảnh */
        }

        .last-product p {
            margin: 0.5rem 0; /* Khoảng cách giữa các đoạn văn */
            color: #555; /* Màu chữ nhạt */
        }

        .last-product .price {
            font-weight: bold; /* Chữ đậm */
            color: #e63946; /* Màu đỏ */
            font-size: 1.1rem; /* Cỡ chữ */
        }

        .button-container {
            display: flex; /* Dùng Flexbox để các nút nằm cùng một hàng */
            justify-content: space-between; /* Căn đều các nút theo chiều ngang */
            gap: 10px; /* Khoảng cách giữa các nút */
        }

        .add-btn {
            background-color: #BC1F23;
            font-size: 0.8rem;
            font-weight: 700;
            outline: none;
            border-radius: 5px;
            border: none;
            color: aliceblue;
            padding: 5px 5px;
            cursor: pointer;
            text-transform: uppercase;
            transition: 0.5s ease-in-out;
        }

        .category-title, .lproduct-title {
            text-align: center;
            padding: 10px;
            background-color: #BC1F23;
            font-weight: bold;
            color: white !important;
        }


        .add-btn:hover {
            background-color: black;
        }

        .container {
            max-width: 1400px; /* Tăng độ rộng tối đa của container */
            width: 100%; /* Đảm bảo container chiếm hết chiều ngang nếu cần */
        }

    </style>

    <div style="text-align: right; margin-right: 100px; ">
        <a class="sort-button" href="?categoryId=${param.categoryId}&sort=asc"
           class="${param.sort == 'asc' ? 'active' : ''}">Giá: Thấp đến cao</a>
        <a class="sort-button" href="?categoryId=${param.categoryId}&sort=desc"
           class="${param.sort == 'desc' ? 'active' : ''}">Giá: Cao đến thấp</a>
    </div>

    <body>
    <div class="container mt-4">
        <div class="row">
            <div class="col-md-2">
                <div class="category-list mb-4">
                    <h5 class="category-title">Categories</h5>
                    <ul class="list-category">
                        <c:forEach var="o" items="${categoriesList}">
                            <!-- Kiểm tra nếu categoryId trong URL trùng với id của danh mục -->
                            <li class="${param.categoryId == o.id ? 'active' : ''}">
                                <a href="?categoryId=${o.id}">${o.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>


                <div class="last-product">
                    <h5 class="lproduct-title">Last Product</h5>
                    <c:if test="${not empty lastp}">
                        <img src="${pageContext.request.contextPath}/${lastp.image}" alt="${lastp.name}">
                        <p>${lastp.name}</p>
                        <p>${lastp.description}</p>
                        <p class="price">${lastp.price}K</p>
                    </c:if>
                    <c:if test="${empty lastp}">
                        <p>No product available</p>
                    </c:if>
                </div>
            </div>

            <div class="col-md-10">
                <div class="row">
                    <c:forEach var="product" items="${productList}">
                        <div class="col-md-3 mb-4">
                            <div class="product-card">
                                <a href="detail?id=${product.id}">
                                    <img class="img-fluid" src="<%= request.getContextPath() %>/${product.image}"
                                         alt="${product.name}">
                                    <h5 class="product-name">${product.name}</h5>
                                    <p class="product-price">${product.price}k</p>
                                </a>
                                <div class="button-container">
                                    <button class="add-btn"
                                            onclick="window.location.href='add-to-cart?id=${product.id}&action=add-cart'">
                                        Add to cart
                                    </button>
                                    <button class="buy-btn"
                                            onclick="window.location.href='add-to-cart?id=${product.id}&action=buy-now'">
                                        Mua ngay
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    </body>

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
                <li><a href="/html/Menu/shop.html">Cửa hàng</a></li>
                <li><a href="/html/Menu/AboutUs.html">Thông tin</a></li>
                <li><a href="/html/Menu/ContactUs.html">Liên hệ</a></li>
                <li><a href="/html/Menu/Cart.html">Giỏ hàng</a></li>
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
<!-- bootstarp cdn -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"
        integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy"
        crossorigin="anonymous"></script>
</body>
</html>