package com.practice.kotlinwebsocket.service

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
internal class SequenceService(private val mongoTemplate: MongoTemplate) {
    fun getNextSequence(name: String): Long {
        val query = Query(Criteria.where("_id").`is`(name))
        return 1L
    }
}