package com.practice.kotlinwebsocket.controller

import com.practice.kotlinwebsocket.common.ChatMessage
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class ChatController {

    @MessageMapping("/chat.send") //
    @SendTo("/topic/public")
    fun sendMessage(message: ChatMessage): ChatMessage {
        return message
    }
}