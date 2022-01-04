package com.ecommerce.payment.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "invoices")
public class InvoiceEntity {

	@Id
	@Column(name = "payment_id")
	public String paymentId;
	
	@Column(name = "order_id")
	public String orderId;
	
	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
