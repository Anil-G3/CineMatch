# 🎬 CineMatch — Movie Recommender System

> A backend-heavy movie recommendation system built with Spring Boot.  
> Handles auth, ratings, watchlist, recommendations, and email notifications — all in one clean REST API.

![Status](https://img.shields.io/badge/Status-Live-brightgreen?style=flat)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-005C84?style=flat&logo=mysql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens&logoColor=white)

---

## 🔗 Links

| | |
|---|---|
| 🖥️ Live App | https://cinematch-29kr.onrender.com |
| 💻 GitHub | [CineMatch](https://github.com/Anil-G3/CineMatch) |

---

## 🧩 What This Does

CineMatch is a full-stack movie recommender system. Users rate movies, build a watchlist, and get personalized recommendations — all powered by a Spring Boot backend with stateless JWT auth.

- **JWT Authentication** — stateless auth via HTTP Cookie, no Bearer token
- **Recommendation Engine** — content-based recommender that analyses user rating patterns to suggest relevant movies
- **Ratings & Watchlist** — users can rate movies and manage a personal watchlist with real-time updates
- **Email Notifications** — triggered automatically on key user activity events
- **Normalised Schema** — complex MySQL relationships across Movies, Genres, Users, Ratings, and Watchlist

---

## 🔧 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| Framework | Spring Boot, Spring MVC, Spring Security |
| Auth | JWT, HTTP Cookie |
| ORM | Hibernate |
| Database | MySQL |
| Notifications | JavaMail (Email) |
| Frontend | HTML, CSS, JavaScript (served as static files via Spring Boot) |
| Tools | Maven, Postman, Git |

---

## 🔐 Auth Flow

1. User registers or logs in → server issues a signed JWT token
2. Token is stored in **localStorage** on the client side
3. Frontend attaches the token in the `Authorization: Bearer <token>` header on every request
4. Spring Security filter validates the token on every protected route

```
POST /api/auth/register   → Register a new user
POST /api/auth/login      → Returns JWT token
POST /api/auth/logout     → Clears the token
```

---

## 📦 API Overview

| Module | What it covers |
|---|---|
| Auth | Register, Login, Logout |
| Movies | Browse, Search, Movie details |
| Ratings | Rate a movie, Update rating, View ratings |
| Watchlist | Add, Remove, View personal watchlist |
| Recommendations | Fetch personalized movie suggestions |
| Profile | View and update user profile |

---

## 🚀 Run Locally

**Prerequisites:** Java 17+, Maven, MySQL

**1. Clone the repo**
```bash
git clone https://github.com/Anil-G3/CineMatch.git
cd CineMatch
```

**2. Configure the database**

Create a MySQL database and update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_name
spring.datasource.username=your_username
spring.datasource.password=your_password

app.jwt.secret=your_jwt_secret_key
app.jwt.expiration=86400000

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your_email@gmail.com
spring.mail.password=your_app_password
```

**3. Build & run**
```bash
mvn clean install
mvn spring-boot:run
```

Server starts at `http://localhost:8080`

---

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/cinematch/
│   │   ├── config/           # Security, CORS, and app configuration
│   │   ├── controller/       # REST controllers
│   │   ├── dto/              # Request/Response DTOs
│   │   ├── entity/           # JPA entity classes
│   │   ├── exception/        # Global exception handling
│   │   ├── repository/       # JPA repositories
│   │   ├── service/          # Service interfaces
│   │   ├── serviceimpl/      # Service implementations
│   │   ├── util/             # Utility classes
│   │   └── CineMatchApplication.java
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   ├── js/
│       │   ├── image/
│       │   ├── home.html
│       │   ├── login.html
│       │   ├── register.html
│       │   ├── movie-detail.html
│       │   ├── recommendations.html
│       │   ├── watchlist.html
│       │   └── profile.html
│       └── application.properties
```

---

## 👨‍💻 Author

**G Anil Kumar** — Java Developer  
[GitHub](https://github.com/Anil-G3) · [LinkedIn](https://linkedin.com/in/anil-g3) · [Portfolio](https://portfolio-anil-20.netlify.app)
