let userWatchlist = [];

window.onload = async function() {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login.html";
        return;
    }
    document.getElementById("welcome-name").textContent
        = "Hi, " + localStorage.getItem("name");
    await loadUserWatchlist();
    loadGenres();
    loadAllMovies();
}

async function loadUserWatchlist() {
    try {
        const res = await fetch(`${BASE_URL}/watchlist/my`, { headers: getAuthHeader() });
        if (res.ok) userWatchlist = await res.json();
    } catch (e) {}
}

async function loadGenres() {
    try {
        const response = await fetch(`${BASE_URL}/genres`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const genres = await response.json();
        const filter = document.getElementById("genre-filter");
        genres.forEach(genre => {
            const btn = document.createElement("button");
            btn.className = "genre-btn";
            btn.textContent = genre.name;
            btn.onclick = () => filterByGenre(genre.id, btn);
            filter.appendChild(btn);
        });
    } catch (error) {
        console.error("Error loading genres:", error);
    }
}

async function loadAllMovies() {
    try {
        const response = await fetch(`${BASE_URL}/movies`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const movies = await response.json();
        displayMovies(movies);
    } catch (error) {
        console.error("Error loading movies:", error);
    }
}

async function searchMovies() {
    const title = document.getElementById("search-input").value.trim();
    if (!title) {
        loadAllMovies();
        return;
    }
    try {
        const response = await fetch(`${BASE_URL}/movies/search?title=${title}`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const movies = await response.json();
        displayMovies(movies);
    } catch (error) {
        console.error("Error searching movies:", error);
    }
}

async function filterByGenre(genreId, btn) {
    document.querySelectorAll('.genre-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    if (genreId === null) {
        loadAllMovies();
        return;
    }
    try {
        const response = await fetch(`${BASE_URL}/movies/genre?genreId=${genreId}`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const movies = await response.json();
        displayMovies(movies);
    } catch (error) {
        console.error("Error filtering movies:", error);
    }
}

function displayMovies(movies) {
    const grid = document.getElementById("movies-grid");
    const noResults = document.getElementById("no-results");
    grid.innerHTML = '';
    if (movies.length === 0) {
        noResults.style.display = "block";
        return;
    }
    noResults.style.display = "none";
    movies.forEach(movie => {
        const card = document.createElement("div");
        card.className = "movie-card";
        card.onclick = () => window.location.href = `/movie-detail.html?id=${movie.id}`;

        const inWatchlist = userWatchlist.some(w => w.movieId == movie.id);
        const ratingText = movie.totalRatings === 1 ? '1 rating' : movie.totalRatings + ' ratings';

        card.innerHTML = `
            <img src="${movie.posterUrl}" alt="${movie.title}" loading="lazy"
                 onerror="this.onerror=null; this.src='https://via.placeholder.com/300x450?text=No+Poster'">
            <div class="card-info">
                <h3>${movie.title}</h3>
                <p>${movie.releaseYear}</p>
                <p>${movie.genres.join(', ')}</p>
                <p>⭐ ${movie.averageRating && movie.totalRatings > 0
                    ? movie.averageRating.toFixed(1) + ' (' + ratingText + ')'
                    : 'Not rated yet'}</p>
                ${inWatchlist ? `<div class="watchlist-strip">✓ Watchlisted</div>` : ''}
            </div>
        `;
        grid.appendChild(card);
    });
}

function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}