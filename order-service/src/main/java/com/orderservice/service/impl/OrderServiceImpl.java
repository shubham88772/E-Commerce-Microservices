package com.orderservice.service.impl;

import com.orderservice.dto.OrderRequest;
import com.orderservice.entity.Order;
import com.orderservice.entity.OrderLineItems;
import com.orderservice.repository.OrderRepository;
import com.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsList()
                .stream()
                .map(dto -> modelMapper.map(dto, OrderLineItems.class))
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);

        return "Order placed successfully!";
    }
}
