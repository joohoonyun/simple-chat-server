package com.practice.kotlinwebsocket.application.port.out

import com.practice.kotlinwebsocket.domain.ChatMessage

interface SaveChatMessagePort {
    fun save(message: ChatMessage)
}
