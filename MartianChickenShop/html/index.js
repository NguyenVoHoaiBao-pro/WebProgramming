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
    if (sidebar.style.width === "300px") {
        sidebar.style.width = "0";
    } else {
        sidebar.style.width = "300px";
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
// function addToCart() {
//     let productId = window.location.href.split('/').pop().split('.')[0];
//     // Lấy productId từ URL
//     productId = productId.length == 9 ? productId[8] : productId[8] + productId[9];

//     let noi = document.getElementById('noi').value;
//     let cart = window.localStorage.getItem('cart');
//     if (cart == null) {
//         cart = {};
//     } else {
//         cart = JSON.parse(cart);
//     }
//     cart[productId] = noi;
//     window.localStorage.setItem('cart', JSON.stringify(cart));
//     alert('Sản phẩm đã được thêm vào giỏ hàng');
// }
function addToCart() {
    const isLoggedIn = window.localStorage.getItem('isLoggedIn');
    if (!isLoggedIn) {
        alert('Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng.');
        window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/login.html';
        return; 
    }

    let productId = window.location.href.split('/').pop().split('.')[0];
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
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    // Tìm kiếm người dùng trong danh sách users
    const user = users.find(user => user.username === username && user.password === password);

    if (user) {
        alert('Đăng nhập thành công với tài khoản: ' + username);
        
        if (user.role === 'admin') {
            // Điều hướng đến trang quản trị
            window.location.href = '/WebProgramming/MartianChickenShop/html/Admin.html';
        } else {
            // Điều hướng đến trang menu
            window.location.href = '/WebProgramming/MartianChickenShop/html/index.html';
        }
    } else {
        alert('Sai tên đăng nhập hoặc mật khẩu.');
    }
}

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

// Đảm bảo JavaScript chỉ chạy sau khi trang đã tải
document.addEventListener("DOMContentLoaded", function() {
    // Lấy tất cả các liên kết với class 'toggle-menu' (là "THỰC ĐƠN")
    var toggleMenus = document.querySelectorAll('.toggle-menu');
    
    // Lặp qua tất cả các liên kết và thêm sự kiện click
    toggleMenus.forEach(function(menu) {
        menu.addEventListener('click', function() {
            // Tìm <ul> kế tiếp sau <h4> (submenu)
            var submenu = menu.closest('.menu-section').querySelector('.submenu');
            
            // Toggle hiển thị submenu
            if (submenu.style.display === "none" || submenu.style.display === "") {
                submenu.style.display = "block"; // Hiển thị submenu
            } else {
                submenu.style.display = "none"; // Ẩn submenu
            }
        });
    });
});

// Đảm bảo chạy mã JavaScript sau khi trang đã tải
document.addEventListener('DOMContentLoaded', function() {
    // Lắng nghe sự kiện khi người dùng nhập từ khóa tìm kiếm
    document.getElementById('searchInput').addEventListener('input', function() {
        let query = this.value.trim().toLowerCase();
        
        if (query) {
            searchProducts(query);
        } else {
            clearSearchResults(); // Nếu ô tìm kiếm trống, ẩn kết quả
        }
    });
});

function searchProducts(query) {
    const results = Object.values(productList).filter(product =>
        product.name.toLowerCase().includes(query)
    );

    if (results.length > 0) {
        displaySearchResults(results);
    } else {
        clearSearchResults(); // Nếu không có kết quả, ẩn đi
    }
}

// Hàm hiển thị kết quả tìm kiếm
function displaySearchResults(results) {
    let resultContainer = document.getElementById('searchResults');
    resultContainer.innerHTML = ''; // Xóa các kết quả cũ

    results.forEach(product => {
        let productElement = document.createElement('div');
        productElement.classList.add('search-result');
        productElement.innerHTML = `
            <img src="${product.image}" alt="${product.name}">
            <p><strong>${product.name}</strong> - ${product.price} VND</p>
        `;
        resultContainer.appendChild(productElement);
    });

    // Hiển thị phần tử kết quả tìm kiếm
    resultContainer.style.display = 'block';
}

// Hàm xóa kết quả tìm kiếm
function clearSearchResults() {
    let resultContainer = document.getElementById('searchResults');
    resultContainer.innerHTML = ''; // Xóa tất cả kết quả
    resultContainer.style.display = 'none'; // Ẩn kết quả
}
const users = [
    { username: "user1", password: "password1", role: "user" },
    { username: "user2", password: "password2", role: "user" },
    { username: "user3", password: "password3", role: "user" },
    { username: "user4", password: "password4", role: "user" },
    { username: "user5", password: "password5", role: "user" },
    { username: "admin@gmail.com", password: "admin123", role: "admin" }
  ];

const products = [
    { id: 1, name: "Cánh gà kiểu Thái", price: "50000", quantity: 10, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png" },
    { id: 2, name: "Cánh gà giòn", price: "35000", quantity: 15, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaGion.png" },
    { id: 3, name: "Cánh gà phô mai", price: "45000", quantity: 8, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaPhoMai.png" }
];


// Hàm lấy productId từ URL
function getProductIdFromUrl() {
    const url = window.location.href; 
    const fileName = url.split('/').pop(); 
    const productId = parseInt(fileName.split('sproduct')[1].split('.html')[0]); 
    return productId;
}

function displayAvailableQuantity() {
    const productId = getProductIdFromUrl(); 
    const product = products.find(p => p.id === productId); 

    if (product) {
        const quantityElement = document.getElementById('available-quantity'); 
        if (quantityElement) {
            quantityElement.textContent = `${product.quantity} sản phẩm có sẵn`;
        }
    }
}

document.addEventListener("DOMContentLoaded", displayAvailableQuantity);




