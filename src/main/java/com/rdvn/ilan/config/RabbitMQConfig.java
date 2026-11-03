package com.rdvn.ilan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;


@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue adApprovalQueue() {
        return new Queue("ad-approval-queue", true);//queue RabbitMQ restart sonrası da korunacak şekilde tanımlanır
    }

}
