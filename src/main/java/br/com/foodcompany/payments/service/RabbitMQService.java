package br.com.foodcompany.payments.service;

import br.com.foodcompany.payments.model.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final FanoutExchange fanoutExchange;

    @Value("${spring.rabbitmq.routing-key}")
    private String routingKey;

    public void sendMessage(Payment payment) {
        rabbitTemplate.convertAndSend(fanoutExchange.getName(), routingKey, payment);
    }

}
