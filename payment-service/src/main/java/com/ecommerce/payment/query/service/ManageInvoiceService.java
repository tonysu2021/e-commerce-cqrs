package com.ecommerce.payment.query.service;

import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.stereotype.Service;

import com.ecommerce.coreapis.event.InvoiceCreatedEvent;
import com.ecommerce.payment.query.entity.InvoiceEntity;
import com.ecommerce.payment.query.repository.InvoiceRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ManageInvoiceService {
	
	private final InvoiceRepository invoiceRepository;

	public ManageInvoiceService(InvoiceRepository invoiceRepository) {
		this.invoiceRepository = invoiceRepository;
	}
	
	@EventSourcingHandler
    protected void on(InvoiceCreatedEvent event){
		log.info("[Payment][Query] Handling InvoiceCreatedEvent ...");
		InvoiceEntity invoice = new InvoiceEntity();
		invoice.setPaymentId(event.paymentId);
		invoice.setOrderId(event.orderId);
		invoiceRepository.save(invoice);
    }
	
}
