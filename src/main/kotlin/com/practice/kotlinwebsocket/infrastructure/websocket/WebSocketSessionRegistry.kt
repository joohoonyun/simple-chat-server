package com.practice.kotlinwebsocket.infrastructure.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kotlinwebsocket.application.port.out.ChatBroadcastPort
import com.practice.kotlinwebsocket.application.port.out.DrawBroadcastPort
import com.practice.kotlinwebsocket.domain.ChatMessage
import com.practice.kotlinwebsocket.domain.DrawEvent
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

@Component
class WebSocketSessionRegistry(
    private val objectMapper: ObjectMapper
) : ChatBroadcastPort, DrawBroadcastPort {

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val sessionToRoom = ConcurrentHashMap<String, String>()
    private val roomToSessions = ConcurrentHashMap<String, MutableSet<String>>()

    fun registerSession(sessionId: String, session: WebSocketSession) {
        sessions[sessionId] = session
    }

    fun deregisterSession(sessionId: String) {
        leaveRoom(sessionId)
        sessions.remove(sessionId)
    }

    fun enterRoom(sessionId: String, roomId: String) {
        sessionToRoom[sessionId] = roomId
        roomToSessions.getOrPut(roomId) { ConcurrentHashMap.newKeySet() }.add(sessionId)
    }

    fun leaveRoom(sessionId: String) {
        val roomId = sessionToRoom.remove(sessionId) ?: return
        roomToSessions[roomId]?.remove(sessionId)
    }

    fun isInRoom(sessionId: String): Boolean = sessionToRoom.containsKey(sessionId)

    override fun broadcastToRoom(roomId: String, message: ChatMessage) {
        val payload = objectMapper.writeValueAsString(message)
        roomToSessions[roomId]?.forEach { sessionId ->
            sessions[sessionId]?.takeIf { it.isOpen }?.sendMessage(TextMessage(payload))
        }
    }

    override fun broadcastDrawEvent(roomId: String, event: DrawEvent) {
        val payload = objectMapper.writeValueAsString(event)
        roomToSessions[roomId]?.forEach { sessionId ->
            sessions[sessionId]?.takeIf { it.isOpen }?.sendMessage(TextMessage(payload))
        }
    }
}
