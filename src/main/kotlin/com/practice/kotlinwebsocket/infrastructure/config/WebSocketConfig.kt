package com.practice.kotlinwebsocket.infrastructure.config

import com.practice.kotlinwebsocket.infrastructure.websocket.UserSocketHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig(
    private val userSocketHandler: UserSocketHandler
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(userSocketHandler, "/ws")
            .setAllowedOriginPatterns("*")
            .withSockJS()
    }
}
