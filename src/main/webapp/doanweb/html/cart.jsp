
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>

<head>
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
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
                crossorigin="anonymous"></script>

        <!-- bootstarp stackpath cdn -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
              integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
              crossorigin="anonymous">
        <!-- Bootstrap icons cdn-->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">
        <style>
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

            .ShopMore:hover {
                background-color: #BC1F23;
                color: black;
            }

            .form-group {
                display: flex;
                align-items: center;
                /* Căn giữa theo chiều dọc */
                margin-bottom: 1rem;
            }

            .form-group label {
                flex: 0 0 30%;
                /* Độ rộng của label chiếm 30% */
                margin-bottom: 0;
                /* Loại bỏ khoảng cách mặc định */
                color: #343a40;
                font-weight: bold;
            }

            .form-group select {
                flex: 1;
                /* Chiếm phần còn lại của hàng */
                padding: 0.375rem 0.75rem;
                font-size: 1rem;
                line-height: 1.5;
                border: 1px solid #ced4da;
                border-radius: 0.25rem;
                background-color: #fff;
            }

            .quantity-selector {
                display: flex;
                align-items: center; /* Căn giữa các phần tử theo chiều dọc */
                justify-content: center; /* Căn giữa các phần tử theo chiều ngang */
                gap: 10px; /* Khoảng cách giữa các phần tử */
                padding-top: 12px;
            }

            .quantity-selector button {
                padding: 5px 10px;
                font-size: 20px; /* Điều chỉnh kích thước chữ để dễ nhìn */
                border: 1px solid #ccc;
                background-color: #f0f0f0;
                cursor: pointer;
                width: 35px; /* Điều chỉnh kích thước nút */
                height: 35px; /* Điều chỉnh kích thước nút */
                display: flex;
                align-items: center; /* Căn giữa nội dung nút theo chiều dọc */
                justify-content: center; /* Căn giữa nội dung nút theo chiều ngang */
            }

            .quantity-selector button:hover {
                background-color: #e0e0e0;
            }

            .quantity-selector input {
                width: 50px; /* Điều chỉnh chiều rộng của ô nhập liệu */
                text-align: center;
                padding: 5px;
                font-size: 16px;
                border: 1px solid #ccc;
            }


        </style>
    </head>
    <title></title>
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


<!-- Cart Section -->
<section id="featured" class="mt-3 pt-1">
    <div class="container mx-auto mt-5 px-5 pt-5 text-center">
        <h3 class="mt-5 pt-5">Giỏ Hàng Của Bạn</h3>
        <hr class="border border-danger border-2 opacity-75 mx-auto">
    </div>
</section>

<section id="cart-container" class="container-fluid my-5">
    <table id="cart-table">
        <thead>
        <tr>
            <td class="col-12 col-md-2">Hình ảnh</td> <!-- Chiếm 20% trên màn hình lớn -->
            <td class="col-12 col-md-2">Sản phẩm</td> <!-- Chiếm 20% trên màn hình lớn -->
            <td class="col-12 col-md-1">Giá</td> <!-- Chiếm 15% trên màn hình lớn -->
            <td class="col-12 col-md-2">Số lượng</td> <!-- Chiếm 20% trên màn hình lớn -->
            <td class="col-12 col-md-1">Tổng tiền</td> <!-- Chiếm 10% trên màn hình lớn -->
            <td class="col-12 col-md-1">Xóa</td> <!-- Chiếm 10% trên màn hình lớn -->
        </tr>
        </thead>
        <tbody>
        <!-- Hiển thị sản phẩm từ giỏ hàng -->
        <c:forEach var="item" items="${cart}">
            <tr>

                <td>
                    <img src="${item.product.image}" alt="${item.product.name}" width="100">
                </td>
                <td>${item.product.name}</td>
                <td>${item.product.price}K</td>
                <td>
                    <form action="quantity-inc-dec" method="post">
                        <input type="hidden" name="id" value="${item.product.id}">
                        <div class="quantity-selector">
                            <button type="submit" name="action" value="dec" class="btn-minus">-</button>
                            <input type="number" name="quantity" value="${item.quantity}" min="1" class="form-control"
                                   disabled>
                            <button type="submit" name="action" value="inc" class="btn-plus">+</button>
                        </div>
                    </form>

                </td>
                <td>${item.quantity * item.product.price}k</td>
                <td>
                    <button type="button" class="remove-btn"
                            onclick="window.location.href='add-to-cart?id=${item.product.id}&action=remove'">
                        <i class="bi bi-trash3"></i>
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>

<section id="cart-bottom" class="container my-5">
    <div class="row justify-content-center">
        <div class="col-lg-8 col-md-10 col-12">
            <div class="card shadow rounded-3">
                <div class="card-body p-4">
                    <h4 class="mb-4">Tổng giỏ hàng</h4>

                    <!-- Tạm tính -->
                    <div class="d-flex justify-content-between mb-3">
                        <h6 class="mb-0">Tạm tính</h6>
                        <strong>
                            <fmt:formatNumber value="${sessionScope.totalPrice * 1000}" type="currency" currencySymbol="VND" />
                        </strong>
                    </div>

                    <!-- Form chọn vận chuyển -->
                    <form action="cart" method="get" id="shipping-form">
                        <!-- Đơn vị vận chuyển -->
                        <div class="form-group mb-3">
                            <label for="shippingProvider"><strong>Chọn đơn vị vận chuyển</strong></label>
                            <select class="form-control" name="shippingProvider" id="shippingProvider">
                                <option value="GHN">Giao hàng nhanh</option>
                                <option value="GHTK">Giao hàng tiết kiệm</option>
                            </select>
                        </div>

                        <!-- Chọn tỉnh -->
                        <div class="form-group mb-3">
                            <label for="province"><strong>Chọn Tỉnh/Thành phố</strong></label>
                            <select name="province" id="province" class="form-control" onchange="document.getElementById('shipping-form').submit();">
                                <option value="">-- Chọn tỉnh --</option>
                                <option value="Hanoi" ${param.province == 'Hanoi' ? 'selected' : ''}>Hà Nội</option>
                                <option value="HoChiMinh" ${param.province == 'HoChiMinh' ? 'selected' : ''}>TP. Hồ Chí Minh</option>
                                <option value="DaNang" ${param.province == 'DaNang' ? 'selected' : ''}>Đà Nẵng</option>
                            </select>
                        </div>

                        <!-- Chọn quận huyện -->
                        <div class="form-group mb-4">
                            <label for="district"><strong>Chọn Quận/Huyện</strong></label>
                            <select name="district" id="district" class="form-control" onchange="document.getElementById('shipping-form').submit();">
                                <option value="">-- Chọn quận/huyện --</option>
                                <c:if test="${param.province == 'Hanoi'}">
                                    <option value="HoanKiem" ${param.district == 'HoanKiem' ? 'selected' : ''}>Hoàn Kiếm</option>
                                    <option value="BaDinh" ${param.district == 'BaDinh' ? 'selected' : ''}>Ba Đình</option>
                                </c:if>
                                <c:if test="${param.province == 'HoChiMinh'}">
                                    <option value="TanBinh" ${param.district == 'TanBinh' ? 'selected' : ''}>Tân Bình</option>
                                    <option value="Quan1" ${param.district == 'Quan1' ? 'selected' : ''}>Quận 1</option>
                                </c:if>
                                <c:if test="${param.province == 'DaNang'}">
                                    <option value="HaiChau" ${param.district == 'HaiChau' ? 'selected' : ''}>Hải Châu</option>
                                </c:if>
                            </select>
                        </div>
                    </form>

                    <p hidden id="dataValues"
                       data-totalprice="${sessionScope.totalPrice}"
                       data-shipping="${shippingCost}">
                    </p>

                <div class="payment-method" style="margin-top: 30px; text-align: center;">
                    <h3>Chọn hình thức thanh toán</h3>
                    <select id="paymentSelect" class="sort-button">
                        <option value="">-- Vui lòng chọn --</option>
                        <option value="direct">Thanh toán trực tiếp</option>
                        <option value="card">Thanh toán bằng thẻ</option>
                    </select>
                    <button onclick="redirectPayment()" class="sort-button" style="margin-top: 20px;">Tiếp tục</button>
                </div>
            </div>
        </div>
    </div>
</section>


</body>
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
<script>
    // Cập nhật danh sách quận/huyện theo tỉnh được chọn
    const districtOptions = {
        Hanoi: [
            { value: "HoanKiem", text: "Hoàn Kiếm" },
            { value: "BaDinh", text: "Ba Đình" }
        ],
        HoChiMinh: [
            { value: "TanBinh", text: "Tân Bình" },
            { value: "Quan1", text: "Quận 1" }
        ],
        DaNang: [
            { value: "HaiChau", text: "Hải Châu" }
        ]
    };

    document.getElementById("province").addEventListener("change", function () {
        const province = this.value;
        const districtSelect = document.getElementById("district");

        districtSelect.innerHTML = '<option value="">-- Chọn quận/huyện --</option>';

        if (districtOptions[province]) {
            districtOptions[province].forEach(function (district) {
                const option = document.createElement("option");
                option.value = district.value;
                option.text = district.text;
                districtSelect.appendChild(option);
            });
        }
    });
    function redirectPayment() {
        const selectedOption = document.getElementById("paymentSelect").value;

        if (selectedOption === "direct") {
            window.location.href = "<%= request.getContextPath() %>/doanweb/html/DirectDeposit.jsp";  // thay link thật
        } else if (selectedOption === "card") {
            window.location.href = "<%= request.getContextPath() %>/doanweb/html/Transfer.jsp"; // thay link thật
        } else {
            alert("Vui lòng chọn hình thức thanh toán.");
        }
    }

    // Xử lý áp dụng mã giảm giá
    function applyVoucher() {
        const voucherSelect = document.getElementById('voucherSelect');
        const selectedValue = parseFloat(voucherSelect.value);

        const subtotal = parseFloat('${sessionScope.totalPrice}') * 1000; // Chuyển về VND
        const shipping = parseFloat('${shippingCost}'); // Đảm bảo có thể thay đổi
        let discountRate = 0;

        if (selectedValue === 0.10 && subtotal >= 100000) {
            discountRate = 0.10;
        } else if (selectedValue === 0.15 && subtotal >= 300000) {
            discountRate = 0.15;
        } else if (selectedValue === 0.20 && subtotal >= 500000) {
            discountRate = 0.20;
        }

        const discountAmount = subtotal * discountRate;
        const total = subtotal + shipping - discountAmount;

        // Cập nhật UI
        document.getElementById('discountText')?.innerText = discountAmount.toLocaleString('vi-VN') + ' VND';
        document.getElementById('discount-value')?.innerText = (discountAmount / 1000).toFixed(0) + 'K';

        document.getElementById('totalAmountText')?.innerText = total.toLocaleString('vi-VN') + ' VND';
        document.getElementById('total-value')?.innerText = (total / 1000).toFixed(0) + 'K';
    }

</script>


</html>


