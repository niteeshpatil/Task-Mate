package com.TaskMate.TaskMate.controller;

import com.TaskMate.TaskMate.model.Reminder;
import com.TaskMate.TaskMate.websocket.CustomWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    private final CustomWebSocketHandler webSocketHandler;

    public WebSocketController(CustomWebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    public void sendReminder(Reminder reminder) {
        String message = "Reminder: " + reminder.getMessage() + " at " + reminder.getReminderTime();
        webSocketHandler.broadcastMessage(message);
    }

//    @MessageMapping("/hello")
//    @SendTo("/topic/greetings")
//    public String sendMessage(String message) {
//        return "Hello, " + message + "!";
//    }
}
