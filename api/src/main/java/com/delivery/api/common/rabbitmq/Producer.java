package com.delivery.api.common.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    // exchange -> 어디로 보낼지
    // routeKey -> 특정 큐로 라우팅
    // object -> 데이터
    public void producer(String exchange, String routeKey, Object object) {
        rabbitTemplate.convertAndSend(exchange, routeKey, object);
    }

}
