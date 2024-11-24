// Mẫu dữ liệu người dùng với vai trò
const users = [
    { username: "user1", password: "password1", role: "user" },
    { username: "user2", password: "password2", role: "user" },
    { username: "user3", password: "password3", role: "user" },
    { username: "user4", password: "password4", role: "user" },
    { username: "user5", password: "password5", role: "user" },
    { username: "admin@gmail.com", password: "admin123", role: "admin" }
];

// Mẫu dữ liệu sản phẩm với số lượng
const products = [
    { id: 1, name: "Cánh gà kiểu Thái", price: "50000", quantity: 10, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png" },
    { id: 2, name: "Cánh gà giòn", price: "35000", quantity: 15, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaGion.png" },
    { id: 3, name: "Cánh gà phô mai", price: "45000", quantity: 8, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaPhoMai.png" }
];

// Hàm hiển thị danh sách người dùng
function renderUsers() {
    const userList = document.getElementById('user-list');
    userList.innerHTML = '';

    users.forEach((user, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${user.name}</td>
            <td>${user.phone}</td>
            <td>${user.email}</td>
            <td>${user.address}</td>
            <td>
                <button class="btn btn-warning" onclick="editUser(${index})">Sửa</button>
                <button class="btn btn-danger" onclick="deleteUser(${index})">Xóa</button>
            </td>
        `;
        userList.appendChild(row);
    });
}

// Hàm hiển thị danh sách món ăn
function renderProducts() {
    const productList = document.getElementById('product-list');
    productList.innerHTML = '';

    products.forEach((product, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${product.name}</td>
            <td>${product.price}</td>
            <td>${product.quantity}</td>
            <td><img src="${product.image}" alt="${product.name}" width="50"></td>
            <td>
                <button class="btn btn-warning" onclick="editProduct(${index})">Sửa</button>
                <button class="btn btn-danger" onclick="deleteProduct(${index})">Xóa</button>
            </td>
        `;
        productList.appendChild(row);
    });
}

// Hàm thêm người dùng
function addUser() {
    const name = document.getElementById('add-user-username').value;
    const password = document.getElementById('add-user-password').value;
    const role = document.getElementById('add-user-role').value;

    const newUser = {
        name: name,
        password: password,
        role: role,
        phone: '0123456789', // Thông tin giả
        email: 'user@example.com', // Thông tin giả
        address: '123 Main St' // Thông tin giả
    };

    users.push(newUser);
    renderUsers();
    $('#addUserModal').modal('hide');
}

// Hàm thêm món ăn
function addProduct() {
    const name = document.getElementById('add-product-name').value;
    const price = document.getElementById('add-product-price').value;
    const quantity = document.getElementById('add-product-quantity').value;
    const image = document.getElementById('add-product-image').value;

    const newProduct = {
        name: name,
        price: price,
        quantity: quantity,
        image: image
    };

    products.push(newProduct);
    renderProducts();
    $('#addProductModal').modal('hide');
}

// Hàm xóa người dùng
function deleteUser(index) {
    users.splice(index, 1);
    renderUsers();
}

// Hàm xóa món ăn
function deleteProduct(index) {
    products.splice(index, 1);
    renderProducts();
}

// Hàm chỉnh sửa người dùng
function editUser(index) {
    const user = users[index];
    const newName = prompt('Nhập tên mới:', user.name);
    if (newName) {
        user.name = newName;
        renderUsers();
    }
}

// Hàm chỉnh sửa món ăn
function editProduct(index) {
    const product = products[index];
    const newName = prompt('Nhập tên món ăn mới:', product.name);
    if (newName) {
        product.name = newName;
        renderProducts();
    }
}

// Gọi hàm render ban đầu
renderUsers();
renderProducts();
