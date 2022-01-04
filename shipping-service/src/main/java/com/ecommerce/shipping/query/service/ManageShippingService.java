package com.ecommerce.shipping.query.service;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;

import com.ecommerce.coreapis.event.OrderShippedEvent;
import com.ecommerce.shipping.query.entity.ShippingEntity;
import com.ecommerce.shipping.query.repository.ShippingRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManageShippingService {

	private final ShippingRepository shippingRepository;

	public ManageShippingService(ShippingRepository shippingRepository) {
		this.shippingRepository = shippingRepository;
	}

	@EventHandler
	protected void on(OrderShippedEvent event) {
		log.info("[Shipping][Query] Handling OrderShippedEvent ...");
		ShippingEntity shipping = new ShippingEntity();
		shipping.setShippingId(event.shippingId);
		shipping.setOrderId(event.orderId);
		shipping.setPaymentId(event.paymentId);
		shippingRepository.save(shipping);
	}
}
