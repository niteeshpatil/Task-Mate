package com.TaskMate.TaskMate.controller;
import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.websocket.CustomWebSocketHandler;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final CustomWebSocketHandler webSocketHandler;

    //ws://localhost:8080/ws?Authorization=Bearer <your_token>
    public WebSocketController(CustomWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void sendReminder(String message) {
        webSocketHandler.broadcastMessage(message);
    }
}
