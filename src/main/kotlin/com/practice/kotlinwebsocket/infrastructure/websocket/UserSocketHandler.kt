package com.practice.kotlinwebsocket.infrastructure.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kotlinwebsocket.application.port.out.ChatBroadcastPort
import com.practice.kotlinwebsocket.domain.ChatMessage
import com.practice.kotlinwebsocket.domain.MessageType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class UserSocketHandler(
    private val objectMapper: ObjectMapper
) : TextWebSocketHandler(), ChatBroadcastPort {

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()
    private val sessionToRoom = ConcurrentHashMap<String, String>()
    private val roomToSessions = ConcurrentHashMap<String, MutableSet<String>>()
    private val logger = KotlinLogging.logger {}

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val chatMessage = objectMapper.readValue(message.payload, ChatMessage::class.java)

        when (chatMessage.type) {
            MessageType.ENTER -> {
                sessionToRoom[session.id] = chatMessage.roomId
                roomToSessions.getOrPut(chatMessage.roomId) { ConcurrentHashMap.newKeySet() }.add(session.id)
                logger.info { "${chatMessage.sender} entered room ${chatMessage.roomId}" }
                sendPayloadToRoom(chatMessage.roomId, message.payload)
            }
            MessageType.TALK -> sendPayloadToRoom(chatMessage.roomId, message.payload)
            MessageType.LEAVE -> {
                sendPayloadToRoom(chatMessage.roomId, message.payload)
                removeFromRoom(session)
            }
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info { "Session established >> ${session.id} / ${session.remoteAddress}" }
        sessions[session.id] = session
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "Session closed >> ${session.id} / ${session.remoteAddress}" }
        removeFromRoom(session)
        sessions.remove(session.id)
    }

    // ChatBroadcastPort 구현 - REST 엔드포인트에서 호출 시 사용
    override fun broadcastToRoom(roomId: String, message: ChatMessage) {
        val payload = objectMapper.writeValueAsString(message)
        sendPayloadToRoom(roomId, payload)
    }

    private fun sendPayloadToRoom(roomId: String, payload: String) {
        roomToSessions[roomId]?.forEach { sessionId ->
            sessions[sessionId]?.takeIf { it.isOpen }?.sendMessage(TextMessage(payload))
        }
    }

    private fun removeFromRoom(session: WebSocketSession) {
        val roomId = sessionToRoom.remove(session.id) ?: return
        roomToSessions[roomId]?.remove(session.id)
    }
}
