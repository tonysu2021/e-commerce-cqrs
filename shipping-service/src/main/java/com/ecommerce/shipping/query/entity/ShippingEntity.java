package com.ecommerce.shipping.query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shippings")
public class ShippingEntity {

	@Id
	@Column(name = "shipping_id")
	private String shippingId;

	@Column(name = "order_id")
	private String orderId;

	@Column(name = "payment_id")
	private String paymentId;

	public String getShippingId() {
		return shippingId;
	}

	public void setShippingId(String shippingId) {
		this.shippingId = shippingId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

}
