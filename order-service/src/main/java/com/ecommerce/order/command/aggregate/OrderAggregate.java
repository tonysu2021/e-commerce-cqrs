package com.ecommerce.order.command.aggregate;

import java.math.BigDecimal;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.ecommerce.coreapis.command.CreateOrderCommand;
import com.ecommerce.coreapis.command.UpdateOrderStatusCommand;
import com.ecommerce.coreapis.event.OrderCreatedEvent;
import com.ecommerce.coreapis.event.OrderUpdatedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class OrderAggregate {

	@AggregateIdentifier
	private String orderId;

	private ItemType itemType;

	private BigDecimal price;

	private String currency;

	private OrderStatus orderStatus;

	public OrderAggregate() {
	}

	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
		log.info("[Order][Command] CreateOrderCommand received.");
		AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.orderId, createOrderCommand.itemType,
				createOrderCommand.price, createOrderCommand.currency, createOrderCommand.orderStatus));
	}

	@EventSourcingHandler
	protected void on(OrderCreatedEvent orderCreatedEvent) {
		log.info("[Order][Command] An OrderCreatedEvent occurred.");
		this.orderId = orderCreatedEvent.orderId;
		this.itemType = ItemType.valueOf(orderCreatedEvent.itemType);
		this.price = orderCreatedEvent.price;
		this.currency = orderCreatedEvent.currency;
		this.orderStatus = OrderStatus.valueOf(orderCreatedEvent.orderStatus);
	}

	@CommandHandler
	protected void on(UpdateOrderStatusCommand updateOrderStatusCommand) {
		log.info("[Order][Command] UpdateOrderStatusCommand received.");
		AggregateLifecycle
				.apply(new OrderUpdatedEvent(updateOrderStatusCommand.orderId, updateOrderStatusCommand.orderStatus));
	}

	@EventSourcingHandler
	protected void on(OrderUpdatedEvent orderUpdatedEvent) {
		log.info("[Order][Command] An OrderUpdatedEvent occurred.");
		this.orderId = orderUpdatedEvent.orderId;
		this.orderStatus = OrderStatus.valueOf(orderUpdatedEvent.orderStatus);
	}
}
