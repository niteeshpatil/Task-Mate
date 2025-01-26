package com.TaskMate.TaskMate.config;

import com.TaskMate.TaskMate.websocket.CustomHandshakeHandler;
import com.TaskMate.TaskMate.websocket.CustomWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final CustomHandshakeHandler customHandshakeHandler;

    public WebSocketConfig(CustomHandshakeHandler customHandshakeHandler) {
        this.customHandshakeHandler = customHandshakeHandler;
    }

    @Bean
    public CustomWebSocketHandler customWebSocketHandler() {
        return new CustomWebSocketHandler();
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(customWebSocketHandler(), "/ws")
                .setHandshakeHandler(customHandshakeHandler)
                .setAllowedOrigins("*");  // Allow all origins (or specify your domain)
    }

}
