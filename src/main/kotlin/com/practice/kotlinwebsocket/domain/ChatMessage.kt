package com.practice.kotlinwebsocket.domain

data class ChatMessage(
    val roomId: String,
    val sender: String,
    val message: String,
    val type: MessageType,
)
