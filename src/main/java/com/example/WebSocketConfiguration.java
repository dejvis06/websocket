package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

/**
 * WebSocket configuration class that registers WebSocket handlers and handshake interceptors.
 *
 * <p>This configuration:
 * <ul>
 *   <li>Registers the {@code ChatHandler} at the {@code /chatHandler} endpoint</li>
 *   <li>Adds a custom handshake interceptor ({@code MyDefaultHandshakeHandler}) to manage authentication or session setup</li>
 *   <li>Defines a WebSocket container bean to configure server-level settings like message buffer size</li>
 * </ul>
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myHandler(), "/chatHandler")
                .addInterceptors(new MyDefaultHandshakeHandler());
    }

    @Bean
    public WebSocketHandler myHandler() {
        return new ChatHandler();
    }

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        return container;
    }
}
