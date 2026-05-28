let selectedRating = 0;
let movieId = null;
let isInWatchlist = false;

window.onload = function () {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/login.html";
        return;
    }
    document.getElementById("welcome-name").textContent
        = "Hi, " + localStorage.getItem("name");
    const params = new URLSearchParams(window.location.search);
    movieId = params.get("id");
    if (!movieId) {
        window.location.href = "/home.html";
        return;
    }
    loadMovieDetail();
    loadReviews();
	checkIfAlreadyRated();
    checkWatchlist();
    document.getElementById("submit-rating-btn").onclick = submitRating;
}

async function loadMovieDetail() {
    try {
        const response = await fetch(`${BASE_URL}/movies/${movieId}`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const movie = await response.json();

        document.getElementById("poster-img").src = movie.posterUrl;
        document.getElementById("movie-title").textContent = movie.title;
        document.getElementById("movie-director").textContent = "Director: " + movie.director;
        document.getElementById("movie-year").textContent = "Year: " + movie.releaseYear;
        document.getElementById("movie-duration").textContent = "Duration: " + movie.durationMinutes + " min";
        document.getElementById("movie-description").textContent = movie.description;

        document.getElementById("movie-rating").innerHTML =
            '<svg width="28" height="28" viewBox="0 0 24 24" fill="#F5C842" xmlns="http://www.w3.org/2000/svg">' +
                '<path d="M12 2l3.09 6.26L22 9.27l-5 4.87 1.18 6.88L12 17.77l-6.18 3.25L7 14.14 2 9.27l6.91-1.01L12 2z"/>' +
            '</svg>' +
            '<span class="rating-value">' + (movie.averageRating && movie.totalRatings > 0 ? movie.averageRating.toFixed(1) : 'Not rated') + '</span>';

        const totalText = movie.totalRatings === 1 ? '1 rating' : movie.totalRatings + ' ratings';
        document.getElementById("movie-total-ratings").textContent = totalText;

        const genresDiv = document.getElementById("movie-genres");
        genresDiv.innerHTML = '';
        movie.genres.forEach(genre => {
            const tag = document.createElement("span");
            tag.className = "genre-tag";
            tag.textContent = genre;
            genresDiv.appendChild(tag);
        });
    } catch (error) {
        console.error("Error loading movie:", error);
    }
}

async function checkIfAlreadyRated() {
    try {
        const response = await fetch(`${BASE_URL}/ratings/my`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const ratings = await response.json();
        const alreadyRated = ratings.find(r => r.movieId == movieId);
        if (alreadyRated) {
            document.getElementById("rating-section").innerHTML =
			`<h3>Rate this Movie</h3>
			<span style="background:#1e2a3a; padding: 4px 14px; border-radius: 20px; font-size: 0.85rem; font-weight:600; letter-spacing:0.5px;">
			    <span style="color:rgba(255,255,255,0.7);">You rated this movie</span>
			    <span style="color:#f59e0b;"> ${'★'.repeat(alreadyRated.score)}${'☆'.repeat(5 - alreadyRated.score)} &nbsp;${alreadyRated.score}/5</span>
			</span>`;
        }
    } catch (error) {
        console.error("Error checking rating:", error);
    }
}

async function checkWatchlist() {
    try {
        const response = await fetch(`${BASE_URL}/watchlist/my`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const watchlist = await response.json();
        isInWatchlist = watchlist.some(item => item.movieId == movieId);
        updateWatchlistBtn();
    } catch (error) {
        console.error("Error checking watchlist:", error);
    }
}

async function toggleWatchlist() {
    try {
        const method = isInWatchlist ? "DELETE" : "POST";
        const url = isInWatchlist ? `${BASE_URL}/watchlist/${movieId}` : `${BASE_URL}/watchlist`;
        const options = {
            method,
            headers: { ...getAuthHeader(), "Content-Type": "application/json" }
        };
        if (!isInWatchlist) options.body = JSON.stringify({ movieId: parseInt(movieId) });
        const response = await fetch(url, options);
        if (handleError(response)) return;
        if (response.ok) {
            isInWatchlist = !isInWatchlist;
            updateWatchlistBtn();
        }
    } catch (error) {
        console.error("Error toggling watchlist:", error);
    }
}

function updateWatchlistBtn() {
    const btn = document.getElementById("watchlist-btn");
    btn.textContent = isInWatchlist ? "✓ In Watchlist" : "+ Add to Watchlist";
    btn.onclick = toggleWatchlist;
}

function setRating(value) {
    selectedRating = value;
    document.getElementById("rating-label").textContent = "You selected: " + value + " star(s)";
    document.querySelectorAll('.star').forEach((star, index) => {
        star.classList.toggle('selected', index < value);
    });
}

async function submitRating() {
    if (selectedRating === 0) {
        showRatingMsg("Please select a star rating.", "error");
        return;
    }
    const review = document.getElementById("review-input").value.trim();
    const btn = document.getElementById("submit-rating-btn");
    btn.disabled = true;
    btn.textContent = "Posting...";
    try {
        const response = await fetch(`${BASE_URL}/ratings`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...getAuthHeader()
            },
            body: JSON.stringify({
                movieId: parseInt(movieId),
                score: selectedRating,
                review: review
            })
        });
        if (handleError(response)) return;
        const data = await response.json();
        if (response.ok) {
            document.getElementById("rating-section").innerHTML =
                `<h3>Rate this Movie</h3><p style="color: rgba(255,255,255,0.6); font-size: 0.95rem;">✅ ${data.message}</p>`;
            loadMovieDetail();
            loadReviews();
        } else {
            if (data.message && data.message.toLowerCase().includes("already rated")) {
                document.getElementById("rating-section").innerHTML =
                    `<h3>Rate this Movie</h3><p style="color: rgba(255,255,255,0.6); font-size: 0.95rem;">You have already rated this movie.</p>`;
            } else {
                showRatingMsg(data.message || "Something went wrong.", "error");
                btn.disabled = false;
                btn.textContent = "Post";
            }
        }
    } catch (error) {
        console.error("Error submitting rating:", error);
        showRatingMsg("Something went wrong. Please try again.", "error");
        btn.disabled = false;
        btn.textContent = "Post";
    }
}

async function loadReviews() {
    try {
        const response = await fetch(`${BASE_URL}/ratings/movie/${movieId}`, {
            headers: getAuthHeader()
        });
        if (handleError(response)) return;
        const reviews = await response.json();
        const list = document.getElementById("reviews-list");
        list.innerHTML = '';
        if (reviews.length === 0) {
            list.innerHTML = '<p style="color: rgba(255,255,255,0.4); font-size: 0.9rem;">No reviews yet. Be the first to rate!</p>';
            return;
        }
        reviews.forEach(review => {
            const item = document.createElement("div");
            item.className = "review-item";
            const filledStars = '★'.repeat(review.score);
            const emptyStars = '☆'.repeat(5 - review.score);
            const date = new Date(review.createdAt).toLocaleDateString();
            item.innerHTML = `
                <p>
                    <strong style="color: #fff;">${review.username}</strong>
                    <span style="color: #f59e0b; margin: 0 0.5rem;">${filledStars}${emptyStars}</span>
                    <span style="color: rgba(255,255,255,0.35); font-size: 0.8rem;">${date}</span>
                </p>
                ${review.review ? `<p style="color: rgba(255,255,255,0.6);">${review.review}</p>` : ''}
            `;
            list.appendChild(item);
        });
    } catch (error) {
        console.error("Error loading reviews:", error);
    }
}

function showRatingMsg(msg, type) {
    const el = document.getElementById("rating-msg");
    el.textContent = msg;
    el.style.color = type === "error" ? "var(--marvel-red)" : "#22c55e";
    setTimeout(() => { el.textContent = ''; }, 4000);
}

function logout() {
    localStorage.clear();
    window.location.href = "/login.html";
}