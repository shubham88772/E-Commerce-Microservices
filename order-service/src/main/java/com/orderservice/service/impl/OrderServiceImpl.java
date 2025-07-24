package com.orderservice.service.impl;

import com.orderservice.dto.OrderRequest;
import com.orderservice.entity.Order;
import com.orderservice.entity.OrderItem;
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

        List<OrderItem> orderItems = orderRequest.getOrderItems().stream()
                .map(item -> {
                    OrderItem orderItem = modelMapper.map(item, OrderItem.class);
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .toList();

        order.setOrderItems(orderItems);
        orderRepository.save(order);

        return "Order placed successfully!";
    }
}
