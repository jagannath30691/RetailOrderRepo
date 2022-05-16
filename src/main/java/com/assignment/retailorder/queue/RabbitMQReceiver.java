package com.assignment.retailorder.queue;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assignment.retailorder.entity.RetailOrder;
import com.assignment.retailorder.service.OrderService;


@Component
@RabbitListener(queues = "rabbitmq.queue", id = "listener")
public class RabbitMQReceiver {

    private static Logger logger = LogManager.getLogger(RabbitMQReceiver.class.toString());


	@Autowired
	private OrderService orderService;
	
    @RabbitHandler
    public void receiver(RetailOrder order) {
    	orderService.updateOrder(order.getOrderId());
        logger.info("Order listener invoked - Consuming Message with Retail Order id : " + order.getOrderId());
    }

}
