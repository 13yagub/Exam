package com.example.exam.controller;

import com.example.exam.model.Customer;
import com.example.exam.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerController {

    CustomerService customerService;

    @PostMapping("/create-card")
    public Customer createCustomerWithCard(@RequestParam String firstName,
                                           @RequestParam String lastName,
                                           @RequestParam String fin,
                                           @RequestParam String pin) {
        return customerService.createCustomerWithCard(firstName, lastName, fin, pin);
    }

    @PostMapping("/change-pin")
    public Customer changePin(@RequestParam String cardNumber,
                              @RequestParam String newPin) {
        return customerService.changePin(cardNumber, newPin);
    }

    @DeleteMapping("/delete")
    public void deleteCustomer(@RequestParam String fin) {
        customerService.deleteCustomer(fin);
    }
}