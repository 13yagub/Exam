package com.example.exam.repository;

import com.example.exam.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByFinAndDeletedFalse(String fin);
    Optional<Customer> findByCardNumberAndDeletedFalse(String cardNumber);
}