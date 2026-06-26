# 🚀 Java Multi-Threaded HTTP Server

A lightweight, multi-threaded HTTP/1.1 server built completely from scratch using **Core Java**. The project demonstrates networking, concurrency, caching, asynchronous logging, request parsing, and performance benchmarking without using frameworks such as Spring Boot.

---

## 📖 Project Overview

This project implements a basic HTTP server capable of serving static web pages while handling multiple client requests concurrently using a fixed-size thread pool.

To improve performance, the server includes a thread-safe LRU cache for frequently accessed files, asynchronous logging using a producer-consumer model, and a statistics endpoint to monitor server activity.

The project was built for learning low-level networking, concurrent programming, and operating system concepts.

---

# ✨ Features

- ✅ Multi-threaded HTTP Server using `ExecutorService`
- ✅ HTTP/1.1 Request Parsing
- ✅ Static File Serving
- ✅ MIME Type Detection
- ✅ Thread-safe LRU Cache
- ✅ Asynchronous Logging using `LinkedBlockingQueue`
- ✅ Lock-free Statistics Manager using `AtomicLong`
- ✅ `/stats` Monitoring Endpoint
- ✅ Path Traversal Protection
- ✅ Custom 404 Error Page
- ✅ Modular ClientHandler (Refactored Design)
- ✅ Performance Benchmarking using Apache JMeter

---

# 🏗 Architecture

```
                Client (Browser)

                       │
                       ▼

               Java HTTP Server
                 (ServerSocket)

                       │
                       ▼

              Fixed Thread Pool
            (ExecutorService)

                       │
                       ▼

               ClientHandler

      ┌──────────┼────────────┐
      │          │            │
      ▼          ▼            ▼

  LRU Cache   FileUtils   StatsManager
      │                        │
      │                        ▼
      │                   /stats Endpoint
      │
      ▼

 AsyncLogger
 (BlockingQueue)

```

---

# 📂 Project Structure

```
java-http-server
│
├── src
│   ├── HttpServer.java
│   ├── ClientHandler.java
│   ├── HttpRequest.java
│   ├── HttpResponse.java
│   ├── FileUtils.java
│   ├── MimeTypes.java
│   ├── LRUCache.java
│   ├── StatsManager.java
│   ├── AsyncLogger.java
│   ├── PathValidator.java
│   ├── Main.java
│   └── logs/
│       └── server.log
│
├── www
│   ├── index.html
│   ├── about.html
│   ├── style.css
│   └── images/
│
└── README.md
```

---

# ⚙ Technologies Used

- Java
- Java Socket Programming
- ExecutorService
- LinkedHashMap
- AtomicLong
- LinkedBlockingQueue
- Java NIO File API
- Apache JMeter

---

# 🚀 How to Run

Compile the project

```bash
javac *.java
```

Run the server

```bash
java Main
```

Open your browser

```
http://localhost:8080
```

Statistics Page

```
http://localhost:8080/stats
```

---

# 📊 Performance Benchmark

The server was benchmarked using **Apache JMeter**.

### Configuration

- 20 Concurrent Users
- 1000 HTTP Requests
- Ramp-up Time: 1 second

### Results

| Configuration | Throughput | Error Rate |
|--------------|-----------:|-----------:|
| Cache Disabled | 1097.7 Requests/sec | 0% |
| Cache Enabled | 1187.6 Requests/sec | 0% |

The benchmark shows that enabling the LRU cache improves throughput by reducing repeated disk access while maintaining zero request failures.

---

# 🔒 Security Features

- Path Traversal Protection
- Safe File Access
- Custom 404 Error Handling

---

# 📈 Statistics Endpoint

The `/stats` endpoint displays:

- Total Requests
- Cache Hits
- Cache Misses

---

# 📝 Logging

The server performs asynchronous logging using a dedicated background thread.

Every incoming request is written to `logs/server.log` without blocking worker threads.

---

# 💡 Key Concepts Demonstrated

- Socket Programming
- Multi-threading
- Thread Pool
- Concurrent Programming
- Producer-Consumer Pattern
- Thread-safe LRU Cache
- Atomic Operations
- HTTP Protocol
- File Handling
- Performance Benchmarking

---

# 🔮 Future Improvements

- Graceful Shutdown
- HTTP POST Support
- HTTP Keep-Alive
- Socket Timeout Handling
- HTTPS Support
- Directory Listing
- Compression (GZIP)

---

# 🎯 Learning Outcomes

Through this project, I gained hands-on experience with:

- Java Networking
- HTTP Protocol
- Concurrent Programming
- Thread Synchronization
- Cache Design
- Performance Optimization
- Asynchronous Processing
- Server Architecture

---

# 📜 License

This project is developed for educational and learning purposes.