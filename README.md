### Security Configuration and Custom Authentication:
- The SecurityConfig class customizes Spring Security using @EnableWebSecurity.
- CSRF protection is disabled with http.csrf(customizer -> customizer.disable()) to avoid CSRF checks in this configuration.
- Enforces authentication for all requests with http.authorizeHttpRequests(request -> request.anyRequest().authenticated()).
- Supports both form-based and HTTP basic authentication, allowing users to log in using different methods.
### Custom User Details Service Implementation (MyUserDetailsService):
- MyUserDetailsService implements UserDetailsService, responsible for loading user data by username.
- Retrieves user details from the database using UserRepo, with findByUsername to locate the user.
- Throws UsernameNotFoundException if the user is not found, ensuring only valid users are authenticated.

### Custom UserDetails Implementation (UserPrincipal Class):
- UserPrincipal implements the UserDetails interface, defining user-specific details for authentication.
- Includes methods like getUsername, getPassword, and getAuthorities, enabling Spring Security to retrieve necessary data for login.
- The getAuthorities method returns a basic USER authority, which can be expanded to support roles or privileges for advanced security.
### Data Access Layer Using JPA (UserRepo):
- UserRepo extends JpaRepository, handling database interactions for Users entities.
- The custom method findByUsername enables efficient fetching of user data by username, crucial for authentication workflows.
- This separation of data access logic promotes a clean, maintainable structure and can be expanded to support additional queries if needed.
