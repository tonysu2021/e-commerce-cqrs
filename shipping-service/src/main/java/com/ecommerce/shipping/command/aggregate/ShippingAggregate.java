package com.ecommerce.shipping.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.ecommerce.coreapis.command.CreateShippingCommand;
import com.ecommerce.coreapis.event.OrderShippedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class ShippingAggregate {

    @AggregateIdentifier
    private String shippingId;

    private String orderId;

    private String paymentId;

    public ShippingAggregate() {
    }

    @CommandHandler
    public ShippingAggregate(CreateShippingCommand command){
    	log.info("[Shipping][Command] CreateShippingCommand received.");
    	AggregateLifecycle.apply(new OrderShippedEvent(command.shippingId, command.orderId, command.paymentId));
    }

    @EventSourcingHandler
    protected void on(OrderShippedEvent event){
    	log.info("[Shipping][Command] An OrderShippedEvent occurred.");
    	this.shippingId = event.shippingId;
        this.orderId = event.orderId;
    }
}
