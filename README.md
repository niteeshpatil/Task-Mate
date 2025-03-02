# 🚀 Task-Mate 
<div align="center">
  
<img width="70%" width="1813" alt="METH" src="https://github.com/user-attachments/assets/6e21ebbd-488d-4524-b84f-1dcaca5a7a47">

</div>

--- 

## 🔹 Non-Blocking and Event-Driven

<div align="center">
  
<img width="70%" width="1813" alt="BlockDigram-nonBlockingEventDriven" src="https://github.com/user-attachments/assets/dfccaeba-3343-47df-90c8-3b3a8d081dfc">

</div>

### 🔸 Reactive Streams with Flux
* Uses `Flux<Task>` to stream real-time updates without blocking threads.
* Multiple subscribers receive updates via Server-Sent Events (SSE).

### 🔸 Event-Driven Architecture
* Task updates are pushed using `taskUpdateSink.tryEmitNext(updatedTask);`.
* No need for polling, ensuring efficient real-time data flow.

### 🔸 Thread Management
* Uses Netty-based Event Loop for handling multiple concurrent connections.
* Unlike traditional blocking APIs, it avoids creating separate threads per request.

### 🔸 Database Operations
* Asynchronous `Mono`/`Flux` repository methods ensure non-blocking I/O.

### 🔸 Server-Sent Events (SSE)
* Configured via `MediaType.TEXT_EVENT_STREAM` for real-time updates.
* Clients maintain an open connection without constant polling.

### 🔸 Multi-Client Support
* Multiple subscribers can listen to live task updates simultaneously.
* Efficiently broadcasts updates without redundant DB queries.

## 🔹 Non-Blocking and Multi-Threaded Execution  
- **Threading:** Uses `ThreadPoolTaskExecutor` for parallel execution, ensuring efficient resource utilization.  
- **Non-Blocking Communication:** WebSocket enables real-time messaging without blocking operations.  
- **Security:** Implements JWT-based authentication to maintain secure client connections.  
- **Scalability:** Efficient scheduling and task execution prevent performance bottlenecks.  


### 🔸 Asynchronous Task Execution with `ThreadPoolTaskExecutor`
- **Handles Reminder Scheduling in a Multi-Threaded Manner.**  
- Configured with:  
  - **Core Pool Size:** `5`  
  - **Max Pool Size:** `10`  
  - **Queue Capacity:** `25`  
  - **Thread Prefix:** `ReminderExecutor-`  
- **Prevents Thread Exhaustion** while ensuring efficient resource utilization.  


### 🔸 Non-Blocking WebSocket Communication  
- **Configured via `WebSocketConfig` to enable real-time messaging.**  
- Uses `CustomWebSocketHandler` to:  
  - Handle client connections **asynchronously**.  
  - Manage active sessions using **`ConcurrentHashMap`**.  
  - Efficiently broadcast messages to multiple clients.  


### 🔸 Secure WebSocket Authentication  
- Implements **token-based authentication** in `CustomHandshakeHandler`.  
- **Token Extraction:** Retrieves JWT from WebSocket URL query parameters.  
- **Validation:** Ensures token integrity using `JWTService` before establishing a connection.  



### 🔸 Multi-Threaded Reminder Processing  
- **Reminders are fetched and scheduled asynchronously.**  
- **Uses incremental delays** to prevent thread congestion.  
- **Periodic Execution:**  
  ```java
  @Scheduled(fixedRate = 60000)

---

## 🔹 Blocking (Single Thread Waits for Completion)
- **Transactional Consistency:**  
  - `@Transactional` ensures atomicity, maintaining data integrity when creating entities like Task & Assignees or Reminder & Users.  
- **Synchronous Repository Calls:**  
  - Blocks the thread until database operations complete, ensuring data consistency before proceeding.  
- **Performance Trade-offs:**  
  - While ensuring data integrity, blocking database interactions can cause performance bottlenecks under high load.  


## 🔹 Implementing Authentication with Credentials and BCryptPasswordEncoder

### 🔸 Security Configuration and Custom Authentication
- **Custom Security Setup:**  
  - Configured in `SecurityConfig` using `@EnableWebSecurity`.  
  - CSRF protection disabled: `http.csrf(customizer -> customizer.disable())` to simplify authentication in this setup.  
  - Enforces authentication for all requests:  
    ```java
    http.authorizeHttpRequests(request -> request.anyRequest().authenticated());
    ```
- **Authentication Support:**  
  - Supports both **form-based** and **HTTP basic authentication**, providing multiple login options.  

### 🔸 Custom User Details Service (`MyUserDetailsService`)
- Implements `UserDetailsService`, responsible for loading user data by username.  
- Retrieves user details using `UserRepo.findByUsername()`.  
- Ensures only valid users are authenticated by throwing `UsernameNotFoundException` if no user is found.  

### 🔸 Custom UserDetails Implementation (`UserPrincipal`)
- Implements `UserDetails`, defining authentication-specific user properties:  
  - `getUsername()`, `getPassword()`, and `getAuthorities()`.  
  - **Role-based security support:** `getAuthorities()` returns a basic `USER` authority (can be expanded for role-based access control).  

### 🔸 Data Access Layer (`UserRepo`)
- Extends `JpaRepository`, handling user authentication-related database queries.  
- `findByUsername()` enables efficient lookup of users, ensuring quick authentication.  

### 🔸 Password Security with `BCryptPasswordEncoder`
- **Secure Hashing:** Uses a high computational cost (strength 12) for robust password protection.  
- **Consistency:** Applied uniformly across **registration** and **authentication** to maintain secure password handling.  

---

## 🔹 JWT Authentication & Security Filter
<div align="center">
  
<img width="70%" width="1813" alt="METH" src="https://github.com/user-attachments/assets/fcc4103a-acb1-4543-ab75-92d739d381d5">

</div>

### 🔸 JWT Service (`JWTService`)
- **Token Generation:**  
  - Uses `HmacSHA256` for strong signing.  
  - Embeds user information (`username`) and expiration time for session management.  
- **Claim Extraction:**  
  - Provides methods to extract token claims (e.g., `username`) without extra database calls.  
- **Token Validation:**  
  - Checks expiration and ensures authenticity before granting access.  

### 🔸 JWT Filter (`JwtFilter`)
- **Token Handling:**  
  - Extracts JWT from the `Authorization` header.  
  - Validates token integrity using `jwtService`.  
- **User Authentication Setup:**  
  - Creates `UsernamePasswordAuthenticationToken` in `SecurityContext` if the token is valid, enabling Spring Security to handle authorization seamlessly.  
- **Efficient Execution:**  
  - Implemented as `OncePerRequestFilter` to ensure each request is processed only once, avoiding redundant checks.  



