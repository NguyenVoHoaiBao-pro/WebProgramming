// TODO: Add all products to the productList object

const productList = {
    "1": {
        "name": "Cánh gà kiểu Thái",
        "price": ">50.000 đ",
        "image": "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png"
    },
    "2": {
        "name": "Wellesley Women's Laptop Bag",
        "price": "3990",
        "image": "/images/leather/products/78image.jpg"
    },
    "3": {
        "name": "Tudor Leather Messenger Bag",
        "price": "5990",
        "image": "/images/leather/products/113image.jpg"
    },
    "4": {
        "name": "Classic Leather Flap",
        "price": "5990",
        "image": "/images/leather/products/111image.jpg"
    },
    "5": {
        "name": "Astor Leather Laptop Sleeve",
        "price": "5990",
        "image": "/images/leather/products/49image.jpg"
    },
    "6": {
        "name": "The Chronicle Leather Backpack Canvas",
        "price": "8990",
        "image": "/images/leather/products/39image.jpg"
    },
    "7": {
        "name": "Business Card Case",
        "price": "990",
        "image": "/images/leather/products/44image.jpg"
    },
    "8": {
        "name": "Hamlet Leather Satchel",
        "price": "6990",
        "image": "/images/leather/products/76image.jpg"
    },
    "9": {
        "name": "Wallstreeter Leather Laptop Sleeve",
        "price": "4990",
        "image": "/images/leather/products/41image.jpg"
    },
    "10": {
        "name": "Aberdeen Leather Folder",
        "price": "2990",
        "image": "/images/leather/products/60image.jpg"
    },
    "11": {
        "name": "Doc Holiday Leather Briefcase",
        "price": "2990",
        "image": "./images/leather/products/15image.jpg"
    },
    "12": {
        "name": "Blossom Leather Sling",
        "price": "1990",
        "image": "/images/leather/products/68image.jpg"
    },
    "13": {
        "name": "Dahlia Leather Clutch",
        "price": "1290",
        "image": "/images/leather/products/70image.jpg"
    },
    "14": {
        "name": "Anemone Leather Sling",
        "price": "2990",
        "image": "/images/leather/products/82image.jpg"
    },
    "15": {
        "name": "Strawberry Fields Leather Backpack",
        "price": "3990",
        "image": "/images/leather/products/80image.jpg"
    },
    "16": {
        "name": "Sioux Leather Saddlebag",
        "price": "4990",
        "image": "/images/leather/products/53image.jpg"
    },
    "17": {
        "name": "Twisty Lock Wallet",
        "price": "1990",
        "image": "/images/leather/products/97image.jpg"
    },
    "18": {
        "name": "Luggage Flier Tag",
        "price": "400",
        "image": "/images/leather/products/105image.jpg"
    },
    "19": {
        "name": "Casual Leather Belt",
        "price": "1990",
        "image": "/images/leather/products/108image.jpg"
    },
    "20": {
        "name": "Leather Bootstrap Wallet",
        "price": "1990",
        "image": "/images/leather/products/86image.jpg"
    },
    "21": {
        "name": "Pick-Me-Up Wallet",
        "price": "1990",
        "image": "/images/leather/products/33image.jpg"
    }
}

function addToCart() {
    let productId = window.location.href.split('/').pop().split('.')[0];
    productId = productId.length == 9 ? productId[8] : productId[8] + productId[9];
    let noi = parseInt(document.getElementById('noi').value);
    let cart = JSON.parse(window.localStorage.getItem('cart')) || {};

    // Cập nhật số lượng nếu sản phẩm đã có trong giỏ hàng
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
        window.location.reload();
    }
}

function login(event) {
    event.preventDefault();
    let email = document.getElementById('email').value;
    alert('Logged in successfully as ' + email);
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
    alert('Signed up successfully as ' + email);
    window.location.href = '/WebProgramming/MartianChickenShop/html/Login.html';
}