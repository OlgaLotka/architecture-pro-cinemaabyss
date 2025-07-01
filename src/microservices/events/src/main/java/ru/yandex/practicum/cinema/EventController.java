package ru.yandex.practicum.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.cinema.api.ApiApiDelegate;
import ru.yandex.practicum.cinema.model.EventResponse;
import ru.yandex.practicum.cinema.model.MovieEvent;
import ru.yandex.practicum.cinema.model.PaymentEvent;
import ru.yandex.practicum.cinema.model.UserEvent;

@Primary
@RestController
@RequiredArgsConstructor
public class EventController implements ApiApiDelegate {

    @Value("${spring.kafka.consumer.topic:topic-event}")
    private String sendClientTopic;

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    public ResponseEntity<EventResponse> createUserEvent(UserEvent userEvent) {
        send(userEvent);
        return ResponseEntity.ok(new EventResponse());
    }

    @Override
    public ResponseEntity<EventResponse> createPaymentEvent(PaymentEvent paymentEvent) {
        send(paymentEvent);
        return ResponseEntity.ok(new EventResponse());
    }

    @Override
    public ResponseEntity<EventResponse> createMovieEvent(MovieEvent movieEvent){
        send(movieEvent);
        return ResponseEntity.ok(new EventResponse());
    }

    public void send(Object string) {
        kafkaTemplate.send(sendClientTopic, string);
    }

}
