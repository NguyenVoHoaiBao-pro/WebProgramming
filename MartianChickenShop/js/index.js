document.addEventListener("DOMContentLoaded", function () {
  // Thêm sự kiện cho nút đóng sidebar
  document.getElementById("closeBtn").addEventListener("click", function () {
    var sidebar = document.getElementById("mySideBar");
    sidebar.style.width = "0";
  });
});
document.addEventListener("DOMContentLoaded", function () {
  document.getElementById("closeBtn").addEventListener("click", function () {
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
  document.getElementById("closeBtn").addEventListener("click", function () {
    sidebar.style.width = "0";
  });
}

// Danh sách sản phẩm
const productList = {
  1: {
    name: "Cánh gà kiểu Thái",
    price: "50000",
    image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png",
    link: "/WebProgramming/MartianChickenShop/html/chickendishes/sproduct1.html",
  },
  2: {
    name: "Cánh gà giòn",
    price: "35000",
    image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaGion.png",
    link: "/WebProgramming/MartianChickenShop/html/chickendishes/sproduct2.html",
  },
  3: {
    name: "Cánh gà phô mai",
    price: "45000",
    image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaPhoMai.png",
    link: "/WebProgramming/MartianChickenShop/html/chickendishes/sproduct3.html",
  },
  1000: {
    name: "Combo 4",
    price: "250000",
    image: "/WebProgramming/MartianChickenShop/images/ComboGa/4dui.png",
    link: "/WebProgramming/MartianChickenShop/html/chickendishes/sproduct1000.html",
  },
};


function addToCart() {
  if (!isLoggedIn()) {
    alert("Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng.");
    window.location.href =
      "/WebProgramming/MartianChickenShop/html/Menu/login.html";
    return; // Dừng hàm nếu chưa đăng nhập
  }

  // Lấy ID từ tên file trong URL
  const url = window.location.href;
  const fileName = url.split("/").pop(); // Lấy phần cuối cùng của URL (ví dụ: sproduct1000.html)
  const productId = parseInt(fileName.replace("sproduct", "").replace(".html", "")); // Lấy ID (1000)

  // Kiểm tra giá trị productId
  console.log("Product ID: ", productId);

  if (!productId || !productList[productId]) {
    alert("Sản phẩm không tồn tại.");
    return;
  }

  let quantityToAdd = parseInt(document.getElementById("noi").value);

  let product = productList[productId]; // Lấy thông tin sản phẩm từ productList
  if (!product) {
    alert("Sản phẩm không tồn tại.");
    return;
  }

  // Kiểm tra số lượng có sẵn (nếu bạn thêm thuộc tính quantity vào productList)
  if (quantityToAdd > product.quantity) {
    alert(
      `Số lượng yêu cầu vượt quá số lượng có sẵn. Chỉ còn ${product.quantity} sản phẩm.`
    );
    return;
  }

  // Lấy giỏ hàng từ localStorage
  let cart = window.localStorage.getItem("cart");
  if (cart == null) {
    cart = {};
  } else {
    cart = JSON.parse(cart);
  }

  // Thêm sản phẩm vào giỏ hàng
  cart[productId] = (cart[productId] || 0) + quantityToAdd; // Cộng dồn nếu sản phẩm đã tồn tại trong giỏ
  window.localStorage.setItem("cart", JSON.stringify(cart));

  alert("Sản phẩm đã được thêm vào giỏ hàng");
}


// Hàm xóa sản phẩm khỏi giỏ hàng
function removeFromCart(productId) {
  let cart = window.localStorage.getItem("cart");
  if (cart == null) {
    cart = {};
  } else {
    cart = JSON.parse(cart);
  }
  delete cart[productId];
  window.localStorage.setItem("cart", JSON.stringify(cart));
  window.location.reload();
}

function contactUs(event) {
  event.preventDefault();
  let name = document.getElementById("name").value;
  let email = document.getElementById("email").value;
  let message = document.getElementById("message").value;

  alert(
    "Cảm ơn bạn đã liên hệ với chúng tôi. Chúng tôi sẽ liên lạc lại với bạn sớm."
  );
  window.location.href =
    "/WebProgramming/MartianChickenShop/html/Menu/index.html";
}
// Đảm bảo JavaScript chỉ chạy sau khi trang đã tải
document.addEventListener("DOMContentLoaded", function () {
  // Lấy tất cả các liên kết với class 'toggle-menu' (là "THỰC ĐƠN")
  var toggleMenus = document.querySelectorAll(".toggle-menu");

  // Lặp qua tất cả các liên kết và thêm sự kiện click
  toggleMenus.forEach(function (menu) {
    menu.addEventListener("click", function () {
      // Tìm <ul> kế tiếp sau <h4> (submenu)
      var submenu = menu.closest(".menu-section").querySelector(".submenu");

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
document.addEventListener("DOMContentLoaded", function () {
  // Lắng nghe sự kiện khi người dùng nhập từ khóa tìm kiếm
  document.getElementById("searchInput").addEventListener("input", function () {
    let query = this.value.trim().toLowerCase();

    if (query) {
      searchProducts(query);
    } else {
      clearSearchResults(); // Nếu ô tìm kiếm trống, ẩn kết quả
    }
  });
});

function searchProducts(query) {
  const results = Object.values(productList).filter((product) =>
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
  let resultContainer = document.getElementById("searchResults");
  resultContainer.innerHTML = ""; // Xóa các kết quả cũ

  results.forEach((product) => {
    let productElement = document.createElement("div");
    productElement.classList.add("search-result");

    // Thêm nội dung sản phẩm
    productElement.innerHTML = `
            <img src="${product.image}" alt="${product.name}">
            <p><strong>${product.name}</strong> - ${product.price} VND</p>
        `;

    // Thêm sự kiện click để chuyển hướng đến link sản phẩm
    productElement.addEventListener("click", () => {
      window.location.href = product.link; // Chuyển đến trang chi tiết
    });

    // Thêm phần tử vào container
    resultContainer.appendChild(productElement);
  });

  // Hiển thị phần tử kết quả tìm kiếm
  resultContainer.style.display = "block";
}



// Hàm xóa kết quả tìm kiếm
function clearSearchResults() {
  let resultContainer = document.getElementById("searchResults");
  resultContainer.innerHTML = ""; // Xóa tất cả kết quả
  resultContainer.style.display = "none"; // Ẩn kết quả
}
const users = [
  { username: "user1", password: "password1", role: "user" },
  { username: "user2", password: "password2", role: "user" },
  { username: "user3", password: "password3", role: "user" },
  { username: "user4", password: "password4", role: "user" },
  { username: "user5", password: "password5", role: "user" },
  { username: "admin@gmail.com", password: "admin123", role: "admin" },
];

const products = [
  {
    id: 1,
    name: "Cánh gà kiểu Thái",
    price: "50000",
    quantity: 10,
    image:
      "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaKieuThai.png",
  },
  {
    id: 2,
    name: "Cánh gà giòn",
    price: "35000",
    quantity: 15,
    image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaGion.png",
  },
  {
    id: 3,
    name: "Cánh gà phô mai",
    price: "45000",
    quantity: 8,
    image: "/WebProgramming/MartianChickenShop/images/CanhGa/CanhGaPhoMai.png",
  },
];

// Hàm lấy productId từ URL
function getProductIdFromUrl() {
  const url = window.location.href;
  const fileName = url.split("/").pop();
  const productId = parseInt(fileName.split("sproduct")[1].split(".html")[0]);
  return productId;
}

function displayAvailableQuantity() {
  const productId = getProductIdFromUrl();
  const product = products.find((p) => p.id === productId);

  if (product) {
    const quantityElement = document.getElementById("available-quantity");
    if (quantityElement) {
      quantityElement.textContent = `${product.quantity} sản phẩm có sẵn`;
    }
  }
}
document.addEventListener("DOMContentLoaded", displayAvailableQuantity);

let currentUser = null; // Biến lưu trữ người dùng hiện tại

// Hàm kiểm tra trạng thái đăng nhập
function isLoggedIn() {
  return localStorage.getItem("loggedInUser") !== null;
}

// Hàm đăng nhập
document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("loginForm");
  form.addEventListener("submit", function (event) {
    event.preventDefault(); // Ngăn chặn gửi form và reload trang

    // Lấy thông tin từ form
    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value;

    // Mảng người dùng (giả sử đây là mảng người dùng của bạn)
    const users = [
      { username: "user1", password: "password1", role: "user" },
      { username: "user2", password: "password2", role: "user" },
      { username: "user3", password: "password3", role: "user" },
      { username: "user4", password: "password4", role: "user" },
      { username: "user5", password: "password5", role: "user" },
      { username: "admin@gmail.com", password: "admin123", role: "admin" },
    ];

    // Kiểm tra tài khoản và mật khẩu
    const user = users.find(
      (user) => user.username === username && user.password === password
    );

    if (user) {
      // Nếu tìm thấy người dùng hợp lệ, lưu thông tin vào localStorage
      localStorage.setItem("loggedInUser", JSON.stringify(user));

      document.getElementById("loginSuccess").style.display = "block";
      document.getElementById(
        "loginSuccess"
      ).textContent = `Chào mừng, ${user.username}!`;

      // Chuyển hướng dựa trên vai trò người dùng
      setTimeout(function () {
        if (user.role === "admin") {
          window.location.href =
            "/WebProgramming/MartianChickenShop/html/Admin.html"; // Trang admin
        } else {
          window.location.href =
            "/WebProgramming/MartianChickenShop/html/index.html"; // Trang user
        }
      }, 2000);
    } else {
      alert("Tài khoản hoặc mật khẩu không chính xác. Vui lòng thử lại!");
    }
  });
});
function displayUserMenu() {
  const loggedInUser = JSON.parse(localStorage.getItem("loggedInUser"));

  const userMenu = document.getElementById("user-menu");
  const userSidebar = document.getElementById("user-sidebar");
  const loggedInMenu = document.getElementById("user-logged-in");
  const greeting = document.getElementById("greeting");
  const menuGreeting = document.getElementById("greeting-menu");
  const profileGreeting = document.getElementById("profile-greeting"); // Nếu có phần này trong profile
  const loginLink = document.querySelector(
    '#nav-icons a[href="/WebProgramming/MartianChickenShop/html/Menu/Login.html"]'
  );
  const logoutLink = document.querySelector('#user-menu a[onclick="logout()"]');
  const adminBtn = document.getElementById("admin-btn"); // Nút quản trị

  if (loggedInUser) {
    // Khi người dùng đã đăng nhập
    if (greeting) {
      greeting.textContent = `Xin chào, ${loggedInUser.username}`;
    }
    menuGreeting.textContent = `XIN CHÀO, ${loggedInUser.username.toUpperCase()}`;

    if (profileGreeting) {
      profileGreeting.textContent = `XIN CHÀO, ${loggedInUser.username.toUpperCase()}`;
    }

    userSidebar.style.display = "none"; // Ẩn phần đăng nhập / đăng ký
    loggedInMenu.style.display = "block"; // Hiển thị menu khi đăng nhập

    if (userMenu) {
      userMenu.style.display = "none";
    }

    if (loginLink) {
      loginLink.addEventListener("click", function (event) {
        event.preventDefault();
        userMenu.style.display =
          userMenu.style.display === "none" || userMenu.style.display === ""
            ? "block"
            : "none";
      });
    }

    if (logoutLink) {
      logoutLink.addEventListener("click", function () {
        logout();
      });
    }

    // Hiển thị nút quản trị nếu là admin
    if (adminBtn) {
      adminBtn.style.display = loggedInUser.role === "admin" ? "block" : "none";
    }
  } else {
    // Khi người dùng chưa đăng nhập
    if (profileGreeting) {
      profileGreeting.textContent = "XIN CHÀO !";
    }

    userSidebar.style.display = "block"; // Hiển thị phần Đăng nhập / Đăng ký
    loggedInMenu.style.display = "none"; // Ẩn menu người dùng đã đăng nhập
    if (userMenu) {
      userMenu.style.display = "none";
    }

    // Ẩn nút quản trị
    if (adminBtn) {
      adminBtn.style.display = "none";
    }
  }
}

// Gọi hàm để hiển thị menu người dùng khi trang đã tải xong
document.addEventListener("DOMContentLoaded", function () {
  displayUserMenu();
});


function logout() {
  window.localStorage.removeItem("username");
  window.localStorage.removeItem("isLoggedIn");
  window.localStorage.removeItem("loggedInUser");
  window.localStorage.removeItem("cart");
  window.location.href =
    "/WebProgramming/MartianChickenShop/html/Menu/Login.html";
}









