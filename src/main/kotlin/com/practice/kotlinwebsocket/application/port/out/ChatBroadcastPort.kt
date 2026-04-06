package com.practice.kotlinwebsocket.application.port.out

import com.practice.kotlinwebsocket.domain.ChatMessage

interface ChatBroadcastPort {
    fun broadcastToRoom(roomId: String, message: ChatMessage)
}
