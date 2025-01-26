package com.TaskMate.TaskMate.websocket;

import com.TaskMate.TaskMate.service.JWTService;
import com.TaskMate.TaskMate.service.MyUserDetailsService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    private final JWTService jwtService;
    private final ApplicationContext context;

    @Autowired
    public CustomHandshakeHandler(JWTService jwtService, ApplicationContext context) {
        this.jwtService = jwtService;
        this.context = context;
    }

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // Extract the JWT token from the query parameter in the WebSocket URL
        String token = extractTokenFromRequest(request);
        if (StringUtils.isEmpty(token)) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }
        String username = jwtService.extractUserName(token);
        UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(username);
        if (token != null && jwtService.validateToken(token, userDetails)) {
            // Return an authenticated principal
            return new UsernamePasswordAuthenticationToken(username, null, null);
        }
        return null; // Reject invalid tokens
    }

    private String extractTokenFromRequest(ServerHttpRequest request) {
        // Extract the query parameters from the WebSocket URL
        String token = request.getURI().getQuery();
        if (token != null && token.startsWith("Authorization=Bearer ")) {
            // Remove the "Authorization=Bearer " prefix to get the token
            return token.replace("Authorization=Bearer ", "");
        }
        return null; // No token found
    }
}
