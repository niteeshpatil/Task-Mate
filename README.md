## Implementing authentication with credentials and CryptPassword
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
### Password Security with BCryptPasswordEncoder:
- Secure Hashing: BCryptPasswordEncoder encodes passwords with a high computational cost (strength 12), making brute-force attacks harder.
- Consistent Use: Applied in both registration and authentication for secure, compatible password handling.

## JWT and JWT Filter Implementation
###  JWT Service (JWTService):
- Token Generation: Generates JWTs using HmacSHA256 for robust signing, embedding user information (username) and setting expiration time for session validity.
- Claim Extraction: Provides utility methods to extract claims (like username) from tokens, crucial for user validation without requiring additional database calls.
- Token Validation: Validates the token's authenticity by checking its expiration and ensuring it matches the user details, reducing risks of unauthorized access.

### JWT Filter (JwtFilter):
- Token Extraction and Validation: Intercepts requests to extract JWT from the Authorization header, verifies the token's signature, and validates the token's authenticity with the jwtService.
- User Authentication Setup: Sets up UsernamePasswordAuthenticationToken in the SecurityContext if the token is valid, enabling Spring Security to handle the user’s authorization in downstream processing.
- Component Scope: Implemented as OncePerRequestFilter, ensuring the filter runs once per request, maintaining efficiency and preventing redundant checks.
