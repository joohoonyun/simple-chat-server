package com.practice.kotlinwebsocket.application.usecase

import com.practice.kotlinwebsocket.domain.ChatMessage

interface SendChatUseCase {
    fun sendMessage(message: ChatMessage)
}
