# 🧩 FineMeet Platform

FineMeet is a scalable, microservices-based meeting and collaboration platform that empowers teams and organizations to plan and manage meetings efficiently with built-in support for agenda, MOM (Minutes of Meeting), action points, user roles, notes, and Google Calendar sync.

---

## 🚀 Tech Stack

- **Backend**: Java, Spring Boot (Microservices)
- **Database**: PostgreSQL (per service DB)
- **Cache**: Redis (Spring Cache)
- **Auth**: JWT + Google OAuth 2.0 (coming soon)
- **Email**: SMTP integration for meeting invites
- **Docs**: OpenAPI / Swagger
- **Containerization**: Docker, Docker Compose
- **CI/CD**: GitHub Actions (optional)

---

## 🏗️ Architecture

- **user-service**: Handles user profile, roles, preferences
- **org-service**: Manages organizations and their admins
- **team-service**: Handles team creation, members, managers
- **meeting-service**: Core of meeting logic (agenda, notes, MOM, action points)
- *(future)* **auth-service**: JWT issuance, login, refresh tokens
- *(future)* **notification-service**: Email & Slack notifications

Each service uses its own isolated PostgreSQL database but can share Redis for caching.

---

## 🧰 Features

- User registration & login (JWT / Google OAuth)
- Profile with photo, email, username
- Organization creation, role-based admin & supervisors
- Team creation, manager-based ownership
- Meeting creation with:
  - Agenda
  - Attendees
  - Action Points (public per attendee)
  - Private/Public Notes
  - MOM (Minutes of Meeting)
  - Follow-up meetings
- Internal/External member logic
- Google Calendar integration (via Organizer’s account)
- Email invites with meeting agenda
- Redis-backed user summary caching

---

## 📦 Getting Started

### 1. Clone the Repo

```bash
git clone https://github.com/your-username/finemeet-platform.git
cd finemeet-platform
```

### 2. Start All Services via Docker Compose

```bash
docker-compose up --build
```

> PostgreSQL, Redis, and pgAdmin will spin up for development.

---

## 🌐 API Documentation

Each service is Swagger-enabled. Visit the Swagger UI:

* `user-service`: [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html)
* `org-service`: [http://localhost:8082/swagger-ui.html](http://localhost:8082/swagger-ui.html)
* `team-service`: [http://localhost:8083/swagger-ui.html](http://localhost:8083/swagger-ui.html)
* `meeting-service`: [http://localhost:8084/swagger-ui.html](http://localhost:8084/swagger-ui.html)

---

## 🛠️ Environment Variables

Each service uses `application.yaml` with support for ENV overrides via `.env` files.

> Example `.env` (for `user-service`):

```env
DB_HOST=postgres
DB_PORT=5432
DB_NAME=user_service_db
DB_USERNAME=finemeet
DB_PASSWORD=finemeet123
REDIS_HOST=redis
REDIS_PORT=6379
SPRING_PROFILES_ACTIVE=dev
```

---

## 🐘 pgAdmin Access

* **URL**: [http://localhost:5050](http://localhost:5050)
* **Email**: `admin@finemeet.com`
* **Password**: `admin123`
* **Host**: `postgres`
* **Port**: `5432`

---

## 🧪 Health Check Endpoints

Each service has Spring Boot Actuator enabled:

```http
GET /actuator/health
GET /actuator/info
```

---

## 📜 License

This project is licensed under the [MIT License](./LICENSE).

---

## 👨‍💻 Author

**Ajay Kathwate**  
**[LinkedIn](https://www.linkedin.com/in/ajaykathwate) | [GitHub](https://github.com/ajaykathwate)**


---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!
Feel free to open a pull request or raise an issue.