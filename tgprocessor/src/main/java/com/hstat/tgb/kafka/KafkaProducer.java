package com.hstat.tgb.kafka;

import com.hstat.common.CommonConstants;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

/**
 * creation of the Kafka producer
 */

@Configuration
public class KafkaProducer {
    private final KafkaProperties kafkaProperties;

    @Autowired
    public KafkaProducer(KafkaProperties kafkaProperties) {
        this.kafkaProperties = kafkaProperties;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        // get configs on application.properties/yml
        Map<String, Object> properties = kafkaProperties.buildProducerProperties();
        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic topic1() {
        return TopicBuilder
                .name(CommonConstants.TopicNames.TG_STAT.getName())
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topic2() {
        return TopicBuilder
                .name("tg.userReg")
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topic3() {
        return TopicBuilder
                .name(CommonConstants.TopicNames.BOT_IN.getName())
                .partitions(1)
                .replicas(1)
                .build();
    }
    @Bean
    public NewTopic topic4() {
        return TopicBuilder
                .name(CommonConstants.TopicNames.BOT_OUT.getName())
                .partitions(1)
                .replicas(1)
                .build();
    }
}
