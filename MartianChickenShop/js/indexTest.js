// Mẫu dữ liệu người dùng với vai trò
const users = [
    { username: "user1", password: "password1", role: "user", email: "user1@example.com", diachi: "001, abc, TPHCM" },
    { username: "user2", password: "password2", role: "user", email: "user2@example.com", diachi: "002, abc, TP THU DUC" },
    { username: "user3", password: "password3", role: "user", email: "user3@example.com", diachi: "003, abc, TPHCM" },
    { username: "user4", password: "password4", role: "user", email: "user4@example.com", diachi: "004, abc, TP THU DUC" },
    { username: "user5", password: "password5", role: "user", email: "user5@example.com", diachi: "005, abc, TPHCM" },
    { username: "admin@gmail.com", password: "admin123", role: "admin", email: "22130022@st.hcmuaf.edu.vn", diachi: "admin, abc, TPHCM" },
];

// Mẫu dữ liệu sản phẩm với số lượng
const products = [
    { id: 1, name: "Cánh gà kiểu Thái", price: "50000", quantity: 10, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png" },
    { id: 2, name: "Cánh gà giòn", price: "35000", quantity: 15, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaGion.png" },
    { id: 3, name: "Cánh gà phô mai", price: "45000", quantity: 8, image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaPhoMai.png" }
];

// Hiển thị bảng người dùng
function renderUsers() {
    const userList = document.getElementById('user-list');
    userList.innerHTML = '';  // Clear the table

    users.forEach((user, index) => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${index + 1}</td>
            <td>${user.username}</td>
            <td>${user.password}</td>
            <td>${user.email}</td>
            <td>${user.diachi}</td>
            <td>
                <button class="btn btn-warning" onclick="editUser(${index})">Sửa</button>
                <button class="btn btn-danger" onclick="deleteUser(${index})">Xóa</button>
            </td>
        `;
        userList.appendChild(row);
    });
}

// Hiển thị bảng món ăn
function renderProducts() {
    const productList = document.getElementById('product-list');
    productList.innerHTML = '';  // Clear the table

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

// Chuyển bảng người dùng và món ăn
function showUsersSection() {
    document.getElementById('users-section').style.display = 'block';
    document.getElementById('products-section').style.display = 'none';
    document.getElementById('users-btn').classList.add('active');
    document.getElementById('products-btn').classList.remove('active');
}

function showProductsSection() {
    document.getElementById('products-section').style.display = 'block';
    document.getElementById('users-section').style.display = 'none';
    document.getElementById('products-btn').classList.add('active');
    document.getElementById('users-btn').classList.remove('active');
}

// Thêm người dùng
function addUser() {
    const name = document.getElementById('add-user-username').value;
    const password = document.getElementById('add-user-password').value;
    const role = document.getElementById('add-user-role').value;

    const newUser = {
        username: name,
        password: password,
        role: role,
        email: 'user' + (users.length + 1) + '@example.com',
        diachi: 'Address ' + (users.length + 1)
    };

    users.push(newUser);
    renderUsers();

    const addUserModal = new bootstrap.Modal(document.getElementById('addUserModal'));
    addUserModal.hide();
}

// Thêm món ăn
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

    const addProductModal = new bootstrap.Modal(document.getElementById('addProductModal'));
    addProductModal.hide();
}

// Xóa người dùng
function deleteUser(index) {
    users.splice(index, 1);
    renderUsers();
}

// Xóa món ăn
function deleteProduct(index) {
    products.splice(index, 1);
    renderProducts();
}

// Chỉnh sửa người dùng
function editUser(index) {
    const user = users[index];
    const newName = prompt('Nhập tên mới:', user.username);
    if (newName) {
        user.username = newName;
        renderUsers();
    }
}

// Chỉnh sửa món ăn
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

// Thêm sự kiện cho nút chuyển bảng
document.getElementById('users-btn').addEventListener('click', showUsersSection);
document.getElementById('products-btn').addEventListener('click', showProductsSection);

// Sửa phần modal thêm người dùng
document.querySelector('#addUserModal .btn-primary').addEventListener('click', addUser);

// Sửa phần modal thêm món ăn
document.querySelector('#addProductModal .btn-primary').addEventListener('click', addProduct);
// Hiển thị modal thêm người dùng
// Hiển thị modal thêm người dùng
function showAddUserModal() {
    const addUserModal = new bootstrap.Modal(document.getElementById('addUserModal'));
    addUserModal.show();
}

// Hiển thị modal thêm món ăn
function showAddProductModal() {
    const addProductModal = new bootstrap.Modal(document.getElementById('addProductModal'));
    addProductModal.show();
}

// Thêm người dùng
// Thêm người dùng
function addUser() {
    const username = document.getElementById('add-user-username').value;
    const password = document.getElementById('add-user-password').value;
    const role = document.getElementById('add-user-role').value;

    if (!username || !password || !role) {
        alert('Vui lòng điền đầy đủ thông tin!');
        return;
    }

    const newUser = {
        username: username,
        password: password,
        role: role,
        email: username + "@example.com",
        diachi: 'Địa chỉ ' + (users.length + 1)
    };

    users.push(newUser);
    renderUsers(); // Cập nhật bảng người dùng

    // Đóng modal và reset form
    const addUserModal = new bootstrap.Modal(document.getElementById('addUserModal'));
    addUserModal.hide();

    // Reset input fields
    document.getElementById('add-user-username').value = '';
    document.getElementById('add-user-password').value = '';
    document.getElementById('add-user-role').value = 'user';
}

// Thêm món ăn
// Thêm món ăn
function addProduct() {
    const name = document.getElementById('add-product-name').value;
    const price = document.getElementById('add-product-price').value;
    const quantity = document.getElementById('add-product-quantity').value;
    const image = document.getElementById('add-product-image').value;

    // Kiểm tra nếu có trường nào bị thiếu thông tin
    if (!name || !price || !quantity || !image) {
        alert('Vui lòng điền đầy đủ thông tin !');
        return;
    }

    // Kiểm tra nếu thông tin nhập vào hợp lệ
    if (isNaN(price) || isNaN(quantity)) {
        alert('Giá và số lượng phải là số hợp lệ !');
        return;
    }

    const newProduct = {
        id: products.length + 1,
        name: name,
        price: price,
        quantity: quantity,
        image: image
    };

    products.push(newProduct);
    renderProducts(); // Cập nhật bảng món ăn
    alert('Thêm sản phẩm thành công !');

    // Đóng modal và reset form
    const addProductModal = new bootstrap.Modal(document.getElementById('addProductModal'));
    addProductModal.hide();

    // Reset input fields
    document.getElementById('add-product-name').value = '';
    document.getElementById('add-product-price').value = '';
    document.getElementById('add-product-quantity').value = '';
    document.getElementById('add-product-image').value = '';
}

// Xử lý sự kiện khi nhấn nút đóng modal
document.querySelectorAll('.btn-close').forEach(button => {
    button.addEventListener('click', function () {
        // Reset các trường nhập liệu khi đóng modal
        document.getElementById('add-product-name').value = '';
        document.getElementById('add-product-price').value = '';
        document.getElementById('add-product-quantity').value = '';
        document.getElementById('add-product-image').value = '';
    });
});
