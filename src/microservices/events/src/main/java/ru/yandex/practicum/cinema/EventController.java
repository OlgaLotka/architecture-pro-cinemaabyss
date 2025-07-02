package ru.yandex.practicum.cinema;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.cinema.api.ApiApiDelegate;
import ru.yandex.practicum.cinema.model.EventResponse;
import ru.yandex.practicum.cinema.model.GetMoviesServiceHealth200Response;
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
    public ResponseEntity<GetMoviesServiceHealth200Response> getEventsServiceHealth(){
        GetMoviesServiceHealth200Response body = new GetMoviesServiceHealth200Response();
        body.setStatus(true);
        return ResponseEntity.ok(body);
    }

    @Override
    public ResponseEntity<EventResponse> createUserEvent(UserEvent userEvent) {
        send(userEvent);
        EventResponse body = new EventResponse();
        body.status("success");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    public ResponseEntity<EventResponse> createPaymentEvent(PaymentEvent paymentEvent) {
        send(paymentEvent);
        EventResponse body = new EventResponse();
        body.status("success");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    @Override
    public ResponseEntity<EventResponse> createMovieEvent(MovieEvent movieEvent){
        send(movieEvent);
        EventResponse body = new EventResponse();
        body.status("success");
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    public void send(Object string) {
        kafkaTemplate.send(sendClientTopic, string);
    }

}
