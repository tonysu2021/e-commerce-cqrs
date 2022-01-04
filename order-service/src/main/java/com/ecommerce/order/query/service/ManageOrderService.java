package com.ecommerce.order.query.service;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import com.ecommerce.coreapis.event.OrderCreatedEvent;
import com.ecommerce.coreapis.event.OrderUpdatedEvent;
import com.ecommerce.order.query.entity.OrderEntity;
import com.ecommerce.order.query.repository.OrderRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManageOrderService {

	private final OrderRepository orderRepository;

	public ManageOrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@EventHandler
	protected void on(OrderCreatedEvent event) {
		log.info("[Order][Query] Handling OrderCreatedEvent {}...",event);
		OrderEntity order = new OrderEntity();
		order.setOrderId(event.orderId);
		order.setItemType(event.itemType);
		order.setPrice(event.price);
		order.setCurrency(event.currency);
		order.setStatus(event.orderStatus);
		orderRepository.save(order);
	}

	@EventHandler
	protected void on(OrderUpdatedEvent event) {
		log.info("[Order][Query] Handling OrderUpdatedEvent ...");
		OrderEntity order = orderRepository.findById(event.orderId).orElse(null);
		if (order == null) {
			return;
		}
		order.setStatus(event.orderStatus);
		orderRepository.save(order);
	}
}
