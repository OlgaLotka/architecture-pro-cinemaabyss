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
@ConfigurationProperties(prefix = "spring.kafka.producer")
public class KafkaProducerProperties {

    private String bootstrapServers;
}
