package com.onlineshop.shop1.domain.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onlineshop.shop1.domain.customer.dto.CustomerDto;
import com.onlineshop.shop1.domain.customer.entity.Customer;
import com.onlineshop.shop1.domain.customer.repository.CustomerRepository;
import com.onlineshop.shop1.exception.CustomerNotFoundException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return CustomerDto.from(customer);
    }

    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerDto::from)
                .toList();
    }

    public CustomerDto findByCustomerId(String customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId));
        return CustomerDto.from(customer);
    }

    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        System.out.println("서비스 point = " + customerDto.getCustomerPoint());
        System.out.println("서비스 id = " + customerDto.getCustomerId());
        customer.update(customerDto.getCustomerId(), customerDto.getCustomerPoint());
        return CustomerDto.from(customer);
    }
    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.findById(id).orElseThrow(()-> new CustomerNotFoundException(id));
        customerRepository.deleteById(id);
    }

}
