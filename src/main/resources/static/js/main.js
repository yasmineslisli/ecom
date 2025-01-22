// DOM Elements
const authButtons = document.getElementById('authButtons');
const authModal = document.getElementById('authModal');
const closeModal = document.querySelector('.close');
const loginForm = document.getElementById('loginForm');
const registerForm = document.getElementById('registerForm');
const productGrid = document.getElementById('productGrid');
const categoryGrid = document.getElementById('categoryGrid');

const CONTEXT_PATH = '/ecommerce';
const API_URL = {
    products: `${CONTEXT_PATH}/api/product`,
    categories: `${CONTEXT_PATH}/api/category`,
    auth: {
        login: `${CONTEXT_PATH}/login`,
        register: `${CONTEXT_PATH}/register`
    }
};
// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    fetchCategories();
});

// Auth Modal
authButtons.querySelector('.login-btn').addEventListener('click', () => {
    showModal('login');
});

authButtons.querySelector('.register-btn').addEventListener('click', () => {
    showModal('register');
});

closeModal.addEventListener('click', hideModal);

// Form Submissions
loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(loginForm);
    try {
        const response = await fetch(API_URL.auth.login, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                email: formData.get('email'),
                password: formData.get('password')
            })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token);
            hideModal();
            updateAuthStatus();
        }
    } catch (error) {
        console.error('Login failed:', error);
    }
});

registerForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const formData = new FormData(registerForm);
    try {
        const response = await fetch(API_URL.auth.register, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                name: formData.get('name'),
                email: formData.get('email'),
                password: formData.get('password')
            })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem('token', data.token);
            hideModal();
            updateAuthStatus();
        }
    } catch (error) {
        console.error('Registration failed:', error);
    }
});

// Fetch Functions
async function fetchProducts() {
    try {
        const response = await fetch(API_URL.products);
        const products = await response.json();
        renderProducts(products);
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}

async function fetchCategories() {
    try {
        const response = await fetch(API_URL.categories);
        const categories = await response.json();
        renderCategories(categories);
    } catch (error) {
        console.error('Error fetching categories:', error);
    }
}

// Render Functions
function renderProducts(products) {
    productGrid.innerHTML = products.map(product => `
        <div class="product-card">
            <img src="/api/placeholder/300/200" alt="${product.name}">
            <h3>${product.name}</h3>
            <p>${product.description}</p>
            <div class="product-price">$${product.price}</div>
            <button onclick="addToCart(${product.id})" class="add-to-cart">Add to Cart</button>
        </div>
    `).join('');
}

function renderCategories(categories) {
    categoryGrid.innerHTML = categories.map(category => `
        <div class="category-card">
            <img src="/api/placeholder/300/200" alt="${category.name}">
            <h3>${category.name}</h3>
        </div>
    `).join('');
}