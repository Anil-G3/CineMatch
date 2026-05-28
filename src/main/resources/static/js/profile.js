window.onload = function() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login.html";
        return;
    }
    document.getElementById("welcome-name").textContent = "Hi, " + localStorage.getItem("name");
    loadProfile();
}

async function loadProfile() {
    try {
        const response = await fetch(`${BASE_URL}/profile`, {
            headers: getAuthHeader()
        });

        if (handleError(response)) return;

        const profile = await response.json();

        const initials = profile.name.split(' ')
            .map(n => n[0])
            .join('')
            .toUpperCase();
        document.getElementById("profile-avatar").textContent = initials;

        document.getElementById("profile-name").textContent = profile.name;
        document.getElementById("profile-email").textContent = profile.email;
        document.getElementById("profile-member-since").textContent
            = "Member since " + new Date(profile.memberSince).toLocaleDateString('en-US', {
                year: 'numeric', month: 'long'
            });

        document.getElementById("stat-ratings").textContent = profile.totalRatings;
        document.getElementById("stat-watchlist").textContent = profile.watchlistCount;

        const genresList = document.getElementById("genres-list");
        genresList.innerHTML = '';

        if (profile.preferredGenres.length === 0) {
            genresList.innerHTML = '<p class="empty-msg">No favourite genres yet. Rate some movies!</p>';
        } else {
            profile.preferredGenres.forEach(genre => {
                const tag = document.createElement("span");
                tag.className = "genre-tag";
                tag.textContent = genre;
                genresList.appendChild(tag);
            });
        }

    } catch (error) {
        console.error("Error loading profile:", error);
    }
}

function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}