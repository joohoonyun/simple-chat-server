package com.practice.kotlinwebsocket.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.practice.kotlinwebsocket.config.UserSocketHandler
import com.practice.kotlinwebsocket.domain.ChatMessage
import org.springframework.stereotype.Service

@Service
class SendChatService(private val userSocketHandler: UserSocketHandler) {
    fun sendMessage(message: ChatMessage) {
        val json = ObjectMapper().writeValueAsString(message)
        userSocketHandler.broadcast(json)
    }
}