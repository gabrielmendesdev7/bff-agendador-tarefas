package com.javanauta.bffagendadortarefas.infrastructure.message.producer;

import com.javanauta.bffagendadortarefas.busines.dto.response.TarefasResponse;
import com.javanauta.bffagendadortarefas.infrastructure.message.config.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarEmail(TarefasResponse response) {
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE_EMAIL,
                RabbitMqConfig.ROUNTING_KEY,
                response);
    }

}
