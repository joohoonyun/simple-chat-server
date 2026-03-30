package com.practice.kotlinwebsocket.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document("chat_messages")
@CompoundIndex(
    name = "room_timestamp_idx",
    def = "{'roomId': 1, 'timestamp': -1}"
)
data class ChatMessage(
    @Id
    val id: String?= null,
    val roomId: String,
    val sender: String,
    val message: String,
    val type: MessageType,
)

enum class MessageType {
    ENTER,
    TALK,
    LEAVE
}
