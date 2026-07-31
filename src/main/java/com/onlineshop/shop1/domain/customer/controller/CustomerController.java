package com.onlineshop.shop1.domain.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.shop1.domain.customer.dto.CustomerDto;
import com.onlineshop.shop1.domain.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "유저 정보 관리", description = "유저 정보 조회 API")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    @Operation(summary = "사용자 조회 (Id)", description = "사용자 ID 기반으로 사용자를 조회합니다.")
    public ResponseEntity<CustomerDto> getCustomerById(@Valid @PathVariable("id") Long id) {
        CustomerDto customerDto = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping
    @Operation(summary = "사용자 전체 조회", description = "사용자 전체 목록을 조회합니다.")
    public ResponseEntity<List<CustomerDto>> getAllCustomer() {
        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDtos);
    }

    @GetMapping("/name/{customerId}")
    @Operation(summary = "사용자 조회 (CustomerId)", description = "사용자 customerId(name) 기반으로 사용자를 조회합니다.")
    public ResponseEntity<CustomerDto> getCustomerByName(@Valid @PathVariable("customerId") String customerId) {
        CustomerDto customer = customerService.findByCustomerId(customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "사용자 정보 변경", description = "사용자 정보를 변경합니다.")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id,
            @Valid @RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "사용자 삭제", description = "사용자를 삭제합니다.")
    public ResponseEntity<Void> deleteCustomer(@RequestParam("id") Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    

}
