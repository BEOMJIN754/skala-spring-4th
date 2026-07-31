package com.onlineshop.shop1.domain.customer.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.onlineshop.shop1.domain.customer.entity.Customer;

@Repository
public interface CustomerRepository  extends JpaRepository<Customer, Long> {
    Optional<Customer> findByCustomerId(String customerId);
} 

