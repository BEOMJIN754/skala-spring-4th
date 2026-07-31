package com.onlineshop.shop1.domain.order.controller;

import java.util.List;

import org.springframework.data.domain.jaxb.SpringDataJaxb.OrderDto;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Tag(name = "상품 주문", description="주문에 대한 내용")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    @Operation(summary = "주문하기", description = "상품 주문을 진행합니다.")
    public ResponseEntity<OrderRequestDto> createOrder(@Valid @RequestBody OrderRequestDto orderDto){
        OrderRequestDto orderDto2 = orderService.createOrder(orderDto);
        return ResponseEntity.ok(orderDto2);
    }
    
    @GetMapping("/{customerId}/products")
    @Operation(summary = "상품 조회", description = "유저의 상품을 조회합니다.")
    public ResponseEntity<List<OrderResponseDto>> findProductbyCustomerId(@Valid @PathVariable("customerId")Long id){
        List<OrderResponseDto> orderResponseDtos = orderService.getCustomerOrders(id);
        return ResponseEntity.ok(orderResponseDtos);
    }
    
    @DeleteMapping("/cacel")
    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    public ResponseEntity<Void> cancelOrder(@Valid @RequestParam("id")Long id){
        orderService.cancelOrder(id);
        return ResponseEntity.noContent().build();
    }
    

}
