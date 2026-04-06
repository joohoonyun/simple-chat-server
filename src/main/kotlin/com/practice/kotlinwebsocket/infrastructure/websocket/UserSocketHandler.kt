package com.practice.kotlinwebsocket.infrastructure.websocket

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kotlinwebsocket.application.usecase.SendChatUseCase
import com.practice.kotlinwebsocket.domain.ChatMessage
import com.practice.kotlinwebsocket.domain.MessageType
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

@Component
class UserSocketHandler(
    private val sendChatUseCase: SendChatUseCase,
    private val sessionRegistry: WebSocketSessionRegistry,
    private val objectMapper: ObjectMapper,
) : TextWebSocketHandler() {

    private val logger = KotlinLogging.logger {}

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        val chatMessage = objectMapper.readValue(message.payload, ChatMessage::class.java)

        when (chatMessage.type) {
            MessageType.ENTER -> {
                sessionRegistry.enterRoom(session.id, chatMessage.roomId)
                sendChatUseCase.sendMessage(chatMessage)
            }
            MessageType.TALK -> sendChatUseCase.sendMessage(chatMessage)
            MessageType.LEAVE -> {
                sendChatUseCase.sendMessage(chatMessage)
                sessionRegistry.leaveRoom(session.id)
            }
        }
    }

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info { "Session established >> ${session.id} / ${session.remoteAddress}" }
        sessionRegistry.registerSession(session.id, session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "Session closed >> ${session.id} / ${session.remoteAddress}" }
        sessionRegistry.deregisterSession(session.id)
    }
}
