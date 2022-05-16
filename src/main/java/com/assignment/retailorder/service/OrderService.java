package com.assignment.retailorder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignment.retailorder.constants.OrderConstants;
import com.assignment.retailorder.entity.RetailOrder;
import com.assignment.retailorder.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	public RetailOrder placeOrder(RetailOrder order) {
		return orderRepository.save(order);
	}

	public void updateOrder(Long orderId) {
		orderRepository.updateOrder(orderId,OrderConstants.PROCESSED);
	}

	public Optional<RetailOrder> getOrderDetail(Long orderId) {
		return orderRepository.findById(orderId);
		
	}
}
