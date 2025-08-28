package com.arthur.notificationservice.service;

import com.arthur.event.OrderNotification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {


    //The groupId it's identifier , case with several consumers
    @KafkaListener(topics = "order-placed" , groupId = "notification-service")
    public void getOrderNumber(final String orderId) {
        log.info("Received order id: {}", orderId);
    }

    @KafkaListener(topics = "order-placed-object" , groupId = "notification-service")
    public void getOrderObject(final OrderNotification orderNotification) {
        log.info("Received order : {}", orderNotification);
    }
}
