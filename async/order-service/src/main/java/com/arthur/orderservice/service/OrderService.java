package com.arthur.orderservice.service;

import com.arthur.event.OrderNotification;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {

    //THE SECOND PARAMETERS ITS OBJECT TO PUBLISH IN TOPIC
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, OrderNotification> kafkaOrderNotificationTemplate;

    @Scheduled(fixedRate = 2000)
    public void processOrder(){
        final String orderId = UUID.randomUUID().toString();
        log.info("Order ID: {}", orderId);

        kafkaTemplate.send("order-placed", orderId);
        log.info("Message sent to Kafka topic: order-placed with orderId {}", orderId);

        processOrderNotification(orderId);
    }

    private void processOrderNotification(final String orderId){
        log.info("Processing order notification for orderId {}", orderId);
        final OrderNotification orderNotification = OrderNotification.builder()
                .orderId(orderId)
                .orderStatus("PLACED")
                .userId("JULIO RONALDO ID")
                .price(100.0)
                .productName("Product A")
                .quantity(100)
                .build();

        kafkaOrderNotificationTemplate.send("order-placed-object", orderNotification);
        log.info("Message sent to Kafka topic: order-placed with orderId {}", orderId);
    }


}
