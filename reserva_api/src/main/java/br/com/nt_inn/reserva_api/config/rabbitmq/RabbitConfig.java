package br.com.nt_inn.reserva_api.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {
    public static final String QUEUE_NAME = "notificacoesQueue";
    public static final String EXCHANGE_NAME = "notificacoesExchange";
    public static final String ROUTING_KEY = "notificacoesRoutingKey";

    @Bean
    public Queue notificacoesQueue() {
        return new Queue(QUEUE_NAME, true);
    }

}