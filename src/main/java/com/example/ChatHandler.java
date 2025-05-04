package com.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Handles WebSocket chat messaging between authenticated users.
 */
@Slf4j
public class ChatHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Handles incoming WebSocket text messages from connected users.
     *
     * <p>Parses the JSON payload into a {@code ChatMessage}, extracts the sender,
     * recipient, and message content, then attempts to forward the message to the
     * intended recipient's session if they are connected and available.
     *
     * @param session the WebSocket session of the sender
     * @param message the incoming text message containing JSON-formatted chat data
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        String incoming = message.getPayload();
        log.info("Received: {}", incoming);

        ChatMessage chatMessage = mapper.readValue(incoming, ChatMessage.class);
        String sender = chatMessage.getFrom();
        String recipient = chatMessage.getTo();
        String content = chatMessage.getContent();

        WebSocketSession recipientSession = sessions.get(recipient);
        if (recipientSession != null && recipientSession.isOpen()) {
            ChatMessage response = new ChatMessage(sender, recipient, content);
            recipientSession.sendMessage(
                    new TextMessage(mapper.writeValueAsString(response))
            );
            log.info("Sent message from {} to {}", sender, recipient);
        } else {
            log.warn("User '{}' not connected.", recipient);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.values().remove(session); // Remove disconnected session
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Optional.ofNullable(session.getPrincipal())
                .ifPresentOrElse(
                        principal -> sessions.put(principal.getName(), session),
                        () -> log.warn("WebSocket session has no authenticated principal")
                );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ChatMessage {
        String from, to, content;
    }
}