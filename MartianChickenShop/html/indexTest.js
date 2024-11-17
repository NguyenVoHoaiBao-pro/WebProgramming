// Mẫu dữ liệu người dùng
const users = [
    { id: 1, name: "Nguyễn Văn A", email: "a@example.com", role: "Admin" },
    { id: 2, name: "Trần Thị B", email: "b@example.com", role: "User" }
];

// Mẫu dữ liệu sản phẩm
const products = [
    { id: 1, name: "Gà Sao", price: 50000, quantity: 10 },
    { id: 2, name: "Gà Hấp Hành", price: 70000, quantity: 5 }
];

// Hiển thị danh sách người dùng
function displayUsers() {
    const userTable = document.getElementById("userTable");
    userTable.innerHTML = "";

    users.forEach((user, index) => {
        const row = `
            <tr>
                <td>${user.id}</td>
                <td>${user.name}</td>
                <td>${user.email}</td>
                <td>${user.role}</td>
                <td>
                    <button class="btn btn-danger" onclick="deleteUser(${index})">Xóa</button>
                </td>
            </tr>
        `;
        userTable.innerHTML += row;
    });
}

// Hiển thị danh sách sản phẩm
function displayProducts() {
    const productTable = document.getElementById("productTable");
    productTable.innerHTML = "";

    products.forEach((product, index) => {
        const row = `
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td>${product.price} VND</td>
                <td>${product.quantity}</td>
                <td>
                    <button class="btn btn-danger" onclick="deleteProduct(${index})">Xóa</button>
                </td>
            </tr>
        `;
        productTable.innerHTML += row;
    });
}

// Thêm người dùng
function addUser() {
    const id = users.length + 1;
    const name = prompt("Nhập tên người dùng:");
    const email = prompt("Nhập email:");
    const role = prompt("Nhập vai trò (Admin/User):");

    if (name && email && role) {
        users.push({ id, name, email, role });
        displayUsers();
    } else {
        alert("Vui lòng nhập đầy đủ thông tin.");
    }
}

// Xóa người dùng
function deleteUser(index) {
    users.splice(index, 1);
    displayUsers();
}

// Thêm sản phẩm
function addProduct() {
    const id = products.length + 1;
    const name = prompt("Nhập tên sản phẩm:");
    const price = prompt("Nhập giá:");
    const quantity = prompt("Nhập số lượng:");

    if (name && price && quantity) {
        products.push({ id, name, price, quantity });
        displayProducts();
    } else {
        alert("Vui lòng nhập đầy đủ thông tin.");
    }
}

// Xóa sản phẩm
function deleteProduct(index) {
    products.splice(index, 1);
    displayProducts();
}

// Khởi tạo bảng khi tải trang
document.addEventListener("DOMContentLoaded", () => {
    displayUsers();
    displayProducts();
});
