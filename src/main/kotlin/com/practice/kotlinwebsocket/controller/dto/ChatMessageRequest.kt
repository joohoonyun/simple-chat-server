package com.practice.kotlinwebsocket.controller.dto

import com.practice.kotlinwebsocket.domain.ChatMessage
import com.practice.kotlinwebsocket.domain.MessageType

data class ChatMessageRequest(
    val roomId: String,
    val sender: String,
    val message: String,
    val type: MessageType,
)

fun ChatMessageRequest.toMessage(): ChatMessage {
    return ChatMessage(
        roomId = this.roomId,
        sender = this.sender,
        message = this.message,
        type = this.type
    )
}