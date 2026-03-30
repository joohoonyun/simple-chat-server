package com.practice.kotlinwebsocket.config

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class UserSocketHandler: TextWebSocketHandler() {
    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val logger = KotlinLogging.logger {}

    override fun handleTextMessage(
        session: WebSocketSession,
        message: TextMessage
    ) {
        sessions.values.forEach {
            if (it.isOpen) it.sendMessage(message)
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info { "Session established >> " + session.id + " / " + session.remoteAddress}
        sessions[session.id] = session
    }


    override fun afterConnectionClosed(
        session: WebSocketSession,
        status: CloseStatus
    ) {
        logger.info { "Session closed >> " + session.id + " / " + session.remoteAddress}
        sessions.remove(session.id)
    }

    fun broadcast(message: String) {
        sessions.values.forEach { if (it.isOpen) it.sendMessage(TextMessage(message)) }
    }
}