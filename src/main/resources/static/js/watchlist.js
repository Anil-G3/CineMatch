window.onload = function() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login.html";
        return;
    }
    document.getElementById("welcome-name").textContent
        = "Hi, " + localStorage.getItem("name");
    loadWatchlist();
}

async function loadWatchlist() {
    try {
        const response = await fetch(`${BASE_URL}/watchlist/my`, {
            headers: getAuthHeader()
        });

        if (handleError(response)) return;

        const watchlist = await response.json();
        const grid = document.getElementById("watchlist-grid");
        const noResults = document.getElementById("no-results");
        const count = document.getElementById("watchlist-count");

        grid.innerHTML = '';

        if (watchlist.length === 0) {
            noResults.style.display = "block";
            count.textContent = "0 movies";
            return;
        }

        noResults.style.display = "none";
        count.textContent = watchlist.length + (watchlist.length === 1 ? " movie" : " movies");

        watchlist.forEach(item => {
            const card = document.createElement("div");
            card.className = "watchlist-card";

			card.innerHTML = `
			    <img src="${item.posterUrl}" alt="${item.title}" loading="lazy"
			         onerror="this.onerror=null; this.src='https://via.placeholder.com/300x450?text=No+Poster'"
			         onclick="window.location.href='/movie-detail.html?id=${item.movieId}'">
			    <div class="watchlist-card-info">
			        <h3 onclick="window.location.href='/movie-detail.html?id=${item.movieId}'">${item.title}</h3>
			        <button class="remove-btn" onclick="removeFromWatchlist(${item.movieId})">Remove</button>
			    </div>
			`;

            grid.appendChild(card);
        });

    } catch (error) {
        console.error("Error loading watchlist:", error);
    }
}

async function removeFromWatchlist(movieId) {
    try {
        const response = await fetch(`${BASE_URL}/watchlist/${movieId}`, {
            method: "DELETE",
            headers: getAuthHeader()
        });

        if (handleError(response)) return;

        if (response.ok) {
            loadWatchlist();
        }

    } catch (error) {
        console.error("Error removing from watchlist:", error);
    }
}

function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}