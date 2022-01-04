package com.ecommerce.order.command.service;

import java.util.concurrent.CompletableFuture;

import com.ecommerce.order.command.dto.OrderCreateDTO;

public interface OrderCommandService {

    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO);

}
