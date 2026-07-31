package com.onlineshop.shop1.domain.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.onlineshop.shop1.domain.customer.entity.Customer;
import com.onlineshop.shop1.domain.customer.repository.CustomerRepository;
import com.onlineshop.shop1.domain.order.dto.request.OrderRequestDto;
import com.onlineshop.shop1.domain.order.dto.response.OrderResponseDto;
import com.onlineshop.shop1.domain.order.entity.Order;
import com.onlineshop.shop1.domain.order.repository.OrderRepository;
import com.onlineshop.shop1.domain.product.entity.Product;
import com.onlineshop.shop1.domain.product.repository.ProductRepository;
import com.onlineshop.shop1.exception.CustomerNotFoundException;
import com.onlineshop.shop1.exception.InsufficientException;
import com.onlineshop.shop1.exception.OrderNotFoundException;
import com.onlineshop.shop1.exception.ProductNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    public OrderRequestDto createOrder(OrderRequestDto orderDto) {
        Customer customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException(orderDto.getCustomerId()));
        Product product = productRepository.findById(orderDto.getProductId())
                .orElseThrow(() -> new ProductNotFoundException(orderDto.getProductId()));

        int quantity = orderDto.getQuantity();
        double totalprice = product.getProductPrice() * quantity;

        if (product.getQuantity() < quantity) {
            throw new InsufficientException(product.getQuantity(), quantity);
        }

        if (customer.getCustomerPoint() < totalprice) {
            throw new InsufficientException(customer.getCustomerPoint(), totalprice);
        }

        customer.usePoint(totalprice);
        product.decreaseStock(quantity);

        Order order = Order.builder()
                .customer(customer)
                .product(product)
                .quantity(quantity)
                .build();
        Order savedOrder = orderRepository.save(order);
        return OrderRequestDto.from(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDto> getCustomerOrders(Long customerId) {
        customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

    return orderRepository.findByCustomerId(customerId)
            .stream()
            .map(OrderResponseDto::from)
            .toList();
    }
    @Transactional
    public void cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(()-> new OrderNotFoundException(id));
        Customer customer = customerRepository.findById(order.getCustomer().getId())
                .orElseThrow(() -> new CustomerNotFoundException(order.getCustomer().getId()));
        Product product = productRepository.findById(order.getProduct().getId())
                .orElseThrow(() -> new ProductNotFoundException(order.getProduct().getId()));
        
        int quantity = order.getQuantity();
        double point = quantity * product.getProductPrice();
        
        customer.returnPoint(point);
        product.increaseStock(quantity);

        orderRepository.deleteById(id);
    }

}
