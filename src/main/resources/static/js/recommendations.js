window.onload = function() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login.html";
        return;
    }
    document.getElementById("welcome-name").textContent
        = "Hi, " + localStorage.getItem("name");
    loadRecommendations();
}

async function loadRecommendations() {
    try {
        const response = await fetch(`${BASE_URL}/recommendations`, {
            headers: getAuthHeader()
        });

        if (handleError(response)) return;

        const movies = await response.json();

        const subtitle = document.getElementById("recommendations-subtitle");

        if (movies.length === 0) {
            subtitle.textContent = "Rate more movies to get recommendations!";
            document.getElementById("no-results").style.display = "block";
            return;
        }

        subtitle.textContent = "Based on your ratings and preferences";
        displayMovies(movies);

    } catch (error) {
        console.error("Error loading recommendations:", error);
    }
}

function displayMovies(movies) {
    const grid = document.getElementById("movies-grid");
    const noResults = document.getElementById("no-results");

    grid.innerHTML = '';
    noResults.style.display = "none";

    movies.forEach(movie => {
        const card = document.createElement("div");
        card.className = "movie-card";
        card.onclick = () => window.location.href = `/movie-detail.html?id=${movie.id}`;

        card.innerHTML = `
            <img src="${movie.posterUrl}" alt="${movie.title}" loading="lazy"
                 onerror="this.onerror=null; this.src='https://via.placeholder.com/300x450?text=No+Poster'">
            <div class="card-info">
                <h3>${movie.title}</h3>
                <p>${movie.releaseYear}</p>
                <p>${movie.genres.join(', ')}</p>
                <p>⭐ ${movie.averageRating && movie.totalRatings > 0 ? movie.averageRating.toFixed(1) + ' (' + movie.totalRatings + ' ratings)' : 'Not rated yet'}</p>
            </div>
        `;

        grid.appendChild(card);
    });
}

function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}