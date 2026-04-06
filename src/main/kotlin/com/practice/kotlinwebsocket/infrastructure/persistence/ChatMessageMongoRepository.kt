package com.practice.kotlinwebsocket.infrastructure.persistence

import org.springframework.data.mongodb.repository.MongoRepository

interface ChatMessageMongoRepository : MongoRepository<ChatMessageDocument, String>
