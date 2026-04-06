package com.practice.kotlinwebsocket.infrastructure.persistence

import com.practice.kotlinwebsocket.application.port.out.SaveChatMessagePort
import com.practice.kotlinwebsocket.domain.ChatMessage
import org.springframework.stereotype.Component

@Component
class ChatMessagePersistenceAdapter(
    private val chatMessageMongoRepository: ChatMessageMongoRepository
) : SaveChatMessagePort {

    override fun save(message: ChatMessage) {
        chatMessageMongoRepository.save(message.toDocument())
    }
}
