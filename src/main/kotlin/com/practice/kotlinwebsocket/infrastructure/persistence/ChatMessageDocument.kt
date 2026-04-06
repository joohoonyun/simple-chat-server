package com.practice.kotlinwebsocket.infrastructure.persistence

import com.practice.kotlinwebsocket.domain.ChatMessage
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.mapping.Document

@Document("chat_messages")
@CompoundIndex(
    name = "room_timestamp_idx",
    def = "{'roomId': 1, 'timestamp': -1}"
)
data class ChatMessageDocument(
    @Id
    val id: String? = null,
    val roomId: String,
    val sender: String,
    val message: String,
    val type: String,
)

fun ChatMessage.toDocument(): ChatMessageDocument {
    return ChatMessageDocument(
        roomId = this.roomId,
        sender = this.sender,
        message = this.message,
        type = this.type.name
    )
}
