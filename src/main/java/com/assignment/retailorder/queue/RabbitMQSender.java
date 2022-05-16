package com.assignment.retailorder.queue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.retailorder.entity.RetailOrder;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    private Queue queue;

    private static Logger logger = LogManager.getLogger(RabbitMQSender.class.toString());


    public void send(RetailOrder order) {
        rabbitTemplate.convertAndSend(queue.getName(), order);
        logger.info("Sending Message to the Queue : " + order.toString());
    }
}
