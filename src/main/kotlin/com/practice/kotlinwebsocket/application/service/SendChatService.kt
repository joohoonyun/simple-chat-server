package com.practice.kotlinwebsocket.application.service

import com.practice.kotlinwebsocket.application.port.out.ChatBroadcastPort
import com.practice.kotlinwebsocket.application.usecase.SendChatUseCase
import com.practice.kotlinwebsocket.domain.ChatMessage
import org.springframework.stereotype.Service

@Service
class SendChatService(
    private val chatBroadcastPort: ChatBroadcastPort
) : SendChatUseCase {

    override fun sendMessage(message: ChatMessage) {
        chatBroadcastPort.broadcastToRoom(message.roomId, message)
    }
}
