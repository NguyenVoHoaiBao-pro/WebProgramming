document.addEventListener('DOMContentLoaded', function() {
    // Thêm sự kiện cho nút đóng sidebar
    document.getElementById('closeBtn').addEventListener('click', function() {
        var sidebar = document.getElementById("mySideBar");
        sidebar.style.width = "0";
    });
});
document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('closeBtn').addEventListener('click', function() {
        var sidebar = document.getElementById("mySideBar");
        sidebar.style.width = "0";
    });
});

// Hàm mở/đóng sidebar
function toggleMenu() {
    var sidebar = document.getElementById("mySideBar");
    if (sidebar.style.width === "350px") {
        sidebar.style.width = "0";
    } else {
        sidebar.style.width = "350px";
    }
    // Đảm bảo sự kiện đóng sidebar luôn hoạt động
    document.getElementById('closeBtn').addEventListener('click', function() {
        sidebar.style.width = "0";
    });
}

// Danh sách sản phẩm
const productList = {
    "1": {
        "name": "Cánh gà kiểu Thái",
        "price": "50000",
        "image": "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png"
    },
    "2": {
        "name": "Cánh gà giòn",
        "price": "35000",
        "image": "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaGion.png"
    },
    "3": {
        "name": "Cánh gà phô mai",
        "price": "45000",
        "image": "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaPhoMai.png"
    }
};

// Hàm thêm sản phẩm vào giỏ hàng
function addToCart() {
    let productId = window.location.href.split('/').pop().split('.')[0];
    // Lấy productId từ URL
    productId = productId.length == 9 ? productId[8] : productId[8] + productId[9];

    let noi = document.getElementById('noi').value;
    let cart = window.localStorage.getItem('cart');
    if (cart == null) {
        cart = {};
    } else {
        cart = JSON.parse(cart);
    }
    cart[productId] = noi;
    window.localStorage.setItem('cart', JSON.stringify(cart));
    alert('Sản phẩm đã được thêm vào giỏ hàng');
}

// Hàm xóa sản phẩm khỏi giỏ hàng
function removeFromCart(productId) {
    let cart = window.localStorage.getItem('cart');
    if (cart == null) {
        cart = {};
    } else {
        cart = JSON.parse(cart);
    }
    delete cart[productId];
    window.localStorage.setItem('cart', JSON.stringify(cart));
    window.location.reload();
}

// Hàm đăng nhập
function login(event) {
    event.preventDefault();
    let email = document.getElementById('email').value;
    
    alert('Đăng nhập thành công với ' + email);
    window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/index.html';
}

// Hàm xử lý liên hệ
function contactUs(event) {
    event.preventDefault();
    let name = document.getElementById('name').value;
    let email = document.getElementById('email').value;
    let message = document.getElementById('message').value;

    alert('Cảm ơn bạn đã liên hệ với chúng tôi. Chúng tôi sẽ liên lạc lại với bạn sớm.');
    window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/index.html';
}

// Hàm đăng ký
function signUp(event) {
    event.preventDefault();
    let email = document.getElementById('email').value;
    
    alert('Đăng ký thành công với ' + email);
    window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/Login.html';
}

// Hàm xác thực form đăng ký
function validateForm() {
    // Lấy giá trị từ các trường form
    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value.trim();
    const confirmPassword = document.getElementById('confirmPassword').value.trim();
    const address = document.getElementById('address').value.trim();
    const district = document.getElementById('district').value;
    const phone = document.getElementById('sdt').value.trim();

    // Kiểm tra nếu có trường nào bỏ trống
    if (!name || !email || !password || !confirmPassword || !address || !district || !phone) {
        alert('Vui lòng điền đầy đủ tất cả các trường.');
        return false;
    }

    // Kiểm tra mật khẩu và mật khẩu xác nhận có khớp không
    if (password !== confirmPassword) {
        alert('Mật khẩu và mật khẩu xác nhận không khớp.');
        return false;
    }

    // Kiểm tra định dạng email
    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (!emailPattern.test(email)) {
        alert('Vui lòng nhập email hợp lệ.');
        return false;
    }

    // Kiểm tra độ dài số điện thoại (ít nhất 10 số)
    if (phone.length < 10 || phone.length > 11) {
        alert('Vui lòng nhập số điện thoại hợp lệ (10-11 chữ số).');
        return false;
    }

    // Form hợp lệ
    return true;
}

document.querySelector('.toggle-menu').addEventListener('click', function() {
    var submenu = this.parentElement.nextElementSibling; // Chọn <ul> của "THỰC ĐƠN"
    
    // Toggle hiển thị submenu
    if (submenu.style.display === "none" || submenu.style.display === "") {
        submenu.style.display = "block"; // Hiển thị submenu
    } else {
        submenu.style.display = "none"; // Ẩn submenu
    }
});
