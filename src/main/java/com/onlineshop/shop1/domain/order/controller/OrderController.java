package com.onlineshop.shop1.domain.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.shop1.domain.order.dto.request.OrderRequestDto;
import com.onlineshop.shop1.domain.order.dto.response.OrderResponseDto;
import com.onlineshop.shop1.domain.order.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController implements OrderSwagger {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderRequestDto> createOrder(@Valid @RequestBody OrderRequestDto orderDto){
        OrderRequestDto orderDto2 = orderService.createOrder(orderDto);
        return ResponseEntity.ok(orderDto2);
    }
    
    @GetMapping("/{customerId}/products")
    public ResponseEntity<List<OrderResponseDto>> findProductbyCustomerId(@Valid @PathVariable("customerId")Long id){
        List<OrderResponseDto> orderResponseDtos = orderService.getCustomerOrders(id);
        return ResponseEntity.ok(orderResponseDtos);
    }
    
    @DeleteMapping("/cancel")
    public ResponseEntity<Void> cancelOrder(@Valid @RequestParam("id")Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
    

}
