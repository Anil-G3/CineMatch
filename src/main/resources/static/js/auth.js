
// check if already logged in
window.onload = function() {
    const token = localStorage.getItem("token");
    if (token) {
        window.location.href = "/home.html";
    }
}

function showError(msg) {
    const el = document.getElementById("error-msg");
    el.style.display = "block";
    el.textContent = msg;
}

function showSuccess(msg) {
    const el = document.getElementById("success-msg");
    if (el) {
        el.style.display = "block";
        el.textContent = msg;
    }
}

function togglePassword(el) {
    const input = el.closest('.input-wrapper').querySelector('input');
    input.type = input.type === 'password' ? 'text' : 'password';
}

async function register() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!name || !email || !password) {
        showError("All fields are required");
        return;
    }

    const btn = document.querySelector('.btn-primary');
    btn.textContent = 'Creating account...';
    btn.disabled = true;
    try {
        const response = await fetch(`${BASE_URL}/auth/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password })
        });

        const data = await response.json();

        if (response.ok) {
            showSuccess(data.message);
            setTimeout(() => {
                window.location.href = "/login.html";
            }, 2000);
        } else {
            showError(data.message)
            btn.textContent = 'Create Account';
            btn.disabled = false;
            setTimeout(() => {
                document.getElementById("error-msg").textContent = ''
            }, 5000);
        }

    } catch (error) {
        showError("Something went wrong. Please try again.");
        btn.textContent = 'Create Account';
        btn.disabled = false;
        setTimeout(() => {
            document.getElementById("error-msg").textContent = ''
        }, 5000);
    }
}


async function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    if (!email || !password) {
        showError("All fields are required");
        return;
    }

    const btn = document.querySelector('.btn-primary');
    btn.textContent = 'Signing in...';
    btn.disabled = true;
    try {
        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json();

        if (response.ok) {
            localStorage.setItem("token", data.type + " " + data.token);
            localStorage.setItem("name", data.name);
            window.location.href = "/home.html";
        } else {
            showError(data.message);
            btn.textContent = 'Sign In';
            btn.disabled = false;
            setTimeout(() => {
                document.getElementById("error-msg").textContent = ''
            }, 5000);
        }

    } catch (error) {
        showError("Something went wrong. Please try again.");
        btn.textContent = 'Sign In';
        btn.disabled = false;
        setTimeout(() => {
            document.getElementById("error-msg").textContent = ''
        }, 5000);
    }
}


const passwordInput = document.getElementById("password");

if (passwordInput) {
    passwordInput.addEventListener("keydown", function(e) {
        if (e.key === "Enter") {
            typeof login === "function" ? login() : register();
        }
    });
}