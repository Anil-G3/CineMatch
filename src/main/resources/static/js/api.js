const BASE_URL = window.location.origin;

function getAuthHeader() {
    return { "Authorization": localStorage.getItem("token") };
}

function handleError(response) {
    if (response.status === 401 || response.status === 403) {
        localStorage.clear();
        window.location.href = "/login.html";
        return true;
    }
    if (!response.ok) {
        console.error("API error:", response.status);
        return true;
    }
    return false;
}