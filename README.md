# 🗨️ Chat App

A simple WebSocket-based chat application built with **Spring Boot** and **Spring WebSocket**, demonstrating real-time communication between authenticated users.

This project showcases:

- WebSocket integration in a Spring Boot app
- Basic authentication using Spring Security
- Direct messaging between users
- Postman-based WebSocket testing with headers

## WebSocket Configuration

This configuration class (`WebSocketConfiguration`) does the following:

### Features

- Registers the `ChatHandler` at the `/chatHandler` endpoint & adds a custom handshake interceptor (`MyDefaultHandshakeHandler`)
```java
registry.addHandler(myHandler(), "/chatHandler")
        .addInterceptors(new MyDefaultHandshakeHandler());
```
- Defines a WebSocket container bean to configure server-level settings like message buffer size.
```java
ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
container.setMaxTextMessageBufferSize(8192);
```

## WebSocket Chat Testing in Postman

This setup allows testing authenticated WebSocket communication between in-memory users (`SecurityConfig`) using Postman.

### 🧪 Creating WebSocket Requests

1. Open Postman and click **New → WebSocket Request**.
2. Name each request for clarity:
    - `ws-user-alice`
    - `ws-user-bob`
3. Use the following WebSocket URL for both:

### 🔐 Adding Authorization Header (Basic Auth)

In the **Headers** tab of each WebSocket request, add the `Authorization` header with the Base64-encoded credentials.

---

#### ✅ For Alice

| Header Key     | Value                              |
|----------------|------------------------------------|
| Authorization  | `Basic YWxpY2U6cGFzc3dvcmQ=`        |

- Decodes to: `alice:password`

---

#### ✅ For Bob

| Header Key     | Value                              |
|----------------|------------------------------------|
| Authorization  | `Basic Ym9iOnBhc3N3b3Jk`            |

- Decodes to: `bob:password`

---

### 📤 Sending Messages
After connecting, type a JSON message in the **Message** tab:
```json
{
"from": "alice",
"to": "bob",
"content": "Hello Bob!"
}
