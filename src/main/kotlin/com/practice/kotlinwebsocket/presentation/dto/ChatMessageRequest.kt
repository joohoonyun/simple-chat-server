package com.practice.kotlinwebsocket.presentation.dto

import com.practice.kotlinwebsocket.domain.ChatMessage
import com.practice.kotlinwebsocket.domain.MessageType
import org.jetbrains.annotations.NotNull

data class ChatMessageRequest(
    @NotNull("채팅방 ID를 입력해주세요") val roomId: String,
    @NotNull("메세지 전송자 입력해주세요") val sender: String,
    val message: String,
    @NotNull("메세지 유형을 입력해주세요") val type: MessageType,
)

fun ChatMessageRequest.toMessage(): ChatMessage =
    ChatMessage(
        roomId = this.roomId,
        sender = this.sender,
        message = this.message,
        type = this.type
    )
