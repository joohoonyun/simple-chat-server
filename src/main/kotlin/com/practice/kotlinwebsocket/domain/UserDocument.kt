package com.practice.kotlinwebsocket.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class UserDocument(
    @Id
    val id: String?= null,
    val userId: String,
    val username: String,
    val currentRoom: String?= null,
)
