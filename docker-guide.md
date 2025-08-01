# 📦 Services Overview

This `docker-compose.yml` file sets up multiple services required for your application. Below is a breakdown of each service and its role:

### 1️⃣ PostgreSQL (`postgres`)

* **Image:** `postgres:16`
* **Purpose:** Primary relational database for storing application data.
* **Environment Variables:**

  * `POSTGRES_USER` → Username for database access.
  * `POSTGRES_PASSWORD` → Password for database access.
* **Volumes:**

  * `pgdata` → Persistent storage for database data.
  * `./postgres/init.sql` → Initial SQL script executed when the container is first created.
* **Ports:** `${DB_PORT}:${DB_PORT}` → Exposes PostgreSQL to host.
* **Network:** `finemeet-network` → Shared network for communication between services.
* **Healthcheck:** Uses `pg_isready` to ensure the database is running.

---

### 2️⃣ Redis (`redis`)

* **Image:** `redis:7`
* **Purpose:** In-memory key-value store used for caching, sessions, or pub/sub features.
* **Ports:** `${REDIS_PORT}:${REDIS_PORT}` → Exposes Redis to host.
* **Volumes:** `redisdata` → Persistent storage for Redis data.
* **Network:** `finemeet-network`.
* **Healthcheck:** Uses `redis-cli ping` to verify the service is available.

---

### 3️⃣ pgAdmin (`pgadmin`)

* **Image:** `dpage/pgadmin4`
* **Purpose:** Web-based GUI for managing PostgreSQL databases.
* **Environment Variables:**

  * `PGADMIN_DEFAULT_EMAIL` → Login email for pgAdmin.
  * `PGADMIN_DEFAULT_PASSWORD` → Login password.
* **Ports:** `5050:80` → Access pgAdmin at `http://localhost:5050`.
* **Depends On:** `postgres` → Ensures PostgreSQL starts first.
* **Network:** `finemeet-network`.

---

### 4️⃣ Kafka UI (`kafka-ui`)

* **Image:** `provectuslabs/kafka-ui:latest`
* **Purpose:** Web UI for exploring Kafka topics, consumers, and messages.
* **Ports:** `9090:8080` → Access Kafka UI at `http://localhost:9090`.
* **Environment:**

  * Connects to Kafka at `kafka:9092`.
  * No Zookeeper configuration needed (KRaft mode).
* **Depends On:** `kafka` → Requires Kafka to be running.
* **Network:** `kafka-network`.

---

### 5️⃣ Kafka CLI (`kafka-cli`)

* **Image:** `confluentinc/cp-kafka:7.6.0`
* **Purpose:** Command-line interface container for running Kafka commands.
* **Entry Point:** Keeps the container running indefinitely with `sleep infinity`.
* **Depends On:** `kafka`.
* **Network:** `kafka-network`.

---

### 🌐 Networks

* **finemeet-network:** Connects PostgreSQL, Redis, and pgAdmin.
* **kafka-network:** Connects Kafka-related services (Kafka, Kafka UI, Kafka CLI).

---

### 💾 Volumes

* `pgdata` → Stores PostgreSQL data persistently.
* `redisdata` → Stores Redis data persistently.
* `kafka-data` → Stores Kafka logs and data persistently.

---

# Spring Boot + PostgreSQL + Docker Setup

This guide provides step-by-step instructions for running a **Spring Boot** application with **PostgreSQL** using Docker and Docker Compose.

---

## 📌 2. Run the Application with Docker Compose

### 🏃 Start the Services

```sh
docker compose up --build -d
```

* `--build`: Ensures the application is rebuilt.
* `-d`: Runs the containers in detached mode (background).

### ✅ Verify Running Containers

```sh
docker ps
```

Expected output:

```
CONTAINER ID   IMAGE          STATUS          PORTS                    NAMES
12345abcde     my_springboot_app   Up 10 sec   0.0.0.0:8080->8080/tcp   my_springboot_app
67890fghij     postgres:15         Up 10 sec   0.0.0.0:5432->5432/tcp   my_postgres_db
```

### 🔍 View Application Logs

```sh
docker logs -f my_springboot_app
```

### 📜 Get Last N Lines of Logs

```sh
docker logs --tail=N my_springboot_app
```

### 📜 Get First N Lines of Logs

```sh
docker logs my_springboot_app | head -n N
```

Replace N with the number of lines you want to retrieve.

---

## 📌 3. Managing Docker Services

### 🔄 Restart the Application

```sh
docker compose restart
```

### 🛑 Stop the Application

```sh
docker compose down
```

### 🚀 Restart with a Fresh Database

To remove existing data:

```sh
docker compose down -v
```

---

## 📌 4. Running Individual Services

### 🏃 Start Only PostgreSQL

```sh
docker compose up -d postgres
```

### 🏃 Start Only Spring Boot App

```sh
docker compose up -d app
```

---

## 📌 5. Connecting to PostgreSQL in Docker

### Container Details

* **Container Name:** `my_postgres_db`
* **PostgreSQL Username:** `myuser`
* **Database Name:** `mydatabase`

### Access PostgreSQL Container

Run the following command to enter the PostgreSQL shell inside the Docker container:

```sh
docker exec -it my_postgres_db psql -U myuser -d mydatabase
```

## Common PostgreSQL Commands

* **List all databases**

```sql
\l
```

* **Connect to a specific database**

```sql
\c mydatabase
```

* **List all tables in the current database**

```sql
\dt
```

* **Describe a specific table**

```sql
\d tablename
```

* **Show all users**

```sql
SELECT usename FROM pg_user;
```

* **Create a new table**

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE
);
```

* **Insert data into a table**

```sql
INSERT INTO users (name, email) VALUES ('John Doe', 'john@example.com');
```

* **Retrieve data from a table**

```sql
SELECT * FROM users;
```

* **Update data in a table**

```sql
UPDATE users SET name = 'Jane Doe' WHERE id = 1;
```

* **Delete data from a table**

```sql
DELETE FROM users WHERE id = 1;
```

* **Drop a table**

```sql
DROP TABLE users;
```

* **Exit PostgreSQL shell**

```sql
\q
```

---

## 🖥️ Using pgAdmin (GUI for PostgreSQL)

pgAdmin is included in your Docker setup, allowing you to manage PostgreSQL databases visually without the command line.

### 1️⃣ Access pgAdmin

* Open your browser and go to:
  **[http://localhost:5050](http://localhost:5050)**
* Log in with:

  * **Email:** `admin@finemeet.com`
  * **Password:** `admin123`

---

### 2️⃣ Add a New Server in pgAdmin

1. Click **"Add New Server"**.
2. In the **General** tab:

   * Name: `PostgreSQL Local` (or any name you like)
3. In the **Connection** tab:

   * **Host name/address:** `postgres`
     *(this is the container name from `docker-compose.yml`)*
   * **Port:** `${DB_PORT}` (default: `5432`)
   * **Username:** `${DB_USERNAME}`
   * **Password:** `${DB_PASSWORD}`
   * Save password: ✅

Click **Save** and you’ll now be connected to the PostgreSQL database inside Docker.

---

### 3️⃣ What You Can Do in pgAdmin

* Browse and query tables
* Create, edit, or delete databases
* Export or import data
* Monitor server activity
* Manage roles and permissions


---

## 📌 6. Monitor Docker Containers

### 📊 View Container Stats

```sh
docker stats
```

### 🗑️ Remove Unused Docker Images & Containers

```sh
docker system prune -a
```

---

## 🎯 Summary

| Command                                                       | Description                      |
| ------------------------------------------------------------- | -------------------------------- |
| `docker compose up --build -d`                                | Start the app and database       |
| `docker compose down`                                         | Stop and remove the services     |
| `docker logs -f my_springboot_app`                            | View Spring Boot logs            |
| `docker exec -it my_postgres_db psql -U myuser -d mydatabase` | Connect to PostgreSQL            |
| `docker ps`                                                   | List running containers          |
| `docker stats`                                                | Monitor container performance    |
| `docker system prune -a`                                      | Clean up unused Docker resources |

---

Your **Spring Boot + PostgreSQL** application is now running in Docker! 🚀