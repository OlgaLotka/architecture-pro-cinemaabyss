package ru.yandex.practicum.cinema;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@ConditionalOnProperty(prefix = "spring.kafka", name = "enabled", havingValue = "true")
public class KafkaEventConsumer {


    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}",
        containerFactory = "systemEventListenerContainerFactory")
    public void processUserEvent(ConsumerRecord<String, String> record) {
        log.info("Received Event: " + record);
    }


}
