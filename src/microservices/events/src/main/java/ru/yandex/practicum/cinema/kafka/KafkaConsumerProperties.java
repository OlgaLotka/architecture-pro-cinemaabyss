package ru.yandex.practicum.cinema.kafka;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Getter
@Setter
@RequiredArgsConstructor
@EnableKafka
@Configuration
@ConfigurationProperties(prefix = "spring.kafka.consumer")
public class KafkaConsumerProperties {

    private String bootstrapServers;
    private String groupId;
    private String autoOffsetReset;
    private String keyDeserializer;
    private String valueDeserializer;
}
