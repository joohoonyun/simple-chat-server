package com.practice.kotlinwebsocket.presentation

import com.practice.kotlinwebsocket.presentation.dto.ChatMessageRequest
import com.practice.kotlinwebsocket.presentation.dto.toMessage
import com.practice.kotlinwebsocket.application.usecase.SendChatUseCase
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/chat")
@RestController
class ChatController(private val sendChatUseCase: SendChatUseCase) {
    @PostMapping("/send")
    fun sendMessage(@RequestBody @Valid messageRequest: ChatMessageRequest): ResponseEntity<Void> {
        sendChatUseCase.sendMessage(messageRequest.toMessage())
        return ResponseEntity.ok().build()
    }
}