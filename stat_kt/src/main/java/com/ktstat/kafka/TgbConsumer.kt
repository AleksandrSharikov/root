package com.ktstat.kafka

import com.fasterxml.jackson.databind.ObjectMapper
import com.hstat.common.CommonConstantsKt
import com.hstat.common.dtoModels.StatSend
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component


@Component
class TgbConsumer (private val objectMapper: ObjectMapper){


    @KafkaListener(topics = [CommonConstantsKt.TG_STAT_TOPIC])
    fun consumeMessage(message: String){
        val received = objectMapper.readValue(message, StatSend::class.java)
        println(received)
    }
}

