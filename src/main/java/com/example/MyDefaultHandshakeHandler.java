package com.example;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.Map;

/**
 * A custom WebSocket handshake component that combines {@link DefaultHandshakeHandler}
 * with {@link HandshakeInterceptor} to manage both the handshake process and pre-handshake logic.
 *
 * <p>The {@code doHandshake()} method, inherited from {@link DefaultHandshakeHandler},
 * is responsible for upgrading the HTTP connection to a WebSocket connection.
 */
public class MyDefaultHandshakeHandler extends DefaultHandshakeHandler implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        return doHandshake(request, response, wsHandler, attributes);
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
