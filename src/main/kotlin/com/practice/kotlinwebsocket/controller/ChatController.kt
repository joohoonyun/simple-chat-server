package com.practice.kotlinwebsocket.controller

import com.practice.kotlinwebsocket.controller.dto.ChatMessageRequest
import com.practice.kotlinwebsocket.controller.dto.toMessage
import com.practice.kotlinwebsocket.service.SendChatService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/chat")
@RestController
class ChatController(private val sendChatService: SendChatService) {
    @PostMapping("/send")
    fun sendMessage(@RequestBody messageRequest: ChatMessageRequest): ResponseEntity<Void> {
        sendChatService.sendMessage(messageRequest.toMessage())
        return ResponseEntity.ok().build()
    }
}