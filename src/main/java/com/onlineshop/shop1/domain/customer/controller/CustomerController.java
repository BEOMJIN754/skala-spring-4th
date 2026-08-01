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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController implements CustomerSwagger {
    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@Valid @PathVariable("id") Long id) {
        CustomerDto customerDto = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomer() {
        List<CustomerDto> customerDtos = customerService.getAllCustomers();
        return ResponseEntity.ok(customerDtos);
    }

    @GetMapping("/name/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerByName(@Valid @PathVariable("customerId") String customerId) {
        CustomerDto customer = customerService.findByCustomerId(customerId);
        return ResponseEntity.ok(customer);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable("id") Long id,
            @Valid @RequestBody CustomerDto customerDto) {
        CustomerDto customer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteCustomer(@RequestParam("id") Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    

}
