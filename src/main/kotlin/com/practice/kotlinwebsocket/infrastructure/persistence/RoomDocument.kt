package com.practice.kotlinwebsocket.infrastructure.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("rooms")
data class RoomDocument(
    @Id
    val id: String? = null,
    val roomId: String,
    val users: List<String> = emptyList()
)
