

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
  
}

function addToCart() {
    let productId = window.location.href.split('/').pop().split('.')[0];
    // productId = productId.charAt(productId.length - 1);
    // if(productId.length == 9)
    // {
    //     productId = productId[8];
    // }
    // else if(productId.length == 10)
    // {
    //     productId = productId[8] + productId[9];
    // }
    productId = productId.length == 9 ? productId[8] : productId[8] + productId[9];
    let noi = document.getElementById('noi').value;
    let cart = window.localStorage.getItem('cart');
    if(cart == null) {
        cart = {};
    } else {
        cart = JSON.parse(cart);
    }
    cart[productId] = noi;
    window.localStorage.setItem('cart', JSON.stringify(cart));
    alert('Sản phẩm đã được thêm vào giỏ hàng');
}

function removeFromCart(productId) {
    let cart = window.localStorage.getItem('cart');
    if(cart == null) {
        cart = {};
    } else {
        cart = JSON.parse(cart);
    }
    delete cart[productId];
    window.localStorage.setItem('cart', JSON.stringify(cart));
    window.location.reload();
}

function login() {
    event.preventDefault();
    let email = document.getElementById('email').value;

    alert('Đăng nhập thành công với ' + email);

    window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/index.html';
}

function contactUs() {
    event.preventDefault();
    let name = document.getElementById('name').value;
    let email = document.getElementById('email').value;
    let message = document.getElementById('message').value;

    alert('Cảm ơn bạn đã liên hệ với chúng tôi. Chúng tôi sẽ liên lạc lại với bạn sớm.');

    window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/index.html';
}

function signUp(){
    event.preventDefault();
    let email = document.getElementById('email').value;
    alert('Đăng ký thành công với ' + email);
    window.location.href = '/WebProgramming/MartianChickenShop/html/Menu/Login.html';
}