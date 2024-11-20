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

let currentUser = null; // Biến lưu trữ người dùng hiện tại

// Hàm đăng nhập
function login(username, password) {
    const user = users.find(u => u.username === username && u.password === password);
    if (user) {
        currentUser = user;
        alert(`Đăng nhập thành công! Xin chào ${user.role === "admin" ? "Admin" : "User"} ${username}`);
        if (user.role === "admin") {
            showUsersSection();
        } else {
            showProductsSection();
        }
    } else {
        alert("Tên đăng nhập hoặc mật khẩu không đúng.");
    }
}

// Hiển thị danh sách người dùng (chỉ admin)
function displayUsers() {
    if (currentUser && currentUser.role === "admin") {
        const userTable = document.getElementById("userTable");
        userTable.innerHTML = "";

        users.forEach((user, index) => {
            const row = `
                <tr>
                    <td>${index + 1}</td>
                    <td>${user.username}</td>
                    <td>${user.password}</td>
                    <td>${user.role}</td>
                    <td>
                        <button class="btn btn-danger" onclick="deleteUser(${index})">Xóa</button>
                    </td>
                </tr>
            `;
            userTable.innerHTML += row;
        });
    }
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
                <td><img src="${product.image}" alt="${product.name}" style="width: 100px; height: auto;"></td>
                <td>
                    <button class="btn btn-danger" onclick="deleteProduct(${index})">Xóa</button>
                </td>
            </tr>
        `;
        productTable.innerHTML += row;
    });
}

// Thêm người dùng (chỉ admin)
function addUser() {
    if (currentUser && currentUser.role === "admin") {
        const username = prompt("Nhập tên người dùng:");
        const password = prompt("Nhập mật khẩu:");
        const role = prompt("Nhập vai trò (admin/user):");

        if (username && password && role) {
            users.push({ username, password, role });
            displayUsers();
        } else {
            alert("Vui lòng nhập đầy đủ thông tin.");
        }
    } else {
        alert("Chỉ admin mới có quyền thêm người dùng.");
    }
}

// Xóa người dùng (chỉ admin)
function deleteUser(index) {
    if (currentUser && currentUser.role === "admin") {
        users.splice(index, 1);
        displayUsers();
    } else {
        alert("Chỉ admin mới có quyền xóa người dùng.");
    }
}

// Thêm sản phẩm
function addProduct() {
    const id = products.length + 1;
    const name = prompt("Nhập tên sản phẩm:");
    const price = prompt("Nhập giá:");
    const quantity = prompt("Nhập số lượng:");
    const image = prompt("Nhập đường dẫn hình ảnh:");

    if (name && price && quantity && image) {
        products.push({ id, name, price, quantity, image });
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

// Hiển thị mục Quản lý Người Dùng và ẩn Quản lý Sản Phẩm (chỉ admin)
function showUsersSection() {
    if (currentUser && currentUser.role === "admin") {
        document.getElementById("users-section").style.display = "block";
        document.getElementById("products-section").style.display = "none";
        displayUsers();
    } else {
        alert("Chỉ admin mới được truy cập vào mục Quản lý Người Dùng.");
    }
}

// Hiển thị mục Quản lý Sản Phẩm và ẩn Quản lý Người Dùng
function showProductsSection() {
    document.getElementById("products-section").style.display = "block";
    document.getElementById("users-section").style.display = "none";
    displayProducts();
}

// Khởi tạo chế độ hiển thị mặc định khi tải trang
document.addEventListener("DOMContentLoaded", () => {
    login("admin@gmail.com", "admin123");

    document.querySelector("a[href='#users-section']").onclick = showUsersSection;
    document.querySelector("a[href='#products-section']").onclick = showProductsSection;
});

document.getElementById("available-quantity").textContent = product.quantity;

// Lưu thông tin người dùng vào localStorage
function signUp(event) {
    event.preventDefault(); // Ngăn chặn reload trang
    
    // Lấy dữ liệu từ form
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const address = document.getElementById('address').value;
    const district = document.getElementById('district').value;
    const sdt = document.getElementById('sdt').value;

    // Kiểm tra mật khẩu khớp
    if (password !== confirmPassword) {
        alert('Mật khẩu không khớp!');
        return;
    }

    // Tạo đối tượng người dùng
    const user = {
        name,
        email,
        address,
        district,
        sdt
    };

    // Lưu vào localStorage
    let users = JSON.parse(localStorage.getItem('users')) || [];
    users.push(user);
    localStorage.setItem('users', JSON.stringify(users));

    alert('Đăng ký thành công!');
    document.getElementById('signUpForm').reset(); // Reset form
}