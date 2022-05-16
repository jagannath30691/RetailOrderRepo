/**
 * 
 */
package com.assignment.retailorder.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.retailorder.constants.OrderConstants;
import com.assignment.retailorder.entity.RetailOrder;
import com.assignment.retailorder.queue.RabbitMQSender;
import com.assignment.retailorder.service.OrderService;

@RestController
@RequestMapping(value = "/api/v1/retail")
public class OrderController {
	Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	RabbitMQSender rabbitMQSender;
	
	@Autowired
	private OrderService orderService;

	@PostMapping(value = "/placeorder")
	public ResponseEntity<List<Object>> placeOrder(@RequestBody RetailOrder order) {
		List<Object> res = new ArrayList<Object>();
		try {
			if (order == null || order.getOrderIdentifier() == null || order.getCustomerName() == null) {
				res.add("Order should have valid contents!");
				return ResponseEntity.ok(res);
			}
			log.trace(" place Order in progress");
			String msg = "Message sent to the RabbitMQ Queue Successfully";
			order.setStatus(OrderConstants.PLACED);
			RetailOrder placeOrder = orderService.placeOrder(order);
			res.add(msg);
			res.add(placeOrder);
			rabbitMQSender.send(order);
		} catch (Exception e) {
			log.trace("Error occure while placeOrder", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(res);
	}
	
	@PutMapping(value = "/updateorder")
	public ResponseEntity<List<Object>> updateOrder(@RequestBody RetailOrder order) {
		List<Object> res = new ArrayList<Object>();
		try {
			if (order == null || order.getOrderId() == null) {
				res.add("Order should have order Id to update ");
				return ResponseEntity.ok(res);
			}
			log.trace(" update Order in progress");
			orderService.updateOrder(order.getOrderId());
			res.add(OrderConstants.UPDATED_STATUS);
			res.add("Order listener invoked - Consuming Message with Retail Order id : " + order.getOrderId());
		} catch (Exception e) {
			log.trace("Error occure while placeOrder", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(res);
	}
	
	@GetMapping(value = "getorder/{orderId}")
	public ResponseEntity<List<Object>> fetchOrder(@PathVariable Long orderId){
		List<Object> res = new ArrayList<Object>();
		try {
			if(orderId == null) {
				res.add("orderId should not be empty");
				return ResponseEntity.ok(res);
			}
			 Optional<RetailOrder> order = orderService.getOrderDetail(orderId);
			 if(!order.isPresent()) {
					res.add("Invalid orderId !");
					return ResponseEntity.ok(res);
				 
			 } else {
				 res.add(order.get());
			 }
		} catch (Exception e) {
			log.trace("Error occure while fetching Order", e);
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(res);
		
	}
}