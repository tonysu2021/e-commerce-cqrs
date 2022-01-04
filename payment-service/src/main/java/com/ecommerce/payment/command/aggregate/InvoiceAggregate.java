package com.ecommerce.payment.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.ecommerce.coreapis.command.CreateInvoiceCommand;
import com.ecommerce.coreapis.event.InvoiceCreatedEvent;

import lombok.extern.slf4j.Slf4j;

@Aggregate
@Slf4j
public class InvoiceAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private InvoiceStatus invoiceStatus;

    public InvoiceAggregate() {
    }

    @CommandHandler
    public InvoiceAggregate(CreateInvoiceCommand command){
    	log.info("[Payment][Command] CreateInvoiceCommand received.");
    	AggregateLifecycle.apply(new InvoiceCreatedEvent(command.paymentId, command.orderId));
    }

    @EventSourcingHandler
    protected void on(InvoiceCreatedEvent event){
    	log.info("[Payment][Command] An InvoiceCreatedEvent occurred.");
        this.paymentId = event.paymentId;
        this.orderId = event.orderId;
        this.invoiceStatus = InvoiceStatus.PAID;
    }
}
