package com.ecommerce.order.command.service;

import com.ecommerce.coreapis.command.CreateOrderCommand;
import com.ecommerce.order.command.aggregate.OrderStatus;
import com.ecommerce.order.command.dto.OrderCreateDTO;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private final CommandGateway commandGateway;

    public OrderCommandServiceImpl(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @Override
    public CompletableFuture<String> createOrder(OrderCreateDTO orderCreateDTO) {
        return commandGateway.send(new CreateOrderCommand(UUID.randomUUID().toString(), orderCreateDTO.getItemType(),
                orderCreateDTO.getPrice(), orderCreateDTO.getCurrency(), String.valueOf(OrderStatus.CREATED)));
    }
}
