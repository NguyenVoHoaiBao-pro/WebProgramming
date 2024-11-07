const productList = {
    "1": {
        "name": "Cánh gà kiểu Thái",
        "price": 50000,
        "image": "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png"
    },
    "2": {
        "name": "Wellesley Women's Laptop Bag",
        "price": 3990,
        "image": "/images/leather/products/78image.jpg"
    },
    // Tiếp tục với các sản phẩm khác...
    "21": {
        "name": "Pick-Me-Up Wallet",
        "price": 1990,
        "image": "/images/leather/products/33image.jpg"
    }
};

function addToCart() {
    // Lấy productId từ URL một cách an toàn
    const urlParts = window.location.href.split('/');
    let productId = urlParts[urlParts.length - 1].split('.')[0];
    productId = parseInt(productId.match(/\d+/)[0]);

    let noi = parseInt(document.getElementById('noi').value);
    let cart = JSON.parse(window.localStorage.getItem('cart')) || {};

    if(cart[productId]) {
        cart[productId] += noi;
    } else {
        cart[productId] = noi;
    }

    window.localStorage.setItem('cart', JSON.stringify(cart));
    alert('Sản phẩm đã được thêm vào giỏ hàng');
}

function removeFromCart(productId) {
    let cart = JSON.parse(window.localStorage.getItem('cart')) || {};

    if (cart[productId]) {
        delete cart[productId];
        window.localStorage.setItem('cart', JSON.stringify(cart));
        // Cập nhật UI thay vì reload trang nếu cần
    }
}

function login(event) {
    event.preventDefault();
    let email = document.getElementById('email').value;
    alert('Đăng nhập thành công với email ' + email);
    window.location.href = '/WebProgramming/MartianChickenShop/html/index.html';
}

function contactUs(event) {
    event.preventDefault();
    let name = document.getElementById('name').value;
    let email = document.getElementById('email').value;
    let message = document.getElementById('message').value;
    alert('Cảm ơn bạn đã liên hệ với chúng tôi. Chúng tôi sẽ liên hệ lại với bạn sớm nhất có thể.');
    window.location.href = '/WebProgramming/MartianChickenShop/html/index.html';
}

function signUp(event) {
    event.preventDefault();
    let email = document.getElementById('email').value;
    alert('Đăng ký thành công với email ' + email);
    window.location.href = '/WebProgramming/MartianChickenShop/html/Login.html';
}
